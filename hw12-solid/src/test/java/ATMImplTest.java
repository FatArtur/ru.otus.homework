import org.junit.jupiter.api.*;
import ru.otus.ATM;
import ru.otus.ATMImpl;
import ru.otus.Cell;
import ru.otus.Nominal;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ATMImplTest {

    List<Cell> cells;
    ATM atm;

    @BeforeEach
    void setUp() {
        cells = new ArrayList<>();
        cells.add(new Cell(Nominal.N_100.getValue(), 100));
        cells.add(new Cell(Nominal.N_200.getValue(), 50));
        cells.add(new Cell(Nominal.N_500.getValue(), 50));
        atm = new ATMImpl(cells);
    }

    @AfterEach
    void tearDown() {
        System.out.println();
    }

    @Test
    void testPlus() {
        int balance = 19_300;
        atm.operationGetMoney(25000);
        atm.operationGetMoney(700);

        assertThat(atm.getBalance()).isEqualTo(balance);
    }

    @Test
    void testMinus() {
        int balance = 52_200;
        atm.operationGetMoney(1700);
        atm.operationAddCell(new Cell(Nominal.N_1000.getValue(), 10));
        atm.operationGetMoney(1100);

        assertThat(atm.getBalance()).isEqualTo(balance);
    }

    @Test
    void testAnotherSum() {
        int balance = 44_300;
        atm.operationGetMoney(700);
        atm.operationGetMoney(150);

        assertThat(atm.getBalance()).isEqualTo(balance);
    }

    @Test()
    void testError() {
        atm.operationGetMoney(700);

        Assertions.assertThrows(RuntimeException.class, () -> {
            atm.operationGetMoney(45000);
        });
    }

}
