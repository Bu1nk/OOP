package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Dealer.
 * Проверяем логику дилера — нужно ли брать карту при определенном количестве очков.
 */
class DealerTest {

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
