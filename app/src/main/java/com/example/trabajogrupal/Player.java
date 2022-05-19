package com.example.trabajogrupal;

import java.util.ArrayList;

public class Player {
    private String username, pais, email;
    private int eloCheckers, eloChess;
    private ArrayList<Integer> rankELO;

    //private ArrayList<Game> gamesInCourse;
    //private ArrayList<Game> gamesFinished;
    public Player(String pUsername, String pPais, String pEmail, int pEloCheckers, int pEloChess) {
        username = pUsername;
        pais = pPais;
        email = pEmail;
        eloCheckers = pEloCheckers;
        eloChess = pEloChess;
        rankELO = new ArrayList<>();
        rankELO.add(pEloCheckers);
        rankELO.add(pEloChess);
        //gamesInCourse = new ArrayList<>();
        //gamesFinished = new ArrayList<>();
    }

    public int getEloCheckers(){return eloCheckers;}

    public int getEloChess() {
        return eloChess;
    }

    public String getUsername() {
        return username;
    }

    public void setPais(String Pais) {
        pais = Pais;
    }

    public String getPais() {
        return pais;
    }
    /*
    public void addInCourse(Game game)
    {
        gamesInCourse.add(game);
    }
    public void finishGame(Game game)
    {
        gamesInCourse.remove(game);
        gamesFinished.add(game);
    }
    */
}
