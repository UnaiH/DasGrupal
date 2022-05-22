package com.example.trabajogrupal;

public class Game {
    private int id;
    private Player player1;
    private Player player2;
    private String type;
    private String nextTurn;
    private String winner;

    public Game(int pid, Player pplayer1, Player pplayer2, String ptype, String pnextTurn) {
        id = pid;
        player1 = pplayer1;
        player2 = pplayer2;
        type = ptype;
        nextTurn = pnextTurn;
        winner = null;
    }

    public Player getRival(String currentPlayer) {
        Player rival = null;
        if (currentPlayer.equals(player1.getEmail())) {
            rival = player2;
        }
        else if (currentPlayer.equals(player2.getEmail())) {
            rival = player1;
        }
        return rival;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void changeTurn() {
        if (nextTurn.equals("White")) {
            nextTurn = "Black";
        }
        else if (nextTurn.equals("Black")) {
            nextTurn = "White";
        }
    }

    public boolean isMyTurn(String currentPlayer) {
        Player player = null;
        if (nextTurn.equals("White")) {
            player = player1;
        }
        else if (nextTurn.equals("Black")) {
            player = player2;
        }
        return currentPlayer.equals(player.getEmail());
    }

    public boolean iAmWinner(String currentPlayer) {
        return currentPlayer.equals(winner);
    }

    public boolean isFinished() {
        return winner!=null;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
