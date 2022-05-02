package com.example.trabajogrupal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class globalRankingAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private String[] userNames;
    private int[] ELO;
    public globalRankingAdapter(Activity context, String[] pUserNames,int[] pELO){
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
        return null;
    }
}
