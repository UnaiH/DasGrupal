package com.example.trabajogrupal;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class GameActivity extends AppCompatActivity
{
    protected String player1;
    protected String player2;
    protected int idGame;
    protected String currentTurn;
    protected String currentUser;
    protected void insertPiece(int idGame, String type, int posX, int posY)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putString("type", type);
        data.putInt("posX", posX);
        data.putInt("posY", posY);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerInsertPiece.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>()
                {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Log.i("workerPHP", "Piece inserted");
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    protected void deletePiece(int idGame, int posX, int posY)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putInt("posX", posX);
        data.putInt("posY", posY);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerDeletePiece.class)
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
                            Log.i("workerPHP", "Piece deleted");
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    protected void deletePiece_updatePiece(int idGame, int posX, int posY, int finalX, int finalY)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putInt("posX", finalX);
        data.putInt("posY", finalY);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerDeletePiece.class)
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
                            Log.i("workerPHP", "Piece deleted --> updatePiece");
                            updatePiece(idGame, posX, posY, finalX, finalY);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    protected void deletePiece_updatePiece_updatePieceType(int idGame, int posX, int posY, int finalX, int finalY, String type)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putInt("posX", finalX);
        data.putInt("posY", finalY);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerDeletePiece.class)
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
                            Log.i("workerPHP", "Piece deleted --> updatePiece_updatePieceType");
                            updatePiece_updatePieceType(idGame, posX, posY, finalX, finalY, type);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    protected void updatePiece(int idGame, int posXOld, int posYOld, int posXNew, int posYNew)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putInt("posXOld", posXOld);
        data.putInt("posYOld", posYOld);
        data.putInt("posXNew", posXNew);
        data.putInt("posYNew", posYNew);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerUpdatePiece.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>()
                {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Log.i("workerPHP", "Piece updated");
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    protected void updatePiece_updatePieceType(int idGame, int posXOld, int posYOld, int posXNew, int posYNew, String type)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putInt("posXOld", posXOld);
        data.putInt("posYOld", posYOld);
        data.putInt("posXNew", posXNew);
        data.putInt("posYNew", posYNew);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerUpdatePiece.class)
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
                            Log.i("workerPHP", "Piece updated --> updatePieceType");
                            updatePieceType(idGame,posXNew, posYNew , type);
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    protected void updatePieceType(int idGame, int posX, int posY, String type)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putInt("posX", posX);
        data.putInt("posY", posY);
        data.putString("type", type);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerUpdatePieceType.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>()
                {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Log.i("workerPHP", "Piece type updated");
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }



    protected void getPieces(int idGame)
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
                            for (int i=0; i<listPieces.length; i+=3)
                            {
                                String type = listPieces[i];
                                int posX = Integer.parseInt(listPieces[i + 1]);
                                int posY = Integer.parseInt(listPieces[i + 2]);
                                Log.i("workerPHP", "Pieces recovered: " + type + "  " + posX + "-" + posY);
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    protected void updateTurn(int idGame, String currentTurn)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putString("currentTurn", currentTurn);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerUpdateTurn.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>()
                {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Log.i("workerPHP", "Turn changed");
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    protected void getNameInfo(int idGame)
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
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }
}
