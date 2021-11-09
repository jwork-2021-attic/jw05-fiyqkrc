package com.pFrame;

import java.util.HashMap;
import java.util.Map;

public class Position {
    private int x;
    private int y;
    private static Map<Integer, Map<Integer, Position>> PositionPool=new HashMap<Integer,Map<Integer,Position>>();

    private static Position[][] positions=new Position[1000][1000];

    private Position(int a, int b) {
        x = a;
        y = b;
        //System.out.println("hello");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEqualWith(Position p) {
        return (p.getX() == this.x) && (p.getY() == this.y);
    }

    @Override
    public String toString() {
        return "{"+String.valueOf(x)+","+String.valueOf(y)+"}";
    }

    @Override
    public Position clone(){
        return this;
    }

    static private Position findPosition(int x,int y){
        if(PositionPool.containsKey (x)){
            Map<Integer, Position> map=PositionPool.get(x);
            if(map.containsKey(y)){
                return map.get(y);
            }
            else{
                Position position=new Position(x,y);
                map.put(y, position);
                return position;
            }
        }
        else{
            Map<Integer, Position> map=new HashMap<>();
            Position position=new Position( x, y);
            PositionPool.put(x, map);
            map.put(y,position);
            return position;
        }
    }

    public static Position getPosition (int x, int y){
        /*
        if(positions[x][y]==null){
            Position position=new Position(x, y);
            positions[x][y]=position;
            return position;
        }
        else{
            return positions[x][y];
        }
        
        return Position.findPosition (x, y);
        */
        return new Position(x, y);
    }
}