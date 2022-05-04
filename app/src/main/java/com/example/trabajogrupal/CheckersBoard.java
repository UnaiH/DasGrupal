package com.example.trabajogrupal;

import android.util.Log;

import java.util.ArrayList;

public class CheckersBoard
{
    private Piece[][] board;
    private boolean firstMove;

    public CheckersBoard()
    {
        board = new Piece[10][10];
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                board[i][j]=null;
                if (i==0 || j==0 || i ==9 || j==9)
                {
                    Piece pieceBorder = new Piece_Border_Board("useless", "Grey", i,j);
                }
            }
        }
        firstMove=true;
    }

    public Piece returnPiece(int x, int y)
    {
        return board[x][y];
    }
    public void addPiece(Piece p, int x, int y)
    {
        board[x][y]=p;
    }
    public void movePiece(int x, int y, int x2, int y2)
    {
        board[x2][y2]=board[x][y];
        board[x][y]=null;
    }

    public ArrayList<Integer> availableMoves(int x, int y)
    {
        ArrayList<Integer> moves = new ArrayList<>();
        Piece p = board[x][y];
        Log.i("Checkers","posicion = " + x + "-" + y);
        Log.i("Checkers", "pieza " + p.toString());
        if (p instanceof Piece_Checkers_White)
        {
            moves = checkMovementJumpUpLeft(p, moves);
            moves = checkMovementJumpUpRight(p, moves);
            if (firstMove)
            {
                moves = checkMovementUpLeft(p, moves);
                moves = checkMovementUpRight(p, moves);
            }
            if (p instanceof  Piece_Checkers_Crowned_White)
            {
                moves = checkMovementJumpDownLeft(p, moves);
                moves = checkMovementJumpDownRight(p, moves);
                if (firstMove)
                {
                    moves = checkMovementDownLeft(p, moves);
                    moves = checkMovementDownRight(p, moves);
                }
            }
        }
        else if (p instanceof Piece_Checkers_Black)
        {
            moves = checkMovementJumpDownLeft(p, moves);
            moves = checkMovementJumpDownRight(p, moves);
            if (firstMove)
            {
                moves = checkMovementDownLeft(p, moves);
                moves = checkMovementDownRight(p, moves);
            }
            if (p instanceof  Piece_Checkers_Crowned_Black)
            {
                moves = checkMovementJumpDownLeft(p, moves);
                moves = checkMovementJumpDownRight(p, moves);
                if (firstMove)
                {
                    moves = checkMovementDownLeft(p, moves);
                    moves = checkMovementDownRight(p, moves);
                }
            }


        }
        else
        {
            Log.i("Checkers", "Piece: " + x + "-" + y + " has wrong type.");
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementUpLeft(Piece currentPiece, ArrayList<Integer> moves)
    {
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        Log.i("Checkers","posX " + posX);
        Log.i("Checkers","posY " + posY);
        int finalX = posX-1;
        int finalY = posY+1;
        if (finalX<=0 || finalY >=9)
        {
            return moves;
        }
        Piece finalPiece = board[finalX][finalY];

        if (finalPiece == null)
        {
            Log.i("Checkers","jumpleft");
            moves.add(finalX);
            moves.add(finalY);
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementUpRight(Piece currentPiece, ArrayList<Integer> moves)
    {
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int finalX = posX+1;
        int finalY = posY+1;
        if (finalX>=9 || finalY >=9)
        {
            return moves;
        }
        Piece finalPiece = board[finalX][finalY];
        if (finalPiece == null)
        {
            moves.add(finalX);
            moves.add(finalY);

        }
        return moves;
    }

    private ArrayList<Integer> checkMovementDownLeft(Piece currentPiece, ArrayList<Integer> moves)
    {
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int finalX = posX-1;
        int finalY = posY-1;
        if (finalX<=0 || finalY <=0)
        {
            return moves;
        }
        Piece finalPiece = board[finalX][finalY];
        if (finalPiece == null)
        {
            moves.add(finalX);
            moves.add(finalY);
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementDownRight(Piece p, ArrayList<Integer> moves)
    {
        int posX = p.returnPosition()[0];
        int posY = p.returnPosition()[1];
        int finalX = posX+1;
        int finalY = posY-1;
        if (finalX>=9 || finalY <=0)
        {
            return moves;
        }
        Piece finalPiece = board[finalX][finalY];
        if (finalPiece == null)
        {
            moves.add(finalX);
            moves.add(finalY);

        }
        return moves;
    }

    private ArrayList<Integer> checkMovementJumpUpLeft(Piece currentPiece, ArrayList<Integer> moves)
    {
        String color = currentPiece.color;
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int middleX = posX-1;
        int middleY = posY+1;
        int finalX = posX-2;
        int finalY = posY+2;
        if (finalX<=0 || finalY >=9)
        {
            return moves;
        }
        Piece middlePiece = board[middleX][middleY];
        Piece finalPiece = board[finalX][finalY];
        if (color.equals("White"))
        {
            if (middlePiece instanceof Piece_Checkers_Black && finalPiece==null)
            {
                moves.add(finalX);
                moves.add(finalY);
            }
        }
        else if (color.equals("Black"))
        {
            if (middlePiece instanceof Piece_Checkers_White && finalPiece==null)
            {
                moves.add(finalX);
                moves.add(finalY);
            }
        }
        else
        {
            Log.i("Checker","Piece in " + posX + "-" + posY + " is of " + color + " color");
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementJumpUpRight(Piece currentPiece, ArrayList<Integer> moves)
    {
        String color = currentPiece.color;
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int middleX = posX+1;
        int middleY = posY+1;
        int finalX = posX+2;
        int finalY = posY+2;
        if (finalX >=9 || finalY >=9)
        {
            return moves;
        }
        Piece middlePiece = board[middleX][middleY];
        Piece finalPiece = board[finalX][finalY];
        if (color.equals("White"))
        {
            if (middlePiece instanceof Piece_Checkers_Black && finalPiece==null)
            {
                moves.add(finalX);
                moves.add(finalY);
            }
        }
        else if (color.equals("Black"))
        {
            if (middlePiece instanceof Piece_Checkers_White && finalPiece==null)
            {
                moves.add(finalX);
                moves.add(finalY);
            }
        }
        else
        {
            Log.i("Checker","Piece in " + posX + "-" + posY + " is of " + color + " color");
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementJumpDownLeft(Piece currentPiece, ArrayList<Integer> moves)
    {
        String color = currentPiece.color;
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int middleX = posX-1;
        int middleY = posY-1;
        int finalX = posX-2;
        int finalY = posY-2;
        if (finalX<=0 || finalY <=0)
        {
            return moves;
        }

        Piece middlePiece = board[middleX][middleY];
        Piece finalPiece = board[finalX][finalY];
        if (color.equals("White"))
        {
            if (middlePiece instanceof Piece_Checkers_Black && finalPiece==null)
            {
                moves.add(finalX);
                moves.add(finalY);
            }
        }
        else if (color.equals("Black"))
        {
            if (middlePiece instanceof Piece_Checkers_White && finalPiece==null)
            {
                moves.add(finalX);
                moves.add(finalY);
            }
        }
        else
        {
            Log.i("Checker","Piece in " + posX + "-" + posY + " is of " + color + " color");
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementJumpDownRight(Piece currentPiece, ArrayList<Integer> moves)
    {
        String color = currentPiece.color;
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int middleX = posX+1;
        int middleY = posY-1;
        int finalX = posX+2;
        int finalY = posY-2;
        if (finalX >=9 || finalY <=0)
        {
            return moves;
        }
        Piece middlePiece = board[middleX][middleY];
        Piece finalPiece = board[finalX][finalY];
        if (color.equals("White"))
        {
            if (middlePiece instanceof Piece_Checkers_Black && finalPiece==null)
            {
                moves.add(finalX);
                moves.add(finalY);
            }
        }
        else if (color.equals("Black"))
        {
            if (middlePiece instanceof Piece_Checkers_White && finalPiece==null)
            {
                moves.add(finalX);
                moves.add(finalY);
            }
        }
        else
        {
            Log.i("Checker","Piece in " + posX + "-" + posY + " is of " + color + " color");
        }
        return moves;
    }
}
