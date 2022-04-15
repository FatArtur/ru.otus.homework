package homework;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final NavigableMap<Customer, String> map = new TreeMap<>((a, b) -> (int) (a.getScores() - b.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> entry = map.firstEntry();
        return Map.entry(new Customer(entry.getKey()), entry.getValue());

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = map.higherEntry(customer);
        return entry != null ? Map.entry(new Customer(entry.getKey()), entry.getValue()) : null;
    }

    public void add(Customer customer, String data) {
        this.map.put(customer, data);
    }

}
