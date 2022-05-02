package com.example.trabajogrupal;

public class Piece
{
    protected String name;
    protected String color;
    protected int posX;
    protected int posY;
    public Piece(String pName, String pColor, int pPosX,int pPosY)
    {
        name=pName;
        color=pColor;
        posX=pPosX;
        posY=pPosY;
    }

    public int[] returnPosition()
    {
        int[] pos=new int[2];
        pos[0]=posX;
        pos[1]=posY;
        return pos;
    }

    public void changePosition (int x, int y)
    {
        posX = x;
        posY = y;
    }

}
