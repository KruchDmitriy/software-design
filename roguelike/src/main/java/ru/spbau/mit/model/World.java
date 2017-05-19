package ru.spbau.mit.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class World {
    private static final String DEFAULT_MAP = "src/main/resources/default.map";

    private static final int DEFAULT_SIZE = 42;
    private int size = DEFAULT_SIZE;
    private List<GameObject> objects = new ArrayList<>();

    public World() {
        loadMap(DEFAULT_MAP);
    }

    public int getSize() {
        return size;
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public static class LoadObjectInfo {
        public final KindObject kindObject;
        public final int x;
        public final int y;

        LoadObjectInfo(KindObject kindObject, int x, int y) {
            this.kindObject = kindObject;
            this.x = x;
            this.y = y;
        }

        public enum KindObject {
            NO_OBJECT(0),
            PLAYER(1),
            ENEMY(2),
            ITEM(3),
            LANDSCAPE(4);

            private final int value;
            KindObject(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }
        }
    }


    private void loadMap(String mapFileName) {
        try {
            Scanner scanner = new Scanner(new BufferedReader(
                    new FileReader(mapFileName)));

            size = scanner.nextInt();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int kind = scanner.nextInt();

                    LoadObjectInfo.KindObject kindObject;
                    switch (kind) {
                        case 1:
                            kindObject = LoadObjectInfo.KindObject.PLAYER;
                            break;
                        case 2:
                            kindObject = LoadObjectInfo.KindObject.ENEMY;
                            break;
                        case 3:
                            kindObject = LoadObjectInfo.KindObject.ITEM;
                            break;
                        case 4:
                            kindObject = LoadObjectInfo.KindObject.LANDSCAPE;
                            break;
                        default:
                            kindObject = LoadObjectInfo.KindObject.NO_OBJECT;
                    }

                    if (kindObject != LoadObjectInfo.KindObject.NO_OBJECT) {
                        objects.add(ObjectFactory.createObject(
                                new LoadObjectInfo(kindObject, i, j)));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading map.");
        }
    }
}
