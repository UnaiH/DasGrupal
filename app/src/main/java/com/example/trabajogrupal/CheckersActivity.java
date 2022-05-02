package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class CheckersActivity extends AppCompatActivity {

    private Game game;
    private Piece[][] board = new Piece[9][9];
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

        game.addWhitePiece(p1); board[1][1]=p1;
        game.addWhitePiece(p2); board[3][1]=p2;
        game.addWhitePiece(p3); board[5][1]=p3;
        game.addWhitePiece(p4); board[7][1]=p4;
        game.addWhitePiece(p5); board[2][2]=p5;
        game.addWhitePiece(p6); board[4][2]=p6;
        game.addWhitePiece(p7); board[6][2]=p7;
        game.addWhitePiece(p8); board[8][2]=p8;
        game.addWhitePiece(p9); board[1][3]=p9;
        game.addWhitePiece(p10); board[3][3]=p10;
        game.addWhitePiece(p11); board[5][3]=p11;
        game.addWhitePiece(p12); board[7][3]=p12;

        game.addBlackPiece(p13); board[2][6]=p13;
        game.addBlackPiece(p14); board[4][6]=p14;
        game.addBlackPiece(p15); board[6][6]=p15;
        game.addBlackPiece(p16); board[8][6]=p16;
        game.addBlackPiece(p17); board[1][7]=p17;
        game.addBlackPiece(p18); board[3][7]=p18;
        game.addBlackPiece(p19); board[5][7]=p19;
        game.addBlackPiece(p20); board[7][7]=p20;
        game.addBlackPiece(p21); board[2][8]=p21;
        game.addBlackPiece(p22); board[4][8]=p22;
        game.addBlackPiece(p23); board[6][8]=p23;
        game.addBlackPiece(p24); board[8][8]=p24;

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

        arrowUL = findViewById(R.id.CheckersArrowUL); arrowUL.setImageResource(R.drawable.checkers_up_left);
        arrowUR = findViewById(R.id.CheckersArrowUR); arrowUR.setImageResource(R.drawable.checkers_up_right);
        arrowDL = findViewById(R.id.CheckersArrowDL); arrowDL.setImageResource(R.drawable.checkers_down_left);
        arrowDR = findViewById(R.id.CheckersArrowDR); arrowDR.setImageResource(R.drawable.checkers_down_right);

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
    }

    public void onClickBoard(View v)
    {
        switch (v.getId())
        {
            case R.id.CheckersA1:
                comprobarCasilla(1,1);
                break;
            case R.id.CheckersC1:
                comprobarCasilla(3,1);
                break;
            case R.id.CheckersE1:
                comprobarCasilla(5,1);
                break;
            case R.id.CheckersG1:
                comprobarCasilla(7,1);
                break;
            case R.id.CheckersB2:
                comprobarCasilla(2,2);
                break;
            case R.id.CheckersD2:
                comprobarCasilla(4,2);
                break;
            case R.id.CheckersF2:
                comprobarCasilla(6,2);
                break;
            case R.id.CheckersH2:
                comprobarCasilla(8,2);
                break;
            case R.id.CheckersA3:
                comprobarCasilla(1,3);
                break;
            case R.id.CheckersC3:
                comprobarCasilla(3,3);
                break;
            case R.id.CheckersE3:
                comprobarCasilla(5,3);
                break;
            case R.id.CheckersG3:
                comprobarCasilla(7,3);
                break;
            case R.id.CheckersB4:
                comprobarCasilla(2,4);
                break;
            case R.id.CheckersD4:
                comprobarCasilla(4,4);
                break;
            case R.id.CheckersF4:
                comprobarCasilla(6,4);
                break;
            case R.id.CheckersH4:
                comprobarCasilla(8,4);
                break;
            case R.id.CheckersA5:
                comprobarCasilla(1,5);
                break;
            case R.id.CheckersC5:
                comprobarCasilla(3,5);
                break;
            case R.id.CheckersE5:
                comprobarCasilla(5,5);
                break;
            case R.id.CheckersG5:
                comprobarCasilla(7,5);
                break;
            case R.id.CheckersB6:
                comprobarCasilla(2,6);
                break;
            case R.id.CheckersD6:
                comprobarCasilla(4,6);
                break;
            case R.id.CheckersF6:
                comprobarCasilla(6,6);
                break;
            case R.id.CheckersH6:
                comprobarCasilla(8,6);
                break;
            case R.id.CheckersA7:
                comprobarCasilla(1,7);
                break;
            case R.id.CheckersC7:
                comprobarCasilla(3,7);
                break;
            case R.id.CheckersE7:
                comprobarCasilla(5,7);
                break;
            case R.id.CheckersG7:
                comprobarCasilla(7,7);
                break;
            case R.id.CheckersB8:
                comprobarCasilla(2,8);
                break;
            case R.id.CheckersD8:
                comprobarCasilla(4,8);
                break;
            case R.id.CheckersF8:
                comprobarCasilla(6,8);
                break;
            case R.id.CheckersH8:
                comprobarCasilla(8,8);
                break;
        }
    }

    private void comprobarCasilla(int x, int y)
    {
        Piece p = board[x][y];
        if (p==null)
        {
            return;
        }
        if (p instanceof Piece_Checkers_White)
        {
            if (game.currentTurn()=="Black")
            {
                return;
            }
        }
        else if (p instanceof Piece_Checkers_Black)
        {
            if (game.currentTurn()=="White")
            {
                return;
            }
        }
    }
}


//https://www.pixilart.com/draw/50x50-b16e5b967c8eaa3