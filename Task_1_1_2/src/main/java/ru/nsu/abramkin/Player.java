package ru.nsu.abramkin;

import java.util.ArrayList;
import java.util.List;

/**
 *Класс Player - представляет игрока и хранит его карты и очки.
 */
public class Player {
    protected List<Card> hand = new ArrayList<>();  // Карты на руках
    protected int score = 0;  // Сумма очков

    /**
     * Метод добавления карты в руку.
     */
    public void takeCard(Card card) {
        hand.add(card);
        updateScore();  // Обновляем количество очков после получения карты
    }

    /**
     * Метод для обновления суммы очков, учитывая туз.
     */
    public void updateScore() {
        score = 0;
        int aceCount = 0;  // Считаем количество тузов

        // Подсчитываем сумму очков
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);  // Получаем карту по индексу
            score += card.getValue();  // Добавляем значение карты к счету
            if (card.getRank().equals("Туз")) {
                aceCount++;  // Считаем тузы
            }
        }


        // Если сумма больше 21 и есть тузы, меняем их значение на 1
        while (score > 21 && aceCount > 0) {
            score -= 10;  // Уменьшаем очки на 10 за каждый туз
            aceCount--;
        }
    }

    public int getScore() {
        return score;
    }

    public List<Card> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return hand.toString() + " > " + score;
    }
}
