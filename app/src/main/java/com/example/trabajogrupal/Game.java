package com.example.trabajogrupal;

import java.util.ArrayList;

public class Game
{
    public Player player1;  //white
    public Player player2;  //black
    public ArrayList<Piece> whitePieces;
    public ArrayList<Piece> blackPieces;
    public String nextToMove;

    public Game(Player p1, Player p2)
    {
        player1=p1;
        player2=p2;
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        nextToMove="White";
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
