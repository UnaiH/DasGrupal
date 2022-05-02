package com.example.trabajogrupal;

public class Piece_Checkers_Father extends Piece
{
    public Piece_Checkers_Father(String pName, String pColor, int pPosX, int pPosY) {
        super(pName, pColor, pPosX, pPosY);
    }

    protected boolean checkMovementUpLeft()
    {
        return true;
    }

    protected boolean checkMovementUpRight()
    {
        return true;
    }

    protected boolean checkMovementDownLeft()
    {
        return true;
    }

    protected boolean checkMovementDownRight()
    {
        return true;
    }

    protected boolean checkMovementJumpUpLeft()
    {
        return true;
    }

    protected boolean checkMovementJumpUpRight()
    {
        return true;
    }

    protected boolean checkMovementJumpDownLeft()
    {
        return true;
    }

    protected boolean checkMovementJumpDownRight()
    {
        return true;
    }
}
