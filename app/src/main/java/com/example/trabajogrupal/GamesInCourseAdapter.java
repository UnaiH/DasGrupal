package com.example.trabajogrupal;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GamesInCourseAdapter extends BaseAdapter {
    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList<GameInCourse> gamesInCourse;

    public GamesInCourseAdapter(Context pcontext, ArrayList<GameInCourse> pgamesInCourse)
    {
        contexto = pcontext;
        gamesInCourse = pgamesInCourse;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gamesInCourse.size();
    }

    @Override
    public Object getItem(int i) {
        return gamesInCourse.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.fila_games_in_course,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView usernameView = (TextView) view.findViewById(R.id.username);
        TextView countryView = (TextView) view.findViewById(R.id.country);
        TextView gameIDView = (TextView) view.findViewById(R.id.gameID);
        TextView gameTypeView = (TextView) view.findViewById(R.id.gameType);
        TextView eloView = (TextView) view.findViewById(R.id.elo);
        TextView nexTurnView = (TextView) view.findViewById(R.id.nextTurn);

        GameInCourse gameInCourse = gamesInCourse.get(i);
        Player rival = gameInCourse.getRival();

        imageView.setImageBitmap(rival.getImage());
        usernameView.setText(rival.getName());
        countryView.setText(rival.getPais());
        gameIDView.setText(gameInCourse.getId());
        gameTypeView.setText(gameInCourse.getType());
        int elo = 0;
        if (gameInCourse.getType().equals("Checkers")) {
            elo = rival.getElo(0);
        }
        else if (gameInCourse.getType().equals("Chess")) {
            elo = rival.getElo(1);
        }
        eloView.setText(elo);
        String turn;
        if (gameInCourse.isMyTurn()) {
            turn = "My turn";
        }
        else {
            turn = "Rival turn";
        }
        nexTurnView.setText(turn);

        return view;
    }
}
