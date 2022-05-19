package com.example.trabajogrupal;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;


//clase que guarda todos los anuncios
public class PlayerCatalogue
{
    private static PlayerCatalogue myPlayerCatalogue =null;
    private HashMap<String,Player> mapPlayers;
    private String currentUser;

    private PlayerCatalogue ()
    {
        this.mapPlayers =new HashMap<String,Player>();
    }
    public static synchronized PlayerCatalogue getMyPlayerCatalogue()
    {
        if (myPlayerCatalogue ==null)
        {
            myPlayerCatalogue =new PlayerCatalogue();
        }
        return myPlayerCatalogue;
    }

    public void addPlayer(String mail, int eloCheckers, int eloChess)
    {
        if (this.returnPlayer(mail)==null)
        {
            Player player = new Player(mail, eloCheckers, eloChess);
            mapPlayers.put(mail, player);
        }
    }

    public Player returnPlayer(String mail)
    {
        Player p=null;
        if (mapPlayers.containsKey(mail))
        {
            p= mapPlayers.get(mail);
        }
        return p;
    }

    public ArrayList<String> returnMails()
    {
        ArrayList<String> listPlayers = new ArrayList<>();
        for (String key : mapPlayers.keySet() )
        {
            listPlayers.add(key);
        }
        return listPlayers;
    }

    public void setCurrentUser(String pUser)
    {
        currentUser=pUser;
    }
}