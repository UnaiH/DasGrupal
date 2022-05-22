package com.example.trabajogrupal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CheckersActivity extends GameActivity {

    private CheckersBoard board;
    private ImageButton[][] buttons = new ImageButton[10][10];
    private ArrayList<Integer> redSquares;
    private int[] chosenSquare = new int[2];
    private int whiteCounts;
    private int blackCounts;
    ArrayList<Integer> coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem=new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_checkers);

        initialSteps();
        type="Checkers";

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            idGame= extras.getInt("idGame");
            currentUser = PlayerCatalogue.getMyPlayerCatalogue().getCurrentUser();
            getNameInfo(idGame);
        }
    }

    private void initialSteps()
    {
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

        ImageButton A1 = findViewById(R.id.CheckersA1);
        ImageButton C1 = findViewById(R.id.CheckersC1);
        ImageButton E1 = findViewById(R.id.CheckersE1);
        ImageButton G1 = findViewById(R.id.CheckersG1);
        ImageButton B2 = findViewById(R.id.CheckersB2);
        ImageButton D2 = findViewById(R.id.CheckersD2);
        ImageButton F2 = findViewById(R.id.CheckersF2);
        ImageButton H2 = findViewById(R.id.CheckersH2);
        ImageButton A3 = findViewById(R.id.CheckersA3);
        ImageButton C3 = findViewById(R.id.CheckersC3);
        ImageButton E3 = findViewById(R.id.CheckersE3);
        ImageButton G3 = findViewById(R.id.CheckersG3);
        ImageButton B4 = findViewById(R.id.CheckersB4);
        ImageButton D4 = findViewById(R.id.CheckersD4);
        ImageButton F4 = findViewById(R.id.CheckersF4);
        ImageButton H4 = findViewById(R.id.CheckersH4);
        ImageButton A5 = findViewById(R.id.CheckersA5);
        ImageButton C5 = findViewById(R.id.CheckersC5);
        ImageButton E5 = findViewById(R.id.CheckersE5);
        ImageButton G5 = findViewById(R.id.CheckersG5);
        ImageButton B6 = findViewById(R.id.CheckersB6);
        ImageButton D6 = findViewById(R.id.CheckersD6);
        ImageButton F6 = findViewById(R.id.CheckersF6);
        ImageButton H6 = findViewById(R.id.CheckersH6);
        ImageButton A7 = findViewById(R.id.CheckersA7);
        ImageButton C7 = findViewById(R.id.CheckersC7);
        ImageButton E7 = findViewById(R.id.CheckersE7);
        ImageButton G7 = findViewById(R.id.CheckersG7);
        ImageButton B8 = findViewById(R.id.CheckersB8);
        ImageButton D8 = findViewById(R.id.CheckersD8);
        ImageButton F8 = findViewById(R.id.CheckersF8);
        ImageButton H8 = findViewById(R.id.CheckersH8);

        for (int j=1; j<9; j++)
        {
            for (int i=1; i<9; i++)
            {
                if ((i+j)%2==0)
                {
                    coordinates.add(i);
                    coordinates.add(j);
                }
            }
        }

        buttons[1][1]=A1; buttons[3][1]=C1; buttons[5][1]=E1;
        buttons[7][1]=G1; buttons[2][2]=B2; buttons[4][2]=D2;
        buttons[6][2]=F2; buttons[8][2]=H2; buttons[1][3]=A3;
        buttons[3][3]=C3; buttons[5][3]=E3; buttons[7][3]=G3;
        buttons[2][4]=B4; buttons[4][4]=D4; buttons[6][4]=F4;
        buttons[8][4]=H4; buttons[1][5]=A5; buttons[3][5]=C5;
        buttons[5][5]=E5; buttons[7][5]=G5; buttons[2][6]=B6;
        buttons[4][6]=D6; buttons[6][6]=F6; buttons[8][6]=H6;
        buttons[1][7]=A7; buttons[3][7]=C7; buttons[5][7]=E7;
        buttons[7][7]=G7; buttons[2][8]=B8; buttons[4][8]=D8;
        buttons[6][8]=F8; buttons[8][8]=H8;
    }

    private void setUpBoard()
    {
        whiteCounts=12;
        blackCounts=12;

        for (int i = 0; i< coordinates.size(); i+=2)
        {
            int coordinateX = coordinates.get(i);
            int coordinateY = coordinates.get(i+1);
            ImageButton a = buttons[coordinateX][coordinateY];

            String name = a.toString();
            Piece p;
            if (coordinateX<1 || coordinateX >8 || coordinateY<1 || coordinateY>8)
            {
                Log.i("Checkers","illegal coordinate:" + coordinateX + "-" + coordinateY);
                return;
            }
            if (coordinateY>=4 && coordinateY<=5)
            {
                a.setImageResource(R.drawable.checkers_empty);
            }
            else
            {
                if (coordinateY<=3)
                {
                    p = new Piece_Checkers_White(name, "White", coordinateX, coordinateY);
                    a.setImageResource(R.drawable.checkers_white);
                }
                else
                {
                    p = new Piece_Checkers_Black(name, "Black", coordinateX, coordinateY);
                    a.setImageResource(R.drawable.checkers_black);
                }
                insertPiece(idGame,p.getClass().getName(),coordinateX,coordinateY);
                board.addPiece(p,coordinateX,coordinateY);
            }
        }
    }

    private void loadPieces()
    {
        whiteCounts=0;
        blackCounts=0;

        for (int i=0; i<coordinates.size(); i+=2)
        {
            int coordinateX = coordinates.get(i);
            int coordinateY = coordinates.get(i+1);
            ImageButton button = buttons[coordinateX][coordinateY];
            button.setImageResource(R.drawable.checkers_empty);
        }

        for (int j=0; j<loadedPieces.size(); j++)
        {
            Piece p = loadedPieces.get(j);
            drawProperPiece(p,p.posX,p.posY);
            board.addPiece(p,p.posX,p.posY);
            if(p.color.equals("White"))
            {
                whiteCounts++;
            }
            else if(p.color.equals("Black"))
            {
                whiteCounts++;
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
                    if (currentTurn.equals("White"))
                    {
                        currentTurn="Black";
                        updateTurn(idGame,"Black");
                    }
                    else if (currentTurn.equals("Black"))
                    {
                        currentTurn="White";
                        updateTurn(idGame,"White");
                    }
                    else
                    {
                        Log.i("Checkers","currentTurn error: " + currentTurn);
                    }
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
            if (currentTurn.equals("Black") || currentUser.equals(player2))
            {
                return;
            }
            moves = board.availableMoves(x, y);
        }
        else if (p instanceof Piece_Checkers_Black)
        {
            if (currentTurn.equals("White")  || currentUser.equals(player1))
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

    private void movePiece(int posX, int posY, int finalX, int finalY)
    {
        Piece startPiece = board.returnPiece(posX, posY);
        if (startPiece instanceof Piece_Checkers_White || startPiece instanceof Piece_Checkers_Black)
        {
            if ((finalX==posX+2 || finalX==posX-2) && (finalY==posY+2 || finalY==posY-2))    //if it is a jump
            {
                int middleX = (posX+finalX)/2;
                int middleY = (posY+finalY)/2;
                board.removePiece(middleX,middleY);

                if (currentTurn.equals("White"))
                {
                    blackCounts--;
                }
                else if (currentTurn.equals("Black"))
                {
                    whiteCounts--;
                }
                else
                {
                    Log.i("Chess", "currentTurn error: " + currentTurn);
                    return;
                }
                drawProperPiece(null,middleX,middleY);
                if(whiteCounts==0 || blackCounts==0)
                {
                    finish();
                    Intent i = new Intent(this, GameOverActivity.class);
                    if (currentTurn.equals("White"))
                    {
                        i.putExtra("winner", player1);
                        i.putExtra("loser", player2);
                    }
                    if (currentTurn.equals("Black"))
                    {
                        i.putExtra("winner", player2);
                        i.putExtra("loser", player1);
                    }
                    i.putExtra("gameType", "Checkers");
                    i.putExtra("idGame",idGame);
                    startActivity(i);
                    return;
                }
                deletePiece(idGame,middleX,middleY);
            }
            boolean crowned;
            crowned = board.movePiece(posX, posY, finalX, finalY);
            drawProperPiece(null,posX,posY);
            if (crowned)
            {
                Piece crownedPiece = board.returnPiece(finalX,finalY);
                updatePiece_updatePieceType(idGame, posX,posY,finalX,finalY, crownedPiece.getClass().getName());
                drawProperPiece(crownedPiece,finalX,finalY);
            }
            else
            {
                updatePiece(idGame,posX,posY,finalX,finalY);
                drawProperPiece(startPiece,finalX,finalY);
            }

        }
        else
        {
            Log.i("Checkers", "Piece: " + posX + "-" + posY + " has wrong type.");
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

    private void getNameInfo(int idGame)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerSelectGame.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>()
                {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        if (workInfo != null && workInfo.getState().isFinished())
                        {
                            String[] listPieces = workInfo.getOutputData().getStringArray("lista");
                            for (int i=0; i<listPieces.length; i+=3)
                            {
                                player1 = listPieces[i];
                                player2 = listPieces[i + 1];
                                currentTurn = listPieces[i + 2];
                                Log.i("workerPHP", "Game info: " + player1 + "-" + player2 + "-" + currentTurn);
                            }
                            getPieces(idGame);
                        }

                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    private void getPieces(int idGame)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerSelectPieces.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>()
                {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        if (workInfo != null && workInfo.getState().isFinished())
                        {
                            String[] listPieces = workInfo.getOutputData().getStringArray("lista");
                            loadedPieces = new ArrayList<>();
                            for (int i=0; i<listPieces.length; i+=3)
                            {
                                String type = listPieces[i];
                                int posX = Integer.parseInt(listPieces[i + 1]);
                                int posY = Integer.parseInt(listPieces[i + 2]);
                                Log.i("workerPHP", "Pieces recovered: " + type + "  " + posX + "-" + posY);

                                String color="";
                                if (type.contains("White"))
                                {
                                    color = "White";
                                }
                                else if (type.contains("Black"))
                                {
                                    color = "Black";
                                }

                                Piece p=null;
                                if (type.contains("Crowned_White"))
                                {
                                    p = new Piece_Checkers_Crowned_White("name",color,posX,posY);
                                }
                                else if (type.contains("Crowned_Black"))
                                {
                                    p = new Piece_Checkers_Crowned_Black("name",color,posX,posY);
                                }
                                else if (type.contains("Checkers_White"))
                                {
                                    p = new Piece_Checkers_White("name",color,posX,posY);
                                }
                                else if (type.contains("Checkers_Black"))
                                {
                                    p = new Piece_Checkers_Black("name",color,posX,posY);
                                }
                                else
                                {
                                    Log.i("workerPHP","ayuda");
                                    break;
                                }
                                Log.i("workerPHP",p.getClass().getName());
                                loadedPieces.add(p);
                            }
                            if (loadedPieces!=null && loadedPieces.size()!=0)
                            {
                                loadPieces();
                                Log.i("workerPHP","load");
                            }
                            else
                            {
                                setUpBoard();
                                Log.i("workerPHP","setUp");
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    /*Para que no se salga de la App
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(this, SelectMenuActivity.class);
        //i.putExtra("user", user);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }*/
}

//https://www.pixilart.com/draw/50x50-b16e5b967c8eaa3