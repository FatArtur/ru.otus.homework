package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProcessorEvenSecondTest {

    @Test
    void evenTestWithException() {
        //given
        var message = new Message.Builder(100L)
                .field1("field1")
                .field2("field2")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var processor = new ProcessorEvenSecond(() -> LocalDateTime.of(2022, 1, 1, 12, 30, 46));

        //then
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> processor.process(message));
    }

    @Test
    void evenTestWithoutException() {
        //given
        var message = new Message.Builder(100L)
                .field1("field1")
                .field2("field2")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var processor = new ProcessorEvenSecond(() -> LocalDateTime.of(2022, 1, 1, 12, 30, 45));

        //then
        assertDoesNotThrow(() -> processor.process(message));
    }

}