package ru.spbau.mit.model;

import ru.spbau.mit.Entity;
import ru.spbau.mit.messages.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollisionHandler implements Filter {
    private Map<GameObject.Position, GameObject> positions =
            new ConcurrentHashMap<>();
    private final int mapSize;

    public CollisionHandler(final List<GameObject> objects, int mapSize) {
        objects.forEach(
                object -> positions.put(
                    object.getPosition(),
                    object
                )
        );

        this.mapSize = mapSize;
    }

    @Override
    public Message apply(Message message, Entity receiver) {
        if (message instanceof ShiftMessage) {
            GameObject gameObject = (GameObject) receiver;
            ShiftMessage shiftMessage = ((ShiftMessage) message);

            GameObject.Position position = gameObject.getPosition();
            position.shift(shiftMessage.getDirection());

            if (positions.containsKey(position)) {
                GameObject object = positions.get(position);

                if (object instanceof Enemy && gameObject instanceof Player) {
                    Player player = (Player) gameObject;
                    Enemy enemy = (Enemy) object;

                    Message playerMessage = new FightMessage(enemy.getDamage(),
                            enemy.toString(), player.toString());

                    Message enemyMessage;
                    if (enemy.getHealth() - player.getDamage() <= 0) {
                        positions.remove(position);
                        enemyMessage = new DeathMessage(enemy);
                    } else {
                        enemyMessage = new FightMessage(player.getDamage(),
                                player.toString(), enemy.toString());
                    }

                    enemy.process(enemyMessage);
                    return playerMessage;
                }

                if (object instanceof Item && receiver instanceof Player) {
                    ItemTakenMessage itemMsg = new ItemTakenMessage((Item) object);

                    receiver.process(itemMsg);
                    object.process(itemMsg);
                    positions.remove(object.getPosition());
                    positions.remove(gameObject.getPosition());
                    positions.put(position, gameObject);

                    return shiftMessage;
                }


                shiftMessage.setDirection(ShiftDirection.NO_SHIFT);
                return shiftMessage;
            }

            if (position.x < 0 || position.x >= mapSize
                    || position.y < 0 || position.y >= mapSize) {
                shiftMessage.setDirection(ShiftDirection.NO_SHIFT);
                return shiftMessage;
            }

            positions.remove(gameObject.getPosition());
            positions.put(position, gameObject);
        }

        return message;
    }
}
