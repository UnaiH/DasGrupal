package com.example.trabajogrupal;

public class Piece_Checkers_Black extends Piece_Checkers_Father {
    public boolean firstMove;

    public Piece_Checkers_Black(String pName, String pColor, int pPosX, int pPosY) {
        super(pName, pColor, pPosX, pPosY);
        firstMove = true;
    }

    public void checkMovement()
    {
        checkMovementJumpDownLeft();
        checkMovementJumpDownRight();
        if (firstMove) {
            checkMovementDownLeft();
            checkMovementDownRight();
        }
    }
}
