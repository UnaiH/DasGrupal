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
    private ArrayList<Integer> coordinates;
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
    private ImageButton arrowUL;
    private ImageButton arrowUR;
    private ImageButton arrowDL;
    private ImageButton arrowDR;

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
        game = new Game(player1, player2);
        board = new CheckersBoard();

        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                buttons[i][j]=null;
            }
        }
        redSquares = new ArrayList<>();
        coordinates = new ArrayList<>();

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




        for (int i=0; i<coordinates.size(); i+=2)
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
        /**

        Piece p1 = new Piece_Checkers_White("A1","White",1,1);
        Piece p2 = new Piece_Checkers_White("C1","White",3,1);
        Piece p3 = new Piece_Checkers_White("E1","White",5,1);
        Piece p4 = new Piece_Checkers_White("G1","White",7,1);
        Piece p5 = new Piece_Checkers_White("B2","White",2,2);
        Piece p6 = new Piece_Checkers_White("D2","White",4,2);
        Piece p7 = new Piece_Checkers_White("F2","White",6,2);
        Piece p8 = new Piece_Checkers_White("H2","White",8,2);
        Piece p9 = new Piece_Checkers_White("A3","White",1,3);
        Piece p10 = new Piece_Checkers_White("C3","White",3,3);
        Piece p11 = new Piece_Checkers_White("E3","White",5,3);
        Piece p12 = new Piece_Checkers_White("G3","White",7,3);

        Piece p13 = new Piece_Checkers_Black("B6","Black",2,6);
        Piece p14 = new Piece_Checkers_Black("D6","Black",4,6);
        Piece p15 = new Piece_Checkers_Black("F6","Black",6,6);
        Piece p16 = new Piece_Checkers_Black("H6","Black",8,6);
        Piece p17 = new Piece_Checkers_Black("A7","Black",1,7);
        Piece p18 = new Piece_Checkers_Black("C7","Black",3,7);
        Piece p19 = new Piece_Checkers_Black("E7","Black",5,7);
        Piece p20 = new Piece_Checkers_Black("G7","Black",7,7);
        Piece p21 = new Piece_Checkers_Black("B8","Black",2,8);
        Piece p22 = new Piece_Checkers_Black("D8","Black",4,8);
        Piece p23 = new Piece_Checkers_Black("F8","Black",6,8);
        Piece p24 = new Piece_Checkers_Black("H8","Black",8,8);

        game.addWhitePiece(p1); board.addPiece(p1,1,1);
        game.addWhitePiece(p2); board.addPiece(p2,3,1);
        game.addWhitePiece(p3); board.addPiece(p3,5,1);
        game.addWhitePiece(p4); board.addPiece(p4,7,1);
        game.addWhitePiece(p5); board.addPiece(p5,2,2);
        game.addWhitePiece(p6); board.addPiece(p6,4,2);
        game.addWhitePiece(p7); board.addPiece(p7,6,2);
        game.addWhitePiece(p8); board.addPiece(p8,8,2);
        game.addWhitePiece(p9); board.addPiece(p9,1,3);
        game.addWhitePiece(p10); board.addPiece(p10,3,3);
        game.addWhitePiece(p11); board.addPiece(p11,5,3);
        game.addWhitePiece(p12); board.addPiece(p12,7,3);

        game.addBlackPiece(p13); board.addPiece(p1,2,6);
        game.addBlackPiece(p14); board.addPiece(p1,4,6);
        game.addBlackPiece(p15); board.addPiece(p1,6,6);
        game.addBlackPiece(p16); board.addPiece(p1,8,6);
        game.addBlackPiece(p17); board.addPiece(p1,1,7);
        game.addBlackPiece(p18); board.addPiece(p1,3,7);
        game.addBlackPiece(p19); board.addPiece(p1,5,7);
        game.addBlackPiece(p20); board.addPiece(p1,7,7);
        game.addBlackPiece(p21); board.addPiece(p1,2,8);
        game.addBlackPiece(p22); board.addPiece(p1,4,8);
        game.addBlackPiece(p23); board.addPiece(p1,6,8);
        game.addBlackPiece(p24); board.addPiece(p1,8,8);



        A1.setImageResource(R.drawable.checkers_white);
        C1.setImageResource(R.drawable.checkers_white);
        E1.setImageResource(R.drawable.checkers_white);
        G1.setImageResource(R.drawable.checkers_white);
        B2.setImageResource(R.drawable.checkers_white);
        D2.setImageResource(R.drawable.checkers_white);
        F2.setImageResource(R.drawable.checkers_white);
        H2.setImageResource(R.drawable.checkers_white);
        A3.setImageResource(R.drawable.checkers_white);
        C3.setImageResource(R.drawable.checkers_white);
        E3.setImageResource(R.drawable.checkers_white);
        G3.setImageResource(R.drawable.checkers_white);

        B4.setImageResource(R.drawable.checkers_empty);
        D4.setImageResource(R.drawable.checkers_empty);
        F4.setImageResource(R.drawable.checkers_empty);
        H4.setImageResource(R.drawable.checkers_empty);
        A5.setImageResource(R.drawable.checkers_empty);
        C5.setImageResource(R.drawable.checkers_empty);
        E5.setImageResource(R.drawable.checkers_empty);
        G5.setImageResource(R.drawable.checkers_empty);

        B6.setImageResource(R.drawable.checkers_black);
        D6.setImageResource(R.drawable.checkers_black);
        F6.setImageResource(R.drawable.checkers_black);
        H6.setImageResource(R.drawable.checkers_black);
        A7.setImageResource(R.drawable.checkers_black);
        C7.setImageResource(R.drawable.checkers_black);
        E7.setImageResource(R.drawable.checkers_black);
        G7.setImageResource(R.drawable.checkers_black);
        B8.setImageResource(R.drawable.checkers_black);
        D8.setImageResource(R.drawable.checkers_black);
        F8.setImageResource(R.drawable.checkers_black);
        H8.setImageResource(R.drawable.checkers_black);

         **/
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
            /**if (game.currentTurn()=="White")
            {
                return;
            }**/
            moves = board.availableMoves(x, y);
        }

        for (int i=0; i<moves.size(); i+=2)
        {
            markSquare(moves.get(i),moves.get(i+1));
            Log.i("Checkers",moves.get(i) + "-" + moves.get(i+1));
        }

    }



    private void markSquare(int x, int y)
    {
        ImageButton a = buttons[x][y];
        a.setImageResource(R.drawable.checkers_red_square);
        redSquares.add(x);
        redSquares.add(y);
    }

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
                    game.addBlackPiece(crownedPiece);
                }
                if (startPiece instanceof Piece_Checkers_Black)
                {
                    game.removeBlackPiece(startPiece);
                    game.addBlackPiece(crownedPiece);
                }
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(this, selectMenu.class);
        startActivity(i);
    }
}

//https://www.pixilart.com/draw/50x50-b16e5b967c8eaa3