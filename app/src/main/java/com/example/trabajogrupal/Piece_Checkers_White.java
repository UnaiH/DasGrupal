package com.example.trabajogrupal;

import android.util.Log;

public class Piece_Checkers_White extends Piece_Checkers_Father
{
    public boolean firstMove;
    public Piece_Checkers_White(String pName, String pColor, int pPosX, int pPosY)
    {
        super(pName, pColor, pPosX, pPosY);
        firstMove = true;
    }

    public void checkMovement()
    {
        checkMovementJumpUpLeft();
        checkMovementJumpUpRight();
        if (firstMove)
        {
            checkMovementUpLeft();
            checkMovementUpRight();
        }
    }



}
