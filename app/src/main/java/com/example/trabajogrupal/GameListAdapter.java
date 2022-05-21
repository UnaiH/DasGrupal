package com.example.trabajogrupal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameListAdapter extends BaseAdapter {
    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList games;

    public GameListAdapter(Context pcontext, ArrayList pgames)
    {
        contexto = pcontext;
        games = pgames;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int i) {
        return games.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.fila_game_list,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView usernameView = (TextView) view.findViewById(R.id.username);
        TextView countryView = (TextView) view.findViewById(R.id.country);
        TextView eloView = (TextView) view.findViewById(R.id.elo);
        TextView gameIDView = (TextView) view.findViewById(R.id.gameID);
        TextView gameTypeView = (TextView) view.findViewById(R.id.gameType);
        TextView turnOrWinnerView = (TextView) view.findViewById(R.id.turnOrWinner);

        Game game = (Game) games.get(i);
        Player rival = game.getRival();

        imageView.setImageBitmap(rival.getImage());
        usernameView.setText(rival.getName());
        countryView.setText(rival.getPais());
        int elo = 0;
        String gameType = null;
        if (game.getType().equals("Checkers")) {
            elo = rival.getEloCheckers();
            gameType = contexto.getString(R.string.checkers);
        }
        else if (game.getType().equals("Chess")) {
            elo = rival.getEloChess();
            gameType = contexto.getString(R.string.chess);
        }
        eloView.setText(elo);
        gameIDView.setText(game.getId());
        gameTypeView.setText(game.getType());
        String turnOrWinner = null;
        if (game instanceof GameInCourse) {
            if (((GameInCourse) game).isMyTurn()) {
                turnOrWinner = contexto.getString(R.string.my_turn);
            } else {
                turnOrWinner = contexto.getString(R.string.rivals_turn);
            }
        }
        else if (game instanceof GameFinished) {
            if (((GameFinished) game).isWon()) {
                turnOrWinner = contexto.getString(R.string.victory);
            } else {
                turnOrWinner = contexto.getString(R.string.loss);
            }
        }
        turnOrWinnerView.setText(turnOrWinner);

        return view;
    }
}
