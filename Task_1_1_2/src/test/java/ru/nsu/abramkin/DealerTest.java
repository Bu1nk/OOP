package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Dealer.
 * Проверяем логику дилера — нужно ли брать карту при определенном количестве очков.
 */
class DealerTest {

    /**
     * Тест для проверки подсчета очков.
     */
    @Test
    void testDealerScoreWithRegularCards() {
        Dealer dealer = new Dealer();
        dealer.takeCard(new Card("Черви", "Девятка", 9));
        dealer.takeCard(new Card("Бубны", "Валет", 10));
        assertEquals(19, dealer.getScore());
    }

    /**
     * Тест для проверки подсчета очков с тузом больше 21.
     */
    @Test
    void testDealerScoreWithAce() {
        Dealer dealer = new Dealer();
        dealer.takeCard(new Card("Черви", "Туз", 11));
        dealer.takeCard(new Card("Бубны", "Девятка", 9));
        assertEquals(20, dealer.getScore());

        dealer.takeCard(new Card("Трефы", "Двойка", 2));
        assertEquals(12, dealer.getScore()); // Туз становится 1, чтобы не было перебора
    }

    /**
     * Тест для проверки подсчета количества карт у игрока.
     */
    @Test
    void testDealerHand() {
        Dealer dealer = new Dealer();
        dealer.takeCard(new Card("Черви", "Туз", 11));
        dealer.takeCard(new Card("Бубны", "Девятка", 9));
        assertEquals(2, dealer.getHand().size());
    }

    /**
     * Тест, проверяющий корректность добора карт дилером.
     */
    @Test
    void testShouldTakeCard() {
        Dealer dealer = new Dealer();
        dealer.takeCard(new Card("Черви", "Шестерка", 6));
        dealer.takeCard(new Card("Бубны", "Девятка", 9));
        assertTrue(dealer.shouldTakeCard()); // Если меньше 17 очков, должен брать карту

        dealer.takeCard(new Card("Пики", "Тройка", 3));
        assertFalse(dealer.shouldTakeCard()); // 18 очков, больше 17, карту брать не нужно
    }
}
