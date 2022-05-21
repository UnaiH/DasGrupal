package com.example.trabajogrupal;

public class GameFinished  extends Game {
    private boolean won;

    public GameFinished(int pid, Player prival, String ptype, boolean pwon) {
        super(pid, prival, ptype);
        won = pwon;
    }

    public boolean isWon() {
        return won;
    }
}
