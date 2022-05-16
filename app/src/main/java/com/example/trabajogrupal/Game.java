package com.example.trabajogrupal;

import java.util.ArrayList;

public class Game
{
    private int id;
    private Player player1;  //white
    private Player player2;  //black
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;
    private String gameType;
    private String nextToMove;


    public Game(int pId, Player p1, Player p2, String pGameType)
    {
        id=pId;
        player1=p1;
        player2=p2;
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        gameType = pGameType;
        nextToMove="White";
    }
    public int getId()
    {
        return id;
    }
    public ArrayList<Piece> returnWhitePieces()
    {
        return this.whitePieces;
    }
    public ArrayList<Piece> returnBlackPieces()
    {
        return this.whitePieces;
    }
    public void addWhitePiece(Piece p)
    {
        whitePieces.add(p);
    }
    public void addBlackPiece(Piece p)
    {
        blackPieces.add(p);
    }
    public void removeWhitePiece(Piece p)
    {
        whitePieces.remove(p);
    }
    public void removeBlackPiece(Piece p)
    {
        blackPieces.remove(p);
    }
    public String currentTurn()
    {
        return nextToMove;
    }
    public void changeTurn()
    {
        if (nextToMove.equals("White"))
        {
            nextToMove="Black";
        }
        else
        {
            nextToMove="White";
        }
    }


}
