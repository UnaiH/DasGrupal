package com.example.trabajogrupal;

public class Piece_Chess_Castling extends Piece
{
    private boolean hasMoved;
    public Piece_Chess_Castling(String pName, String pColor, int pPosX, int pPosY)
    {
        super(pName, pColor, pPosX, pPosY);
    }
    public boolean getHasMoved()
    {

        return hasMoved;
    }

    public void setMoved()
    {
        hasMoved=true;
    }
}