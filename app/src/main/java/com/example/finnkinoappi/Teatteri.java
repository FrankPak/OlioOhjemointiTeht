package com.example.finnkinoappi;


public class Teatteri{

    private final int id;
    private final String name;

    public Teatteri(int id, String name){

        this.id = id;
        this.name = name;
    }

    public int GetId(){
        return id;
    }

    public String GetName(){
        return name;
    }
}
