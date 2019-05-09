package com.scoobay.rass.q;

public class myStatic {

    static int counter =0;
    static  String store;
    static int score=0;
    static String saveans;
    public int increment(){
        counter++;
        return counter;
    }

    public void resetSta(int t){
        counter=t;
        score=0;
    }

    public  void setStore(String val){
        store=val;
    }
    public String getStore(){
        return  store;
    }

    public int addScore(){
        score++;
     return score ;
    }

    public  void setAns(String val){
        saveans=val;
    }

    public String getAns(){
        return  saveans;
    }
}

