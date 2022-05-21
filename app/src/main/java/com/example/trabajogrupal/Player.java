package com.example.trabajogrupal;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Player {
    private String username, pais, email;
    private int eloCheckers, eloChess;
    private ArrayList<Integer> rankELO;
    private ArrayList<Game> gamesInCourse;
    private ArrayList<Game> gamesFinished;
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
        gamesInCourse = new ArrayList<Game>();
        gamesFinished = new ArrayList<Game>();
    }

    public String getEmail() {
        return email;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getEloCheckers(){return eloCheckers;}

    public int getEloChess() {
        return eloChess;
    }

    public void setEloCheckers(int elo){eloCheckers=elo;}

    public void setEloChess(int elo) {eloChess=elo;}

    public String getUsername() {
        return username;
    }

    public void setPais(String Pais) {
        pais = Pais;
    }

    public String getPais() {
        return pais;
    }
    public void addInCourse(Game game)
    {
        gamesInCourse.add(game);
    }
    public void removeInCourse(Game game)
    {
        gamesInCourse.remove(game);
    }
    public void finishGame(Game game)
    {
        gamesFinished.add(game);
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return username;
    }

    public ArrayList<Game> getGamesInCourse() {
        return gamesInCourse;
    }

    public ArrayList<Game> getGamesFinished() {
        return gamesFinished;
    }
}
