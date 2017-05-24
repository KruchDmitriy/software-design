package ru.spbau.mit.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import ru.spbau.mit.*;
import ru.spbau.mit.Void;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientImpl {
    private static final Logger logger = Logger.getLogger(
            ClientImpl.class.getName());
    private static final BufferedReader READER = new BufferedReader(
            new InputStreamReader(System.in));

    private static final DateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy/MM/dd HH:mm:ss\n");

    private static String myName;
    private static String buddyName;

    private final ManagedChannel channel;
    private final ChatGrpc.ChatFutureStub chatStub;
//    private final ChatGrpc.ChatBlockingStub blockingStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public ClientImpl(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true));
    }

    /** Construct client for accessing to server using the existing channel. */
    ClientImpl(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        chatStub = ChatGrpc.newFutureStub(channel);
//        blockingStub = ChatGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Say hello to server. */
    public void greet() {
        logger.info("Trying to connect...");
        HelloRequest request = HelloRequest.newBuilder().setName(myName).build();
        ListenableFuture<HelloReply> response;
        try {
            response = chatStub.sayHello(request);
            buddyName = response.get().getName();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getMessage());
            return;
        } catch (ExecutionException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getMessage());
            return;
        }
        logger.info("Greeting: " + buddyName);
    }

    public String receiveMessage() throws ExecutionException, InterruptedException {
        ListenableFuture<Message> message;
        try {
            message = chatStub.recieveMessage(ru.spbau.mit.Void.newBuilder().build());
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }

        return message.get().getContent();
    }

    public boolean sendMessage(String content) {
        Message message = Message.newBuilder().setContent(content).build();
        try {
            chatStub.sendMessage(message);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return false;
        }

        return true;
    }

    private static void printDialog(String who, String message) {
        System.out.println(DATE_FORMAT.format(new Date()) + who
                + ": " + message);
        System.out.flush();
    }

    /**
     * Main cycle
     * @param args: args[0] -- host, args[1] -- port, args[2] -- name
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        final ClientImpl client = new ClientImpl(args[0],
                Integer.parseInt(args[1]));
        myName = args[2];

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    String message = null;
                    try {
                        message = client.receiveMessage();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (message != null) {
                        printDialog(buddyName, message);
                    }

                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        try {
            client.greet();

            while (true) {
                String message = READER.readLine();
                printDialog(myName, message);
                client.sendMessage(message);

                if (message.equals("exit")) {
                    break;
                }
            }
        } finally {
            client.shutdown();
        }
    }
}
