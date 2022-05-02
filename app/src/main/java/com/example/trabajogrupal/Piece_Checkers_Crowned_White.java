package com.example.trabajogrupal;

public class Piece_Checkers_Crowned_White extends Piece_Checkers_White
{
    public Piece_Checkers_Crowned_White(String pName, String pColor, int pPosX, int pPosY) {
        super(pName, pColor, pPosX, pPosY);
    }

    public void checkMovement()
    {
        super.checkMovement();

        checkMovementJumpDownLeft();
        checkMovementJumpDownRight();
        if (firstMove)
        {
            checkMovementDownLeft();
            checkMovementDownRight();
        }

    }
}
