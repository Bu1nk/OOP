package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  Тесты для класса Card.
 *  Тестируем корректное создание карты и методы доступа к её параметрам.
 */
class CardTest {

    /**
     * Тест для проверки корректного создания карты.
     */
    @Test
    void testCardCreation() {
        Card card = new Card("Черви", "Туз", 11);
        assertEquals("Черви", card.getSuit());
        assertEquals("Туз", card.getRank());
        assertEquals(11, card.getValue());
    }

    /**
     * Тест для проверки корректного вывода карты в строку.
     */
    @Test
    void testCardToString() {
        Card card = new Card("Пики", "Король", 10);
        assertEquals("Король Пики (10)", card.toString());
    }
}
