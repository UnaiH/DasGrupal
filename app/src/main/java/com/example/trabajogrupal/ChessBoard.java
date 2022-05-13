package com.example.trabajogrupal;

import android.util.Log;

import java.util.ArrayList;

public class ChessBoard
{
    private Piece[][] board;

    public ChessBoard()
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
    }

    public Piece returnPiece(int x, int y)
    {
        return board[x][y];
    }
    public void addPiece(Piece p, int x, int y)
    {
        board[x][y]=p;
    }
    public void removePiece(int x, int y)
    {
        board[x][y]=null;
    }
    public boolean movePiece(int x, int y, int x2, int y2)
    {
        Piece p = board[x][y];
        board[x][y]=null;
        if (p instanceof Piece_Chess_Pawn_White && y2==8)
        {
            Piece crownedP = new Piece_Chess_Queen_White(p.name,"White",x2,y2);
            board[x2][y2]=crownedP;
            return true;
        }
        else if (p instanceof Piece_Chess_Pawn_Black && y2==1)
        {
            Piece crownedP = new Piece_Chess_Queen_Black(p.name,"Black",x2,y2);
            board[x2][y2]=crownedP;
            return true;
        }
        else
        {
            p.changePosition(x2,y2);
            board[x2][y2]=p;
            return false;
        }
    }

    public ArrayList<Integer> availableMoves(int x, int y)
    {
        ArrayList<Integer> moves = new ArrayList<>();
        Piece p = board[x][y];
        Log.i("Chess","tipo de pieza: " + p.getClass().getName());
        if (p instanceof Piece_Chess_Pawn_White)
        {
            moves = checkMovementForward(p, moves);
            moves = checkMovementEatForwardDiagonal(p, moves);

        }
        else if (p instanceof Piece_Chess_Pawn_Black)
        {
            moves = checkMovementBackward(p, moves);
            moves = checkMovementEatBackwardDiagonal(p, moves);
        }
        else if (p instanceof Piece_Chess_Rook_White || p instanceof Piece_Chess_Rook_Black)
        {
            moves = checkMovementHorizontal(p, moves);
        }
        else if (p instanceof Piece_Chess_Knight_White || p instanceof Piece_Chess_Knight_Black)
        {
            moves = checkMovementJumpL(p, moves);
        }
        else if (p instanceof Piece_Chess_Bishop_White || p instanceof Piece_Chess_Bishop_Black)
        {
            moves = checkMovementDiagonal(p, moves);
        }
        else if (p instanceof Piece_Chess_Queen_White || p instanceof Piece_Chess_Queen_Black)
        {
            moves = checkMovementHorizontal(p, moves);
            moves = checkMovementDiagonal(p, moves);
        }
        else if (p instanceof Piece_Chess_King_White || p instanceof Piece_Chess_King_Black)
        {
            moves = checkMovementAround(p, moves);
        }
        else
        {
            Log.i("Checkers", "Piece: " + x + "-" + y + " has wrong type.");
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementForward(Piece currentPiece, ArrayList<Integer> moves)
    {
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int finalX = posX;
        int finalY = posY+1;
        if (finalY >=9)
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

    private ArrayList<Integer> checkMovementBackward(Piece currentPiece, ArrayList<Integer> moves)
    {
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int finalX = posX;
        int finalY = posY-1;
        if (finalY <=0)
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

    private ArrayList<Integer> checkMovementEatForwardDiagonal(Piece currentPiece, ArrayList<Integer> moves)
    {
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int finalX = posX-1;
        int finalX2 = posX+1;
        int finalY = posY+1;
        if (finalY >=9)
        {
            return moves;
        }
        Piece finalPiece = board[finalX][finalY];
        Piece finalPiece2 = board[finalX2][finalY];
        if (finalPiece!= null && finalPiece.color.equals("Black"))
        {
            moves.add(finalX);
            moves.add(finalY);
        }
        if (finalPiece2!= null && finalPiece2.color.equals("Black"))
        {
            moves.add(finalX2);
            moves.add(finalY);
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementEatBackwardDiagonal(Piece currentPiece, ArrayList<Integer> moves)
    {
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        int finalX = posX-1;
        int finalX2 = posX+1;
        int finalY = posY-1;
        if (finalY <=0)
        {
            return moves;
        }
        Piece finalPiece = board[finalX][finalY];
        Piece finalPiece2 = board[finalX2][finalY];
        if (finalPiece!= null && finalPiece.color.equals("White"))
        {
            moves.add(finalX);
            moves.add(finalY);
        }
        if (finalPiece2!= null && finalPiece2.color.equals("White"))
        {
            moves.add(finalX2);
            moves.add(finalY);
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementHorizontal(Piece currentPiece, ArrayList<Integer> moves)
    {
        String color = currentPiece.color;
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];

        Piece finalPiece;
        if (color.equals("White"))
        {
            for (int x=posX+1; x<9; x++)
            {
                finalPiece=board[x][posY];
                if (finalPiece==null || finalPiece.color.equals("Black"))
                {
                    moves.add(x);
                    moves.add(posY);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int x=posX-1; x>0; x--)
            {
                finalPiece=board[x][posY];
                if (finalPiece==null || finalPiece.color.equals("Black"))
                {
                    moves.add(x);
                    moves.add(posY);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int y=posY+1; y<9; y++)
            {
                finalPiece=board[posX][y];
                if (finalPiece==null || finalPiece.color.equals("Black"))
                {
                    moves.add(posX);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int y=posY-1; y>0; y--)
            {
                finalPiece=board[posX][y];
                if (finalPiece==null || finalPiece.color.equals("Black"))
                {
                    moves.add(posX);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }

        }
        else if (color.equals("Black"))
        {

            for (int x=posX+1; x<9; x++)
            {
                finalPiece=board[x][posY];
                if (finalPiece==null || finalPiece.color.equals("White"))
                {
                    moves.add(x);
                    moves.add(posY);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int x=posX-1; x>0; x--)
            {
                finalPiece=board[x][posY];
                if (finalPiece==null || finalPiece.color.equals("White"))
                {
                    moves.add(x);
                    moves.add(posY);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int y=posY+1; y<9; y++)
            {
                finalPiece=board[posX][y];
                if (finalPiece==null || finalPiece.color.equals("White"))
                {
                    moves.add(posX);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int y=posY-1; y>0; y--)
            {
                finalPiece=board[posX][y];
                if (finalPiece==null || finalPiece.color.equals("White"))
                {
                    moves.add(posX);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        else
        {
            Log.i("Checker","Piece in " + posX + "-" + posY + " is of " + color + " color");
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementDiagonal(Piece currentPiece, ArrayList<Integer> moves)
    {
        String color = currentPiece.color;
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];

        Piece finalPiece;
        if (color.equals("White"))
        {
            for (int x=posX+1, y=posY+1; x<9 && y<9; x++, y++)
            {
                finalPiece=board[x][y];
                if (finalPiece==null || finalPiece.color.equals("Black"))
                {
                    moves.add(x);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int x=posX-1, y=posY+1; x>0 && y<9; x--, y++)
            {
                finalPiece=board[x][y];
                if (finalPiece==null || finalPiece.color.equals("Black"))
                {
                    moves.add(x);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int x=posX+1, y=posY-1; x<9 && y>0; x++, y--)
            {
                finalPiece=board[x][y];
                if (finalPiece==null || finalPiece.color.equals("Black"))
                {
                    moves.add(x);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int x=posX-1, y=posY-1; x>0 && y>0; x--, y--)
            {
                finalPiece=board[x][y];
                if (finalPiece==null || finalPiece.color.equals("Black"))
                {
                    moves.add(x);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }

        }
        else if (color.equals("Black"))
        {

            for (int x=posX+1, y=posY+1; x<9 && y<9; x++, y++)
            {
                finalPiece=board[x][y];
                if (finalPiece==null || finalPiece.color.equals("White"))
                {
                    moves.add(x);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int x=posX-1, y=posY+1; x>0 && y<9; x--, y++)
            {
                finalPiece=board[x][y];
                if (finalPiece==null || finalPiece.color.equals("White"))
                {
                    moves.add(x);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int x=posX+1, y=posY-1; x<9 && y>0; x++, y--)
            {
                finalPiece=board[x][y];
                if (finalPiece==null || finalPiece.color.equals("White"))
                {
                    moves.add(x);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
            for (int x=posX-1, y=posY-1; x>0 && y>0; x--, y--)
            {
                finalPiece=board[x][y];
                if (finalPiece==null || finalPiece.color.equals("White"))
                {
                    moves.add(x);
                    moves.add(y);
                    if(finalPiece!=null)
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        else
        {
            Log.i("Checker","Piece in " + posX + "-" + posY + " is of " + color + " color");
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementJumpL(Piece currentPiece, ArrayList<Integer> moves)
    {
        String color = currentPiece.color;
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        Piece finalPiece;
        Integer[] cambioCoordenadas= {-2,1,-1,2,1,2,2,1,2,-1,1,-2,-1,-2,-2,-1};

        if (color.equals("White"))
        {
            for (int i=0; i<cambioCoordenadas.length; i+=2)
            {
                int cambioX = cambioCoordenadas[i];
                int cambioY = cambioCoordenadas[i+1];
                int finalX= posX+cambioX;
                int finalY= posY+cambioY;
                if(finalX>=1 && finalX<=8 && finalY>=1 && finalY<=8)
                {
                    finalPiece=board[finalX][finalY];
                    if(finalPiece==null || finalPiece.color.equals("Black"))
                    {
                        moves.add(finalX);
                        moves.add(finalY);
                    }
                }
            }
        }
        else if (color.equals("Black"))
        {
            for (int i=0; i<cambioCoordenadas.length; i+=2)
            {
                int cambioX = cambioCoordenadas[i];
                int cambioY = cambioCoordenadas[i+1];
                int finalX= posX+cambioX;
                int finalY= posY+cambioY;
                if(finalX>=1 && finalX<=8 && finalY>=1 && finalY<=8)
                {
                    finalPiece=board[finalX][finalY];
                    if(finalPiece==null || finalPiece.color.equals("White"))
                    {
                        moves.add(finalX);
                        moves.add(finalY);
                    }
                }
            }
        }
        else
        {
            Log.i("Checker","Piece in " + posX + "-" + posY + " is of " + color + " color");
        }
        return moves;
    }

    private ArrayList<Integer> checkMovementAround(Piece currentPiece, ArrayList<Integer> moves)
    {
        String color = currentPiece.color;
        int posX = currentPiece.returnPosition()[0];
        int posY = currentPiece.returnPosition()[1];
        Piece finalPiece;
        Integer[] cambioCoordenadas= {-1,1,0,1,1,1,1,0,1,-1,0,-1,-1,-1,-1,0};

        if (color.equals("White"))
        {
            for (int i=0; i<cambioCoordenadas.length; i+=2)
            {
                int cambioX = cambioCoordenadas[i];
                int cambioY = cambioCoordenadas[i+1];
                int finalX= posX+cambioX;
                int finalY= posY+cambioY;
                if(finalX>=1 && finalX<=8 && finalY>=1 && finalY<=8)
                {
                    finalPiece=board[finalX][finalY];
                    if(finalPiece==null || finalPiece.color.equals("Black"))
                    {
                        moves.add(finalX);
                        moves.add(finalY);
                    }
                }
            }
        }
        else if (color.equals("Black"))
        {
            for (int i=0; i<cambioCoordenadas.length; i+=2)
            {
                int cambioX = cambioCoordenadas[i];
                int cambioY = cambioCoordenadas[i+1];
                int finalX= posX+cambioX;
                int finalY= posY+cambioY;
                if(finalX>=1 && finalX<=8 && finalY>=1 && finalY<=8)
                {
                    finalPiece=board[finalX][finalY];
                    if(finalPiece==null || finalPiece.color.equals("White"))
                    {
                        moves.add(finalX);
                        moves.add(finalY);
                    }
                }
            }
        }
        else
        {
            Log.i("Checker","Piece in " + posX + "-" + posY + " is of " + color + " color");
        }
        return moves;
    }
}