package ru.spbau.mit.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ru.spbau.mit.*;
import ru.spbau.mit.Void;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerImpl {
    private static final Logger logger = Logger.getLogger(ServerImpl.class.getName());
    private static final BufferedReader READER = new BufferedReader(
            new InputStreamReader(System.in));

    private static final DateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy/MM/dd HH:mm:ss\n");

    private static String myName;
    private static String buddyName;

    private Server server;

    private void start(int port) throws IOException {
    /* The port on which the server should run */
        server = ServerBuilder.forPort(port)
                .addService(new ChatImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                ServerImpl.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private static void printDialog(String who, String message) {
        System.out.println(DATE_FORMAT.format(new Date()) + who
                + ": " + message);
    }

    /**
     * Main cycle
     * @param args: args[0] -- port to listen, args[1] -- name
     * @throws Exception
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        myName = args[1];

        final ServerImpl server = new ServerImpl();
        server.start(Integer.parseInt(args[0]));
        server.blockUntilShutdown();
    }

    static class ChatImpl extends ChatGrpc.ChatImplBase {
        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            buddyName = req.getName();
            HelloReply reply = HelloReply.newBuilder().setName(myName).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void sendMessage(Message request, StreamObserver<Void> responseObserver) {
            printDialog(buddyName, request.getContent());
            System.out.flush();
        }

        @Override
        public void recieveMessage(Void request, StreamObserver<Message> responseObserver) {
            Message message = Message.newBuilder()
                    .setContent("Exception while reading.")
                    .build();

            try {
                String content = READER.readLine();
                message = Message.newBuilder()
                    .setContent(content)
                    .build();
                printDialog(myName, content);
            } catch (IOException e) {
                logger.log(Level.WARNING, "Exception while reading.");
            }

            responseObserver.onNext(message);
            responseObserver.onCompleted();
        }
    }
}