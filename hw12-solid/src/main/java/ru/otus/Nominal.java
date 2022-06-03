package ru.otus;

public enum Nominal {
    N_100(100),
    N_200(200),
    N_500(500),
    N_1000(1000);

    private final int value;

    Nominal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}