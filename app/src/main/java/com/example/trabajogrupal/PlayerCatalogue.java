package com.example.trabajogrupal;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//clase que guarda todos los anuncios
public class PlayerCatalogue {
    private static PlayerCatalogue myPlayerCatalogue = null;
    private HashMap<String, Player> mapPlayers;
    private String currentUser;
    private Map<String, List<Player>> mapPlayersByCountry;
    private List<Player> usersByCountryCheckers, usersByCountryChess, usuarios;
    private ArrayList<String> listaPaises;
    private Player currentPlayer;

    private PlayerCatalogue() {
        this.mapPlayers = new HashMap<String, Player>();
        this.usersByCountryCheckers = new ArrayList<>();
        this.usersByCountryChess = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.listaPaises = new ArrayList<>();
        this.mapPlayersByCountry = new HashMap<>();
    }

    public static synchronized PlayerCatalogue getMyPlayerCatalogue() {
        if (myPlayerCatalogue == null) {
            myPlayerCatalogue = new PlayerCatalogue();
        }
        return myPlayerCatalogue;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void addPlayer(String mail, int eloCheckers, int eloChess, String username, String pais) {
        if (this.returnPlayer(mail) == null) {
            Player player = new Player(username, pais, mail, eloCheckers, eloChess);
            usuarios.add(player);
            mapPlayers.put(mail, player);
        }
    }


    public Player returnPlayer(String mail) {
        Player p = null;
        if (mapPlayers.containsKey(mail)) {
            p = mapPlayers.get(mail);
        }
        return p;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getUsersByCountry() {
        this.mapPlayersByCountry = this.usuarios.stream().collect(Collectors.groupingBy(Player::getPais));


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getPaises() {
        this.getUsersByCountry();
        for (String key : mapPlayersByCountry.keySet()) {
            if (!listaPaises.contains(key) && !key.equals("Global")) {
                this.listaPaises.add(key);
            }
        }
        return this.listaPaises;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Player> getUsersDescOrder(String game) {
        //TODO hacer que devuelva una lista<Player> ordenada DESC en función del juego que se le pase por parámetro.
        List<Integer> listaPuntuaciones = new ArrayList<>();
        List<Player> lista = new ArrayList<>();
        Map<Integer, List<Player>> a;
        switch (game) {
            case "Checkers":
                listaPuntuaciones = new ArrayList<>();
                lista = new ArrayList<>();
                a = new HashMap<>();
                a = usuarios.stream().collect(Collectors.groupingBy(Player::getEloCheckers));
                for (Integer i : a.keySet()) {
                    listaPuntuaciones.add(i);
                }
                listaPuntuaciones.sort(Collections.reverseOrder());
                for (Integer i : listaPuntuaciones) {
                    List<Player> unaLista = a.get(i);
                    for (Player j : unaLista) {
                        if(!j.getPais().equals("Global")) {
                            lista.add(j);
                        }
                    }
                }
                break;
            case "Chess":
                listaPuntuaciones = new ArrayList<>();
                lista = new ArrayList<>();
                a = new HashMap<>();
                a = usuarios.stream().collect(Collectors.groupingBy(Player::getEloChess));
                for (Integer i : a.keySet()) {
                    listaPuntuaciones.add(i);
                }
                listaPuntuaciones.sort(Collections.reverseOrder());
                for (Integer i : listaPuntuaciones) {
                    List<Player> unaLista = a.get(i);
                    for (Player j : unaLista) {
                        if(!j.getPais().equals("Global")) {
                            lista.add(j);
                        }
                    }
                }
                break;
        }
        return lista;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Player> getUsersByCountryCheckers(String pais) {
        if (this.mapPlayersByCountry.containsKey(pais)) {
            this.usersByCountryCheckers = mapPlayersByCountry.get(pais).stream().sorted(Comparator.comparing(Player::getEloCheckers).reversed()).collect(Collectors.toList());
        }
        return this.usersByCountryCheckers;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Player> getUsersByCountryChess(String pais) {
        if (this.mapPlayersByCountry.containsKey(pais)) {
            this.usersByCountryChess = mapPlayersByCountry.get(pais).stream().sorted(Comparator.comparing(Player::getEloChess).reversed()).collect(Collectors.toList());
        }
        return this.usersByCountryChess;
    }

    public ArrayList<String> returnMails() {
        ArrayList<String> listPlayers = new ArrayList<>();
        for (String key : mapPlayers.keySet()) {
            listPlayers.add(key);
        }
        return listPlayers;
    }

    public HashMap<String, Player> getMapPlayers() {
        return mapPlayers;
    }

    public void setCurrentUser(String pUser) {
        currentUser = pUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}