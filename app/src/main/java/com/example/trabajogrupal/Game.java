package com.example.trabajogrupal;

public class Game {
    private int id;
    private Player rival;
    private String type;

    public Game(int pid, Player prival, String ptype) {
        id = pid;
        rival = prival;
        type = ptype;
    }

    public Player getRival() {
        return rival;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
