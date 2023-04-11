package ru.ann.currencyrate.domain.type;

public enum Period {
    DAY(1),
    TOMORROW(1),
    WEEK(7),
    MONTH(31);


    private final int dayQuantity;

    Period(int dayQuantity) {
        this.dayQuantity = dayQuantity;
    }

    public int getDayQuantity() {
        return dayQuantity;
    }

}
