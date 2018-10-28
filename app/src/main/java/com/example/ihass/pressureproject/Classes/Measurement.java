package com.example.ihass.pressureproject.Classes;

public class Measurement {
    private int UpperMeasure, LowerMeasure;
    private String Day;
    private int Time;

    public Measurement() {
    }

    public Measurement(int upperMeasure, int lowerMeasure, String day, int time) {
        UpperMeasure = upperMeasure;
        LowerMeasure = lowerMeasure;
        Day = day;
        Time = time;
    }

    public int getUpperMeasure() {
        return UpperMeasure;
    }

    public void setUpperMeasure(int upperMeasure) {
        UpperMeasure = upperMeasure;
    }

    public int getLowerMeasure() {
        return LowerMeasure;
    }

    public void setLowerMeasure(int lowerMeasure) {
        LowerMeasure = lowerMeasure;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }
}
