package ru.otus;

public class Cell {
    private final int NOMINAL;
    private int value;

    public Cell(int nominal, int value) {
        this.NOMINAL = nominal;
        this.value = value;
    }

    public int getNOMINAL() {
        return NOMINAL;
    }

    public void putMoney(int val) {
        this.value += val;
    }

    public void getMoney(int val) {
        this.value -= val;
    }

    public int getBalance() {
        return NOMINAL * value;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "NOMINAL=" + NOMINAL +
                ", value=" + value +
                '}';
    }
}
