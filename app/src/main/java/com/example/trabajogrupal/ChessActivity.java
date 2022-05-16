package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ChessActivity extends GameActivity
{
    private Game game;
    private ChessBoard board;
    private ImageButton[][] buttons = new ImageButton[10][10];
    private ArrayList<Integer> redSquares;
    private int[] chosenSquare = new int[2];
    private ImageButton A1;
    private ImageButton B1;
    private ImageButton C1;
    private ImageButton D1;
    private ImageButton E1;
    private ImageButton F1;
    private ImageButton G1;
    private ImageButton H1;
    private ImageButton A2;
    private ImageButton B2;
    private ImageButton C2;
    private ImageButton D2;
    private ImageButton E2;
    private ImageButton F2;
    private ImageButton G2;
    private ImageButton H2;
    private ImageButton A3;
    private ImageButton B3;
    private ImageButton C3;
    private ImageButton D3;
    private ImageButton E3;
    private ImageButton F3;
    private ImageButton G3;
    private ImageButton H3;
    private ImageButton A4;
    private ImageButton B4;
    private ImageButton C4;
    private ImageButton D4;
    private ImageButton E4;
    private ImageButton F4;
    private ImageButton G4;
    private ImageButton H4;
    private ImageButton A5;
    private ImageButton B5;
    private ImageButton C5;
    private ImageButton D5;
    private ImageButton E5;
    private ImageButton F5;
    private ImageButton G5;
    private ImageButton H5;
    private ImageButton A6;
    private ImageButton B6;
    private ImageButton C6;
    private ImageButton D6;
    private ImageButton E6;
    private ImageButton F6;
    private ImageButton G6;
    private ImageButton H6;
    private ImageButton A7;
    private ImageButton B7;
    private ImageButton C7;
    private ImageButton D7;
    private ImageButton E7;
    private ImageButton F7;
    private ImageButton G7;
    private ImageButton H7;
    private ImageButton A8;
    private ImageButton B8;
    private ImageButton C8;
    private ImageButton D8;
    private ImageButton E8;
    private ImageButton F8;
    private ImageButton G8;
    private ImageButton H8;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        setUpBoard();
        loadPieces();
    }

    private void setUpBoard()
    {
        Player player1 = new Player("whitePlayer");
        Player player2 = new Player("blackPlayer");

        Random rand = new Random();
        int id = rand.nextInt(999999);
        game = new Game(id, player1, player2, "Chess");
        board = new ChessBoard();

        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                buttons[i][j]=null;
            }
        }
        redSquares = new ArrayList<>();
        ArrayList<Integer> coordinates = new ArrayList<>();

        A1 = findViewById(R.id.ChessA1); B1 = findViewById(R.id.ChessB1);
        C1 = findViewById(R.id.ChessC1); D1 = findViewById(R.id.ChessD1);
        E1 = findViewById(R.id.ChessE1); F1 = findViewById(R.id.ChessF1);
        G1 = findViewById(R.id.ChessG1); H1 = findViewById(R.id.ChessH1);
        A2 = findViewById(R.id.ChessA2); B2 = findViewById(R.id.ChessB2);
        C2 = findViewById(R.id.ChessC2); D2 = findViewById(R.id.ChessD2);
        E2 = findViewById(R.id.ChessE2); F2 = findViewById(R.id.ChessF2);
        G2 = findViewById(R.id.ChessG2); H2 = findViewById(R.id.ChessH2);
        A3 = findViewById(R.id.ChessA3); B3 = findViewById(R.id.ChessB3);
        C3 = findViewById(R.id.ChessC3); D3 = findViewById(R.id.ChessD3);
        E3 = findViewById(R.id.ChessE3); F3 = findViewById(R.id.ChessF3);
        G3 = findViewById(R.id.ChessG3); H3 = findViewById(R.id.ChessH3);
        A4 = findViewById(R.id.ChessA4); B4 = findViewById(R.id.ChessB4);
        C4 = findViewById(R.id.ChessC4); D4 = findViewById(R.id.ChessD4);
        E4 = findViewById(R.id.ChessE4); F4 = findViewById(R.id.ChessF4);
        G4 = findViewById(R.id.ChessG4); H4 = findViewById(R.id.ChessH4);
        A5 = findViewById(R.id.ChessA5); B5 = findViewById(R.id.ChessB5);
        C5 = findViewById(R.id.ChessC5); D5 = findViewById(R.id.ChessD5);
        E5 = findViewById(R.id.ChessE5); F5 = findViewById(R.id.ChessF5);
        G5 = findViewById(R.id.ChessG5); H5 = findViewById(R.id.ChessH5);
        A6 = findViewById(R.id.ChessA6); B6 = findViewById(R.id.ChessB6);
        C6 = findViewById(R.id.ChessC6); D6 = findViewById(R.id.ChessD6);
        E6 = findViewById(R.id.ChessE6); F6 = findViewById(R.id.ChessF6);
        G6 = findViewById(R.id.ChessG6); H6 = findViewById(R.id.ChessH6);
        A7 = findViewById(R.id.ChessA7); B7 = findViewById(R.id.ChessB7);
        C7 = findViewById(R.id.ChessC7); D7 = findViewById(R.id.ChessD7);
        E7 = findViewById(R.id.ChessE7); F7 = findViewById(R.id.ChessF7);
        G7 = findViewById(R.id.ChessG7); H7 = findViewById(R.id.ChessH7);
        A8 = findViewById(R.id.ChessA8); B8 = findViewById(R.id.ChessB8);
        C8 = findViewById(R.id.ChessC8); D8 = findViewById(R.id.ChessD8);
        E8 = findViewById(R.id.ChessE8); F8 = findViewById(R.id.ChessF8);
        G8 = findViewById(R.id.ChessG8); H8 = findViewById(R.id.ChessH8);

        for (int j=1; j<9; j++)
        {
            for (int i=1; i<9; i++)
            {
                coordinates.add(i);
                coordinates.add(j);
            }
        }
        buttons[1][1]=A1; buttons[2][1]=B1;
        buttons[3][1]=C1; buttons[4][1]=D1;
        buttons[5][1]=E1; buttons[6][1]=F1;
        buttons[7][1]=G1; buttons[8][1]=H1;
        buttons[1][2]=A2; buttons[2][2]=B2;
        buttons[3][2]=C2; buttons[4][2]=D2;
        buttons[5][2]=E2; buttons[6][2]=F2;
        buttons[7][2]=G2; buttons[8][2]=H2;
        buttons[1][3]=A3; buttons[2][3]=B3;
        buttons[3][3]=C3; buttons[4][3]=D3;
        buttons[5][3]=E3; buttons[6][3]=F3;
        buttons[7][3]=G3; buttons[8][3]=H3;
        buttons[1][4]=A4; buttons[2][4]=B4;
        buttons[3][4]=C4; buttons[4][4]=D4;
        buttons[5][4]=E4; buttons[6][4]=F4;
        buttons[7][4]=G4; buttons[8][4]=H4;
        buttons[1][5]=A5; buttons[2][5]=B5;
        buttons[3][5]=C5; buttons[4][5]=D5;
        buttons[5][5]=E5; buttons[6][5]=F5;
        buttons[7][5]=G5; buttons[8][5]=H5;
        buttons[1][6]=A6; buttons[2][6]=B6;
        buttons[3][6]=C6; buttons[4][6]=D6;
        buttons[5][6]=E6; buttons[6][6]=F6;
        buttons[7][6]=G6; buttons[8][6]=H6;
        buttons[1][7]=A7; buttons[2][7]=B7;
        buttons[3][7]=C7; buttons[4][7]=D7;
        buttons[5][7]=E7; buttons[6][7]=F7;
        buttons[7][7]=G7; buttons[8][7]=H7;
        buttons[1][8]=A8; buttons[2][8]=B8;
        buttons[3][8]=C8; buttons[4][8]=D8;
        buttons[5][8]=E8; buttons[6][8]=F8;
        buttons[7][8]=G8; buttons[8][8]=H8;


        for (int i=0; i<coordinates.size(); i+=2)
        {
            int coordinateX = coordinates.get(i);
            int coordinateY = coordinates.get(i+1);
            ImageButton button = buttons[coordinateX][coordinateY];

            String name = button.toString();
            Piece p;
            if (coordinateY==1)
            {
                if(coordinateX==1 || coordinateX==8)
                {
                    p = new Piece_Chess_Rook_White(name, "White", coordinateX, coordinateY);
                    game.addWhitePiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_white_rook);
                }
                else if(coordinateX==2 || coordinateX==7)
                {
                    p = new Piece_Chess_Knight_White(name, "White", coordinateX, coordinateY);
                    game.addWhitePiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_white_knight);
                }
                else if(coordinateX==3 || coordinateX==6)
                {
                    p = new Piece_Chess_Bishop_White(name, "White", coordinateX, coordinateY);
                    game.addWhitePiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_white_bishop);
                }
                else if(coordinateX==4)
                {
                    p = new Piece_Chess_Queen_White(name, "White", coordinateX, coordinateY);
                    game.addWhitePiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_white_queen);
                }
                else if(coordinateX==5)
                {
                    p = new Piece_Chess_King_White(name, "White", coordinateX, coordinateY);
                    game.addWhitePiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_white_king);
                }
                else
                {
                    Log.i("Chess","illegal coordinate:" + coordinateX + "-" + coordinateY);
                }

            }
            else if(coordinateY==2)
            {
                p = new Piece_Chess_Pawn_White(name, "White", coordinateX, coordinateY);
                game.addWhitePiece(p);
                board.addPiece(p,coordinateX,coordinateY);
                button.setImageResource(R.drawable.chess_white_pawn);
            }
            else if(coordinateY==7)
            {
                p = new Piece_Chess_Pawn_Black(name, "Black", coordinateX, coordinateY);
                game.addBlackPiece(p);
                board.addPiece(p,coordinateX,coordinateY);
                button.setImageResource(R.drawable.chess_black_pawn);
            }
            else if(coordinateY==8)
            {
                if(coordinateX==1 || coordinateX==8)
                {
                    p = new Piece_Chess_Rook_Black(name, "Black", coordinateX, coordinateY);
                    game.addBlackPiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_black_rook);
                }
                else if(coordinateX==2 || coordinateX==7)
                {
                    p = new Piece_Chess_Knight_Black(name, "Black", coordinateX, coordinateY);
                    game.addBlackPiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_black_knight);
                }
                else if(coordinateX==3 || coordinateX==6)
                {
                    p = new Piece_Chess_Bishop_Black(name, "Black", coordinateX, coordinateY);
                    game.addBlackPiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_black_bishop);
                }
                else if(coordinateX==4)
                {
                    p = new Piece_Chess_Queen_Black(name, "Black", coordinateX, coordinateY);
                    game.addBlackPiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_black_queen);
                }
                else if(coordinateX==5)
                {
                    p = new Piece_Chess_King_Black(name, "Black", coordinateX, coordinateY);
                    game.addBlackPiece(p);
                    board.addPiece(p,coordinateX,coordinateY);
                    button.setImageResource(R.drawable.chess_black_king);
                }
                else
                {
                    Log.i("Chess","illegal coordinate:" + coordinateX + "-" + coordinateY);
                }
            }
            else
            {
                if ((coordinateX+coordinateY)%2==0)
                {
                    button.setImageResource(R.drawable.checkers_empty);
                }
                else
                {
                    button.setImageResource(R.drawable.checkers_empty_white);
                }
            }
        }
    }

    public void onClickBoard(View v)
    {
        switch (v.getId())
        {
            case R.id.ChessA1:
                clickSquare(1,1);
                break;
            case R.id.ChessB1:
                clickSquare(2,1);
                break;
            case R.id.ChessC1:
                clickSquare(3,1);
                break;
            case R.id.ChessD1:
                clickSquare(4,1);
                break;
            case R.id.ChessE1:
                clickSquare(5,1);
                break;
            case R.id.ChessF1:
                clickSquare(6,1);
                break;
            case R.id.ChessG1:
                clickSquare(7,1);
                break;
            case R.id.ChessH1:
                clickSquare(8,1);
                break;
            case R.id.ChessA2:
                clickSquare(1,2);
                break;
            case R.id.ChessB2:
                clickSquare(2,2);
                break;
            case R.id.ChessC2:
                clickSquare(3,2);
                break;
            case R.id.ChessD2:
                clickSquare(4,2);
                break;
            case R.id.ChessE2:
                clickSquare(5,2);
                break;
            case R.id.ChessF2:
                clickSquare(6,2);
                break;
            case R.id.ChessG2:
                clickSquare(7,2);
                break;
            case R.id.ChessH2:
                clickSquare(8,2);
                break;
            case R.id.ChessA3:
                clickSquare(1,3);
                break;
            case R.id.ChessB3:
                clickSquare(2,3);
                break;
            case R.id.ChessC3:
                clickSquare(3,3);
                break;
            case R.id.ChessD3:
                clickSquare(4,3);
                break;
            case R.id.ChessE3:
                clickSquare(5,3);
                break;
            case R.id.ChessF3:
                clickSquare(6,3);
                break;
            case R.id.ChessG3:
                clickSquare(7,3);
                break;
            case R.id.ChessH3:
                clickSquare(8,3);
                break;
            case R.id.ChessA4:
                clickSquare(1,4);
                break;
            case R.id.ChessB4:
                clickSquare(2,4);
                break;
            case R.id.ChessC4:
                clickSquare(3,4);
                break;
            case R.id.ChessD4:
                clickSquare(4,4);
                break;
            case R.id.ChessE4:
                clickSquare(5,4);
                break;
            case R.id.ChessF4:
                clickSquare(6,4);
                break;
            case R.id.ChessG4:
                clickSquare(7,4);
                break;
            case R.id.ChessH4:
                clickSquare(8,4);
                break;
            case R.id.ChessA5:
                clickSquare(1,5);
                break;
            case R.id.ChessB5:
                clickSquare(2,5);
                break;
            case R.id.ChessC5:
                clickSquare(3,5);
                break;
            case R.id.ChessD5:
                clickSquare(4,5);
                break;
            case R.id.ChessE5:
                clickSquare(5,5);
                break;
            case R.id.ChessF5:
                clickSquare(6,5);
                break;
            case R.id.ChessG5:
                clickSquare(7,5);
                break;
            case R.id.ChessH5:
                clickSquare(8,5);
                break;
            case R.id.ChessA6:
                clickSquare(1,6);
                break;
            case R.id.ChessB6:
                clickSquare(2,6);
                break;
            case R.id.ChessC6:
                clickSquare(3,6);
                break;
            case R.id.ChessD6:
                clickSquare(4,6);
                break;
            case R.id.ChessE6:
                clickSquare(5,6);
                break;
            case R.id.ChessF6:
                clickSquare(6,6);
                break;
            case R.id.ChessG6:
                clickSquare(7,6);
                break;
            case R.id.ChessH6:
                clickSquare(8,6);
                break;
            case R.id.ChessA7:
                clickSquare(1,7);
                break;
            case R.id.ChessB7:
                clickSquare(2,7);
                break;
            case R.id.ChessC7:
                clickSquare(3,7);
                break;
            case R.id.ChessD7:
                clickSquare(4,7);
                break;
            case R.id.ChessE7:
                clickSquare(5,7);
                break;
            case R.id.ChessF7:
                clickSquare(6,7);
                break;
            case R.id.ChessG7:
                clickSquare(7,7);
                break;
            case R.id.ChessH7:
                clickSquare(8,7);
                break;
            case R.id.ChessA8:
                clickSquare(1,8);
                break;
            case R.id.ChessB8:
                clickSquare(2,8);
                break;
            case R.id.ChessC8:
                clickSquare(3,8);
                break;
            case R.id.ChessD8:
                clickSquare(4,8);
                break;
            case R.id.ChessE8:
                clickSquare(5,8);
                break;
            case R.id.ChessF8:
                clickSquare(6,8);
                break;
            case R.id.ChessG8:
                clickSquare(7,8);
                break;
            case R.id.ChessH8:
                clickSquare(8,8);
                break;
        }
    }

    private void clickSquare(int x, int y)
    {
        if (redSquares.size()>0)
        {
            for (int i=0; i<redSquares.size(); i+=2)
            {
                int posX = redSquares.get(i);
                int posY = redSquares.get(i+1);
                if(posX==x && posY==y)
                {
                    movePiece(chosenSquare[0],chosenSquare[1],x,y);
                    unmarkSquares();
                    game.changeTurn();
                    return;
                }
            }
        }
        unmarkSquares();
        chosenSquare[0]=x;
        chosenSquare[1]=y;
        ArrayList<Integer> moves = new ArrayList<>();
        Piece p = board.returnPiece(x,y);
        if (p==null || p instanceof Piece_Border_Board)
        {
            return;
        }
        if (p.color.equals("White"))
        {
            if (game.currentTurn().equals("Black"))
            {
                return;
            }
            moves = board.availableMoves(x, y);
        }
        else if (p.color.equals("Black"))
        {
            if (game.currentTurn().equals("White"))
            {
                return;
            }
            moves = board.availableMoves(x, y);
        }

        for (int i=0; i<moves.size(); i+=2)
        {
            markSquare(moves.get(i),moves.get(i+1));
            Log.i("Chess",moves.get(i) + "-" + moves.get(i+1));
        }

    }

    //transforms the image of a button to a red square
    private void markSquare(int x, int y)
    {
        ImageButton a = buttons[x][y];
        a.setImageResource(R.drawable.checkers_red_square);
        redSquares.add(x);
        redSquares.add(y);
    }

    //searches the ImageButtons that have red squres and turns them to the correct
    private void unmarkSquares()
    {
        for (int i=0; i<redSquares.size(); i+=2)
        {
            int x = redSquares.get(i);
            int y = redSquares.get(i+1);
            Piece pieceToChange = board.returnPiece(x, y);
            drawProperPiece(pieceToChange, x , y);
        }
        redSquares = new ArrayList<>();
    }

    private void drawProperPiece(Piece piece, int x, int y)
    {
        ImageButton image = buttons[x][y];
        if (piece instanceof Piece_Chess_Pawn_White)
        {
            image.setImageResource(R.drawable.chess_white_pawn);
        }
        else if(piece instanceof Piece_Chess_Pawn_Black)
        {
            image.setImageResource(R.drawable.chess_black_pawn);
        }
        else if(piece instanceof Piece_Chess_Rook_White)
        {
            image.setImageResource(R.drawable.chess_white_rook);
        }
        else if(piece instanceof Piece_Chess_Rook_Black)
        {
            image.setImageResource(R.drawable.chess_black_rook);
        }
        else if(piece instanceof Piece_Chess_Knight_White)
        {
            image.setImageResource(R.drawable.chess_white_knight);
        }
        else if(piece instanceof Piece_Chess_Knight_Black)
        {
            image.setImageResource(R.drawable.chess_black_knight);
        }
        else if(piece instanceof Piece_Chess_Bishop_White)
        {
            image.setImageResource(R.drawable.chess_white_bishop);
        }
        else if(piece instanceof Piece_Chess_Bishop_Black)
        {
            image.setImageResource(R.drawable.chess_black_bishop);
        }
        else if(piece instanceof Piece_Chess_Queen_White)
        {
            image.setImageResource(R.drawable.chess_white_queen);
        }
        else if(piece instanceof Piece_Chess_Queen_Black)
        {
            image.setImageResource(R.drawable.chess_black_queen);
        }
        else if(piece instanceof Piece_Chess_King_White)
        {
            image.setImageResource(R.drawable.chess_white_king);
        }
        else if(piece instanceof Piece_Chess_King_Black)
        {
            image.setImageResource(R.drawable.chess_black_king);
        }
        else if(piece==null)
        {
            if ((x+y)%2==0)
            {
                image.setImageResource(R.drawable.checkers_empty);
            }
            else
            {
                image.setImageResource(R.drawable.checkers_empty_white);
            }
        }
        else
        {
            Log.i("Chess", "Piece: " + x + "-" + y + " has wrong type.");
        }
    }

    private void movePiece(int posX, int posY, int finalX, int finalY)
    {
        Piece startPiece = board.returnPiece(posX, posY);
        if (startPiece!=null)
        {
            boolean[] specialConditions;
            boolean promoted;
            boolean castled;
            boolean finished;
            specialConditions = board.movePiece(posX, posY, finalX, finalY);
            promoted = specialConditions[0];
            castled = specialConditions[1];
            finished = specialConditions[2];
            drawProperPiece(null,posX,posY);
            if (promoted)
            {
                Piece crownedPiece = board.returnPiece(finalX,finalY);
                if (startPiece instanceof Piece_Chess_Pawn_White)
                {
                    game.removeWhitePiece(startPiece);
                    game.addWhitePiece(crownedPiece);
                }
                if (startPiece instanceof Piece_Chess_Pawn_Black)
                {
                    game.removeBlackPiece(startPiece);
                    game.addBlackPiece(crownedPiece);
                }
                drawProperPiece(crownedPiece,finalX,finalY);
            }
            drawProperPiece(startPiece,finalX,finalY);
            if (castled)
            {
                if (finalX==3 && finalY==1)
                {
                    movePiece(1,1,4,1);
                }
                else if(finalX==7 && finalY==1)
                {
                    movePiece(8,1,6,1);
                }
                else if (finalX==3 && finalY==8)
                {
                    movePiece(1,8,4,8);
                }
                else if(finalX==7 && finalY==8)
                {
                    movePiece(8,8,6,8);
                }
                else
                {
                    Log.i("Chess", "Incorrect castling");
                }
            }
            if(finished)
            {
                Toast.makeText(this,"You win", Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            Log.i("Chess", "Piece: " + posX + "-" + posY + " has wrong type.");
        }
    }


    private void loadPieces()
    {
        ArrayList<Piece> whites = game.returnWhitePieces();
        ArrayList<Piece> blacks = game.returnBlackPieces();
        Log.i("Chess", "Number of white pieces : " + whites.size());
        Log.i("Chess", "Number of black pieces : " + blacks.size());
        Piece p;
        int id = game.getId();
        String type;
        int posX;
        int posY;
        for (int i=0; i<whites.size(); i++)
        {
            p = whites.get(i);
            type = p.getClass().getName();
            posX=p.posX;
            posY=p.posY;
            insertPiece(id, type, posX, posY);
        }
    }



    //Para que no se salga de la App
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        Intent i = new Intent(this, SelectMenuActivity.class);
        startActivity(i);
    }
}