package com.pFrame;

import java.awt.Color;

public class Pixel {
    private Color color;
    private char ch;

    public Pixel(Color color, char ch){
        this.ch=ch;
        this.color=color;
    }

    public Color getColor(){
        return this.color;
    }

    public char getch(){
        return this.ch;
    }
    
}
