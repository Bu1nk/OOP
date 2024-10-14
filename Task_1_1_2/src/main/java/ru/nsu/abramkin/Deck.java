package ru.nsu.abramkin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс Deck - представляет колоду карт и методы для тасовки и вытаскивания карт.
 */
public class Deck {
    private List<Card> cards = new ArrayList<>();

    public Deck() {
        // Создаем массивы мастей, достоинств и соответствующих им очков
        String[] suits = {"Черви", "Бубны", "Трефы", "Пики"};
        String[] ranks = {
                "Двойка", "Тройка", "Четверка", "Пятерка",
                "Шестерка", "Семерка", "Восьмерка", "Девятка",
                "Десятка", "Валет", "Дама", "Король", "Туз"
        };
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};  // Вес карт

        // Создаем карты и добавляем их в колоду
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                cards.add(new Card(suits[i], ranks[j], values[j]));
            }
        }

        // Перемешиваем колоду
        Collections.shuffle(cards);
    }

    /**
     * Метод для вытаскивания карты из колоды.
     */
    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }

    /**
     * Метод, показывающий количество карт в колоде.
     */
    public int hmCards() {
        return cards.size();
    }
}
