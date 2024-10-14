package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Deck.
 * Проверяем правильность создания колоды, тасовки и раздачи карт.
 */
class DeckTest {

    /**
     * Тест, проверяющий корректность создания новой колоды.
     */
    @Test
    void testDeckSizeAfterCreation() {
        Deck deck = new Deck();
        assertEquals(52, deck.hmCards());
    }

    /**
     * Тест, проверяющий корректность изменение колоды.
     */
    @Test
    void testDrawCard() {
        Deck deck = new Deck();
        Card drawnCard = deck.drawCard();
        assertNotNull(drawnCard); // Убедимся, что карта вытаскивается
        assertEquals(51, deck.hmCards()); // После вытаскивания карты в колоде должно остаться 51 карта
    }
}
