package com.example.trabajogrupal;

public class GameInCourse extends Game {
    private boolean myTurn;

    public GameInCourse(int pid, Player prival, String ptype) {
        super(pid, prival, ptype);
        myTurn = false;
    }

    public void changeTurn() {
        myTurn = !myTurn;
    }

    public boolean isMyTurn() {
        return myTurn;
    }
}
