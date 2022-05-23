package ru.otus;

import java.util.*;

public class ATM {

    private List<Cell> cells;

    public ATM(List<Cell> cells) {
        this.cells = cells;
    }

    public void operationAddCell(Cell cell) {
        if (this.cells == null) {
            this.cells = new ArrayList<>();
        }
        this.cells.add(cell);
    }

    public int getBalance() {
        return cells.stream().mapToInt(Cell::getBalance).sum();
    }

    private int calculation(int sum) {
        List<Integer> value = new ArrayList<>(List.of(sum));
        Map<Integer, Integer> tempValue = new HashMap<>();

        cells.stream().sorted(Comparator.comparing(Cell::getNOMINAL).reversed())
                .forEachOrdered(cell -> {
                    if (cell.getBalance() > 0) {
                        int count = value.get(0) / cell.getNOMINAL();
                        if (count > 0 && count <= cell.getBalance() / cell.getNOMINAL()) {
                            value.set(0, value.get(0) - count * cell.getNOMINAL());
                            tempValue.put(cell.getNOMINAL(), count);
                        }
                    }
                });
        if (value.get(0) == 0) {
            tempValue.forEach((key, value1) -> {
                cells.stream().filter(cell -> cell.getNOMINAL() == key).forEach(s -> s.getMoney(value1));
            });
            return 1;
        } else {
            return -1;
        }
    }

    public void operationGetMoney(int sum) {
        if (sum > getBalance()) {
            throw new RuntimeException("Не достаточно денег в банкомате");
        }
        int result = calculation(sum);
        if (result == 1) {
            System.out.println("Операция успешна, остаток средств в банкомате: " + getBalance() + " -- " + cells);
        } else {
            System.out.println("Не корректно введена сумма, остаток средств в банкомате: " + getBalance());
        }
    }
}
