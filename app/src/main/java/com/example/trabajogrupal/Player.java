package com.example.trabajogrupal;

import java.util.ArrayList;

public class Player
{
    private String name;
    private ArrayList<Integer> rankELO;
    private String pais;
    //private ArrayList<Game> gamesInCourse;
    //private ArrayList<Game> gamesFinished;
    public Player(String pName, int pEloCheckers, int pEloChess)
    {
        name=pName;
        rankELO = new ArrayList<>();
        rankELO.add(pEloCheckers);
        rankELO.add(pEloChess);
        //gamesInCourse = new ArrayList<>();
        //gamesFinished = new ArrayList<>();
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
