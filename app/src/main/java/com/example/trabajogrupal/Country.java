package com.example.trabajogrupal;

public class Country {
    private String nombre;
    private static Country miCountry=null;
    private Country (){
    }
    public static Country getMiCountry(){
        if (miCountry==null){
            miCountry=new Country();
        }
        return miCountry;
    }

    public  void setNombre(String pNombre){
        nombre=pNombre;
    }
    public String getNombre() {
        return nombre;
    }
}
