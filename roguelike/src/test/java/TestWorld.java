import org.junit.Test;
import ru.spbau.mit.messages.FightMessage;
import ru.spbau.mit.messages.Message;
import ru.spbau.mit.messages.ShiftMessage;
import ru.spbau.mit.model.*;
import ru.spbau.mit.model.items.Carrot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junitx.framework.ComparableAssert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestWorld {
    private static final ShiftMessage right = new ShiftMessage(ShiftDirection.RIGHT);
    private static final ShiftMessage left = new ShiftMessage(ShiftDirection.LEFT);
    private static final ShiftMessage down = new ShiftMessage(ShiftDirection.DOWN);
    private static final ShiftMessage up = new ShiftMessage(ShiftDirection.UP);
    private static final ShiftMessage noShift = new ShiftMessage(ShiftDirection.NO_SHIFT);

    private static final Player player = new Player(0, 0);
    private static final Enemy enemy = new Enemy(1, 1);
    private static final Carrot carrot = new Carrot(2, 2);

    @Test
    public void testPlayerMoving() {
        Player player = new Player(2, 3);

        player.process(right);
        assertEquals(3, player.getX());

        player.process(left);
        assertEquals(2, player.getX());

        player.process(up);
        assertEquals(2, player.getY());

        player.process(down);
        assertEquals(3, player.getY());
    }

    @Test
    public void testCollisionHandlerAngles() {
        List<GameObject> objects = Collections.singletonList(player);
        CollisionHandler collisionHandler = new CollisionHandler(objects, 3);

        player.setX(0);
        player.setY(0);

        assertEquals(noShift, (ShiftMessage) collisionHandler.apply(left, player));
        assertEquals(noShift, (ShiftMessage) collisionHandler.apply(up, player));

        assertEquals(right, (ShiftMessage) collisionHandler.apply(right, player));
        assertEquals(down, (ShiftMessage) collisionHandler.apply(down, player));

        player.setX(1);
        player.setY(0);

        assertEquals(left, (ShiftMessage) collisionHandler.apply(left, player));
        assertEquals(noShift, (ShiftMessage) collisionHandler.apply(up, player));

        assertEquals(right, (ShiftMessage) collisionHandler.apply(right, player));
        assertEquals(down, (ShiftMessage) collisionHandler.apply(down, player));

        player.setX(0);
        player.setY(1);

        assertEquals(noShift, (ShiftMessage) collisionHandler.apply(left, player));
        assertEquals(up, (ShiftMessage) collisionHandler.apply(up, player));

        assertEquals(right, (ShiftMessage) collisionHandler.apply(right, player));
        assertEquals(down, (ShiftMessage) collisionHandler.apply(down, player));

        player.setX(1);
        player.setY(1);

        assertEquals(left, (ShiftMessage) collisionHandler.apply(left, player));
        assertEquals(up, (ShiftMessage) collisionHandler.apply(up, player));

        assertEquals(right, (ShiftMessage) collisionHandler.apply(right, player));
        assertEquals(down, (ShiftMessage) collisionHandler.apply(down, player));

        player.setX(2);
        player.setY(1);

        assertEquals(left, (ShiftMessage) collisionHandler.apply(left, player));
        assertEquals(up, (ShiftMessage) collisionHandler.apply(up, player));

        assertEquals(noShift, (ShiftMessage) collisionHandler.apply(right, player));
        assertEquals(down, (ShiftMessage) collisionHandler.apply(down, player));

        player.setX(1);
        player.setY(2);

        assertEquals(left, (ShiftMessage) collisionHandler.apply(left, player));
        assertEquals(up, (ShiftMessage) collisionHandler.apply(up, player));

        assertEquals(right, (ShiftMessage) collisionHandler.apply(right, player));
        assertEquals(noShift, (ShiftMessage) collisionHandler.apply(down, player));

        player.setX(2);
        player.setY(2);

        assertEquals(left, (ShiftMessage) collisionHandler.apply(left, player));
        assertEquals(up, (ShiftMessage) collisionHandler.apply(up, player));

        assertEquals(noShift, (ShiftMessage) collisionHandler.apply(right, player));
        assertEquals(noShift, (ShiftMessage) collisionHandler.apply(down, player));
    }

    @Test
    public void testCollisionHandlerFight() {
        player.setX(1);
        player.setY(1);

        enemy.setX(0);
        enemy.setY(1);

        List<GameObject> objects = Arrays.asList(player, enemy);
        CollisionHandler collisionHandler = new CollisionHandler(objects, 3);

        Message message = collisionHandler.apply(left, player);
        assertTrue(message instanceof FightMessage);
        FightMessage fightMessage = (FightMessage) message;

        assertEquals("Fox", fightMessage.getWho());
        assertEquals("Raccoon", fightMessage.getWhom());
        assertEquals(enemy.new Characteristics().getDamage(), fightMessage.getDamage());

        message = collisionHandler.apply(right, enemy);
        assertTrue(message instanceof ShiftMessage);
        ShiftMessage shiftMessage = (ShiftMessage) message;
        assertEquals(noShift, shiftMessage);
    }

    @Test
    public void testCollisionHandlerItem() {
        player.setX(1);
        player.setY(1);

        carrot.setX(2);
        carrot.setY(1);

        List<GameObject> objects = Arrays.asList(player, carrot);
        CollisionHandler collisionHandler = new CollisionHandler(objects, 3);

        Message message = collisionHandler.apply(right, player);
        assertEquals(right, (ShiftMessage) message);
        assertTrue(carrot == player.getTakenItems().get(0));
    }
}
