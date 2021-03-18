package com.stankovicjovan.simplecalculator;

import java.util.ArrayList;

public class History {
    private ArrayList<String> calcHistory;
    private static History history;
    private static int current;

    private History() {
        calcHistory = new ArrayList<>();
        addCalcHistory("");
    }

    public static History getInstance() {
        if(history == null)
            history = new History();

        return history;
    }

    public int getSize(){
        return calcHistory.size();
    }

    public String getCalcHistory(int n) {
        return calcHistory.get(n);
    }

    public void addCalcHistory(String exp) {
        calcHistory.add(exp);
    }

    public int getCurrent(){
        return current;
    }

    public void currentIncrement(){
        current++;
    }

    public void currentDecrement(){
        current--;
    }

    public void clearHistory(){
        calcHistory.clear();
        addCalcHistory("");
        current = 0;
    }
}
