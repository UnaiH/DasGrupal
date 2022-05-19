package com.example.trabajogrupal;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Player {
    private String username, pais, email;
    private int eloCheckers, eloChess;
    private ArrayList<Integer> rankELO;
    private ArrayList<GameInCourse> gamesInCourse;
    private ArrayList<GameFinished> gamesFinished;
    private Bitmap image;
    public Player(String pUsername, String pPais, String pEmail, int pEloCheckers, int pEloChess) {
        username = pUsername;
        pais = pPais;
        email = pEmail;
        eloCheckers = pEloCheckers;
        eloChess = pEloChess;
        rankELO = new ArrayList<>();
        rankELO.add(pEloCheckers);
        rankELO.add(pEloChess);
        gamesInCourse = new ArrayList<GameInCourse>();
        gamesFinished = new ArrayList<GameFinished>();
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
    public void addInCourse(GameInCourse gameInCourse)
    {
        gamesInCourse.add(gameInCourse);
    }
    public void removeInCourse(GameInCourse gameInCourse)
    {
        gamesInCourse.remove(gameInCourse);
    }
    public void finishGame(GameFinished gameFinished)
    {
        gamesFinished.add(gameFinished);
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public ArrayList<GameInCourse> getGamesInCourse() {
        return gamesInCourse;
    }
}
