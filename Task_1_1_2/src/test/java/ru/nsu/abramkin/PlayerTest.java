package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для класса Player.
 * Проверяем корректность добавления карт и подсчета очков, особенно с учетом тузов.
 */
class PlayerTest {

    /**
     * Тест для проверки подсчета очков.
     */
    @Test
    void testPlayerScoreWithRegularCards() {
        Player player = new Player();
        player.takeCard(new Card("Черви", "Девятка", 9));
        player.takeCard(new Card("Бубны", "Валет", 10));
        assertEquals(19, player.getScore());
    }

    /**
     * Тест для проверки подсчета очков с тузом больше 21.
     */
    @Test
    void testPlayerScoreWithAce() {
        Player player = new Player();
        player.takeCard(new Card("Черви", "Туз", 11));
        player.takeCard(new Card("Бубны", "Девятка", 9));
        assertEquals(20, player.getScore());

        player.takeCard(new Card("Трефы", "Двойка", 2));
        assertEquals(12, player.getScore()); // Туз становится 1, чтобы не было перебора
    }

    /**
     * Тест для проверки подсчета количества карт у игрока.
     */
    @Test
    void testPlayerHand() {
        Player player = new Player();
        player.takeCard(new Card("Черви", "Туз", 11));
        player.takeCard(new Card("Бубны", "Девятка", 9));
        assertEquals(2, player.getHand().size());
    }
}
