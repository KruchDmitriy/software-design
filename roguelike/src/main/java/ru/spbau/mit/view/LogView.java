package ru.spbau.mit.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import ru.spbau.mit.Entity;
import ru.spbau.mit.messages.*;
import ru.spbau.mit.model.Player;


public class LogView extends Entity {
    private static final ListView<String> LOG_LIST_VIEW = new ListView<>();
    private static final ListView<String> LOG_CHAR_STAT_VIEW = new ListView<>();
    private static final ObservableList<String> LOG_ITEMS =
            FXCollections.observableArrayList(
            "Welcome to the raccoon rogue-like game"
            );
    private static final ObservableList<String> CHARACTER_STAT =
            FXCollections.observableArrayList();
    private static final int LOG_WIDTH_FACTOR = 3;

    private final int logWidth;
    private final int logHeight;

    private final StackPane stackPane = new StackPane();

    LogView(int windowSize) {
        logHeight = windowSize;
        logWidth = windowSize / LOG_WIDTH_FACTOR;

        LOG_LIST_VIEW.setItems(LOG_ITEMS);
        LOG_LIST_VIEW.setPrefWidth(logWidth);

        TitledPane charStatPane = new TitledPane("Character statistics",
                LOG_CHAR_STAT_VIEW);
        charStatPane.setCollapsible(false);
        charStatPane.setPrefHeight(windowSize / 2);

        TitledPane infoPane = new TitledPane("Info", LOG_LIST_VIEW);
        infoPane.setCollapsible(false);
        infoPane.setTranslateY(windowSize / 2);
        infoPane.setPrefHeight(windowSize / 2);

        stackPane.setTranslateX(windowSize);
        stackPane.getChildren().addAll(charStatPane, infoPane);
    }

    public static void write(String loggedText) {
        LOG_ITEMS.add(loggedText);
    }

    public int getWidth() {
        return logWidth;
    }

    public int getHeight() {
        return logHeight;
    }

    public Node toFXNode() {
        return stackPane;
    }

    @Override
    public void process(Message message) {
        super.process(message);

        if (message instanceof PlayerStatusMessage) {
            CHARACTER_STAT.clear();

            PlayerStatusMessage playerMessage = (PlayerStatusMessage) message;

            playerMessage.getChars()
                    .forEach((key, value) -> CHARACTER_STAT.add(key
                            + ": " + value));
            LOG_CHAR_STAT_VIEW.setItems(CHARACTER_STAT);
            return;
        }

        if (message instanceof ItemTakenMessage) {
            ItemTakenMessage itemMsg = (ItemTakenMessage) message;
            LOG_ITEMS.add("Player just take: " + itemMsg.getItem().toString());
            return;
        }

        if (message instanceof FightMessage) {
            FightMessage fightMsg = (FightMessage) message;
            LOG_ITEMS.add(fightMsg.getWho() + " hits damage "
                    + fightMsg.getDamage() + " to " + fightMsg.getWhom());
            return;
        }

        if (message instanceof DeathMessage) {
            if (((DeathMessage) message).getWho() instanceof Player) {
                LOG_ITEMS.add("Game over.");
            }
        }
    }
}
