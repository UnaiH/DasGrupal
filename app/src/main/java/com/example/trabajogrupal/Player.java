package com.example.trabajogrupal;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Player
{
    private String name;
    private ArrayList<Integer> rankELO;
    private String pais;
    private ArrayList<GameInCourse> gamesInCourse;
    private ArrayList<GameFinished> gamesFinished;
    private Bitmap image;
    public Player(String pName, int pEloCheckers, int pEloChess)
    {
        name=pName;
        rankELO = new ArrayList<>();
        rankELO.add(pEloCheckers);
        rankELO.add(pEloChess);
        gamesInCourse = new ArrayList<GameInCourse>();
        gamesFinished = new ArrayList<GameFinished>();
    }
    public Integer getElo(Integer pos){
        return rankELO.get(pos);
    }
    public void setPais(String Pais){
        pais=Pais;
    }
    public String getPais(){
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
