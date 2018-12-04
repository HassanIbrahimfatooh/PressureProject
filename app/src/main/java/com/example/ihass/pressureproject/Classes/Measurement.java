package com.example.ihass.pressureproject.Classes;

public class Measurement {
    private int UpperMeasure, LowerMeasure;
    private String Day;
    private String Time;

    public Measurement() {
    }

    public Measurement(int upperMeasure, int lowerMeasure, String day, String time) {
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
