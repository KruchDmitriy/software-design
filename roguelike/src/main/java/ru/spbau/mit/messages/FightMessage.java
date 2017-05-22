package ru.spbau.mit.messages;

public class FightMessage implements Message {
    private int damage;
    private String who;
    private String whom;

    public FightMessage(int damage, String who, String whom) {
        this.damage = damage;
        this.who = who;
        this.whom = whom;
    }

    public int getDamage() {
        return damage;
    }

    public String getWho() {
        return who;
    }

    public String getWhom() {
        return whom;
    }
}
