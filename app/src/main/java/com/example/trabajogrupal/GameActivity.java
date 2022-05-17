package com.example.trabajogrupal;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity
{
    protected void loadPieces(Piece[] pieces, int id)
    {
        Piece p;
        String type;
        int posX;
        int posY;
        for (int i=0; i<pieces.length; i++)
        {
            p = pieces[i];
            type = p.getClass().getName();
            posX=p.posX;
            posY=p.posY;
            insertPiece(id, type, posX, posY);
        }
    }
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
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            Log.i("workerPHP", "Piece deleted");
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
}
