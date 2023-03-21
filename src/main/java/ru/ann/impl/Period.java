package ru.ann.impl;

public enum Period {
    TOMORROW(1),
    WEEK(7);
    private final int dayQuantity;

    Period(int dayQuantity){
        this.dayQuantity = dayQuantity;
    }

    public int getDayQuantity() {
        return dayQuantity;
    }
}
