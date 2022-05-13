package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class CheckersActivity extends AppCompatActivity {

    private Game game;
    private CheckersBoard board;
    private ImageButton[][] buttons = new ImageButton[10][10];
    private ArrayList<Integer> redSquares;
    private int[] chosenSquare = new int[2];
    private ImageButton A1;
    private ImageButton C1;
    private ImageButton E1;
    private ImageButton G1;
    private ImageButton B2;
    private ImageButton D2;
    private ImageButton F2;
    private ImageButton H2;
    private ImageButton A3;
    private ImageButton C3;
    private ImageButton E3;
    private ImageButton G3;
    private ImageButton B4;
    private ImageButton D4;
    private ImageButton F4;
    private ImageButton H4;
    private ImageButton A5;
    private ImageButton C5;
    private ImageButton E5;
    private ImageButton G5;
    private ImageButton B6;
    private ImageButton D6;
    private ImageButton F6;
    private ImageButton H6;
    private ImageButton A7;
    private ImageButton C7;
    private ImageButton E7;
    private ImageButton G7;
    private ImageButton B8;
    private ImageButton D8;
    private ImageButton F8;
    private ImageButton H8;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers);

        setUpBoard();
    }

    private void setUpBoard()
    {
        Player player1 = new Player("whitePlayer");
        Player player2 = new Player("blackPlayer");
        game = new Game(player1, player2, "Checkers");
        board = new CheckersBoard();

        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                buttons[i][j]=null;
            }
        }
        redSquares = new ArrayList<>();
        ArrayList<Integer> coordinates = new ArrayList<>();

        A1 = findViewById(R.id.CheckersA1);
        C1 = findViewById(R.id.CheckersC1);
        E1 = findViewById(R.id.CheckersE1);
        G1 = findViewById(R.id.CheckersG1);
        B2 = findViewById(R.id.CheckersB2);
        D2 = findViewById(R.id.CheckersD2);
        F2 = findViewById(R.id.CheckersF2);
        H2 = findViewById(R.id.CheckersH2);
        A3 = findViewById(R.id.CheckersA3);
        C3 = findViewById(R.id.CheckersC3);
        E3 = findViewById(R.id.CheckersE3);
        G3 = findViewById(R.id.CheckersG3);
        B4 = findViewById(R.id.CheckersB4);
        D4 = findViewById(R.id.CheckersD4);
        F4 = findViewById(R.id.CheckersF4);
        H4 = findViewById(R.id.CheckersH4);
        A5 = findViewById(R.id.CheckersA5);
        C5 = findViewById(R.id.CheckersC5);
        E5 = findViewById(R.id.CheckersE5);
        G5 = findViewById(R.id.CheckersG5);
        B6 = findViewById(R.id.CheckersB6);
        D6 = findViewById(R.id.CheckersD6);
        F6 = findViewById(R.id.CheckersF6);
        H6 = findViewById(R.id.CheckersH6);
        A7 = findViewById(R.id.CheckersA7);
        C7 = findViewById(R.id.CheckersC7);
        E7 = findViewById(R.id.CheckersE7);
        G7 = findViewById(R.id.CheckersG7);
        B8 = findViewById(R.id.CheckersB8);
        D8 = findViewById(R.id.CheckersD8);
        F8 = findViewById(R.id.CheckersF8);
        H8 = findViewById(R.id.CheckersH8);

        coordinates.add(1); coordinates.add(1); buttons[1][1]=A1;
        coordinates.add(3); coordinates.add(1); buttons[3][1]=C1;
        coordinates.add(5); coordinates.add(1); buttons[5][1]=E1;
        coordinates.add(7); coordinates.add(1); buttons[7][1]=G1;
        coordinates.add(2); coordinates.add(2); buttons[2][2]=B2;
        coordinates.add(4); coordinates.add(2); buttons[4][2]=D2;
        coordinates.add(6); coordinates.add(2); buttons[6][2]=F2;
        coordinates.add(8); coordinates.add(2); buttons[8][2]=H2;
        coordinates.add(1); coordinates.add(3); buttons[1][3]=A3;
        coordinates.add(3); coordinates.add(3); buttons[3][3]=C3;
        coordinates.add(5); coordinates.add(3); buttons[5][3]=E3;
        coordinates.add(7); coordinates.add(3); buttons[7][3]=G3;
        coordinates.add(2); coordinates.add(4); buttons[2][4]=B4;
        coordinates.add(4); coordinates.add(4); buttons[4][4]=D4;
        coordinates.add(6); coordinates.add(4); buttons[6][4]=F4;
        coordinates.add(8); coordinates.add(4); buttons[8][4]=H4;
        coordinates.add(1); coordinates.add(5); buttons[1][5]=A5;
        coordinates.add(3); coordinates.add(5); buttons[3][5]=C5;
        coordinates.add(5); coordinates.add(5); buttons[5][5]=E5;
        coordinates.add(7); coordinates.add(5); buttons[7][5]=G5;
        coordinates.add(2); coordinates.add(6); buttons[2][6]=B6;
        coordinates.add(4); coordinates.add(6); buttons[4][6]=D6;
        coordinates.add(6); coordinates.add(6); buttons[6][6]=F6;
        coordinates.add(8); coordinates.add(6); buttons[8][6]=H6;
        coordinates.add(1); coordinates.add(7); buttons[1][7]=A7;
        coordinates.add(3); coordinates.add(7); buttons[3][7]=C7;
        coordinates.add(5); coordinates.add(7); buttons[5][7]=E7;
        coordinates.add(7); coordinates.add(7); buttons[7][7]=G7;
        coordinates.add(2); coordinates.add(8); buttons[2][8]=B8;
        coordinates.add(4); coordinates.add(8); buttons[4][8]=D8;
        coordinates.add(6); coordinates.add(8); buttons[6][8]=F8;
        coordinates.add(8); coordinates.add(8); buttons[8][8]=H8;




        for (int i = 0; i< coordinates.size(); i+=2)
        {
            int coordinateX = coordinates.get(i);
            int coordinateY = coordinates.get(i+1);
            ImageButton a = buttons[coordinateX][coordinateY];

            String name = a.toString();
            Piece p;
            if (coordinateY<=3)
            {
                p = new Piece_Checkers_White(name, "White", coordinateX, coordinateY);
                game.addWhitePiece(p);
                board.addPiece(p,coordinateX,coordinateY);
                a.setImageResource(R.drawable.checkers_white);
            }
            else if(coordinateY>=6)
            {
                p = new Piece_Checkers_Black(name, "Black", coordinateX, coordinateY);
                game.addBlackPiece(p);
                board.addPiece(p,coordinateX,coordinateY);
                a.setImageResource(R.drawable.checkers_black);
            }
            else
            {
                a.setImageResource(R.drawable.checkers_empty);
            }

        }
    }

    public void onClickBoard(View v)
    {
        switch (v.getId())
        {
            case R.id.CheckersA1:
                clickSquare(1,1);
                break;
            case R.id.CheckersC1:
                clickSquare(3,1);
                break;
            case R.id.CheckersE1:
                clickSquare(5,1);
                break;
            case R.id.CheckersG1:
                clickSquare(7,1);
                break;
            case R.id.CheckersB2:
                clickSquare(2,2);
                break;
            case R.id.CheckersD2:
                clickSquare(4,2);
                break;
            case R.id.CheckersF2:
                clickSquare(6,2);
                break;
            case R.id.CheckersH2:
                clickSquare(8,2);
                break;
            case R.id.CheckersA3:
                clickSquare(1,3);
                break;
            case R.id.CheckersC3:
                clickSquare(3,3);
                break;
            case R.id.CheckersE3:
                clickSquare(5,3);
                break;
            case R.id.CheckersG3:
                clickSquare(7,3);
                break;
            case R.id.CheckersB4:
                clickSquare(2,4);
                break;
            case R.id.CheckersD4:
                clickSquare(4,4);
                break;
            case R.id.CheckersF4:
                clickSquare(6,4);
                break;
            case R.id.CheckersH4:
                clickSquare(8,4);
                break;
            case R.id.CheckersA5:
                clickSquare(1,5);
                break;
            case R.id.CheckersC5:
                clickSquare(3,5);
                break;
            case R.id.CheckersE5:
                clickSquare(5,5);
                break;
            case R.id.CheckersG5:
                clickSquare(7,5);
                break;
            case R.id.CheckersB6:
                clickSquare(2,6);
                break;
            case R.id.CheckersD6:
                clickSquare(4,6);
                break;
            case R.id.CheckersF6:
                clickSquare(6,6);
                break;
            case R.id.CheckersH6:
                clickSquare(8,6);
                break;
            case R.id.CheckersA7:
                clickSquare(1,7);
                break;
            case R.id.CheckersC7:
                clickSquare(3,7);
                break;
            case R.id.CheckersE7:
                clickSquare(5,7);
                break;
            case R.id.CheckersG7:
                clickSquare(7,7);
                break;
            case R.id.CheckersB8:
                clickSquare(2,8);
                break;
            case R.id.CheckersD8:
                clickSquare(4,8);
                break;
            case R.id.CheckersF8:
                clickSquare(6,8);
                break;
            case R.id.CheckersH8:
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
                    Log.i("Checkers","found red square");
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
        if (p instanceof Piece_Checkers_White)
        {
            if (game.currentTurn()=="Black")
            {
                return;
            }
            moves = board.availableMoves(x, y);
        }
        else if (p instanceof Piece_Checkers_Black)
        {
            if (game.currentTurn()=="White")
            {
                return;
            }
            moves = board.availableMoves(x, y);
        }

        for (int i=0; i<moves.size(); i+=2)
        {
            markSquare(moves.get(i),moves.get(i+1));
            Log.i("Checkers",moves.get(i) + "-" + moves.get(i+1));
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
        if (piece instanceof Piece_Checkers_Crowned_White)   //first childs, then fathers
        {
            image.setImageResource(R.drawable.checkers_crowned_white);
        }
        else if(piece instanceof Piece_Checkers_Crowned_Black)
        {
            image.setImageResource(R.drawable.checkers_crowned_black);
        }
        else if(piece instanceof Piece_Checkers_White)
        {
            image.setImageResource(R.drawable.checkers_white);
        }
        else if(piece instanceof Piece_Checkers_Black)
        {
            image.setImageResource(R.drawable.checkers_black);
        }
        else if(piece==null)
        {
            image.setImageResource(R.drawable.checkers_empty);
        }
        else
        {
            Log.i("Checkers", "Piece: " + x + "-" + y + " has wrong type.");
        }
    }

    private void movePiece(int posX, int posY, int finalX, int finalY)
    {
        Piece startPiece = board.returnPiece(posX, posY);
        Piece endPiece = board.returnPiece(finalX, finalY);
        if (startPiece instanceof Piece_Checkers_White || startPiece instanceof Piece_Checkers_Black)
        {
            if ((finalX==posX+2 || finalX==posX-2) && (finalY==posY+2 || finalY==posY-2))    //if it is a jump
            {
                int middleX = (posX+finalX)/2;
                int middleY = (posY+finalY)/2;
                Piece middlePiece = board.returnPiece(middleX, middleY);
                if (startPiece instanceof Piece_Checkers_White)
                {game.removeBlackPiece(middlePiece);}
                if (startPiece instanceof Piece_Checkers_Black)
                {game.removeWhitePiece(middlePiece);}
                board.removePiece(middleX,middleY);
                drawProperPiece(null,middleX,middleY);
            }
            boolean crowned=false;
            crowned = board.movePiece(posX, posY, finalX, finalY);
            drawProperPiece(null,posX,posY);
            if (crowned)
            {
                Piece crownedPiece = board.returnPiece(finalX,finalY);
                if (startPiece instanceof Piece_Checkers_White)
                {
                    game.removeWhitePiece(startPiece);
                    game.addWhitePiece(crownedPiece);
                }
                if (startPiece instanceof Piece_Checkers_Black)
                {
                    game.removeBlackPiece(startPiece);
                    game.addBlackPiece(crownedPiece);
                }
                drawProperPiece(crownedPiece,finalX,finalY);
            }
            else
            {
                drawProperPiece(startPiece,finalX,finalY);
            }

        }
        else
        {
            Log.i("Checkers", "Piece: " + posX + "-" + posY + " has wrong type.");
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

//https://www.pixilart.com/draw/50x50-b16e5b967c8eaa3