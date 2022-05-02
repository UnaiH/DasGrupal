package com.example.trabajogrupal;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Integer> rankELO;
    private ArrayList<Game> gamesInCourse;
    private ArrayList<Game> gamesFinished;
    public Player(String pName){
        name=pName;
    }
    public void addInCourse(Game game){
        gamesInCourse.add(game);
    }
    public void addFinished(Game game){
        gamesInCourse.add(game);
    }
}
