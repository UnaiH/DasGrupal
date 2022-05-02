package com.example.trabajogrupal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GlobalRankingAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private String[] userNames;
    private int[] ELO;
    public GlobalRankingAdapter(Activity context, String[] pUserNames, int[] pELO){
        userNames=pUserNames;
        ELO=pELO;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return userNames.length;
    }

    @Override
    public Object getItem(int i) {
        return userNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view=inflater.inflate(R.layout.globalrankingelement,null);
        TextView textViewUser = (TextView) view.findViewById(R.id.rankUser);
        TextView textViewELO = (TextView) view.findViewById(R.id.rankELO);
        textViewUser.setText(userNames[i]);
        textViewELO.setText(ELO[i]);
        return view;
    }
}
