package ru.otus;

public interface ATM {

    void operationAddCell(Cell cell);

    int getBalance();

    void operationGetMoney(int sum);
}
