package ru.nsu.abramkin;

/**
 * Класс Card - представляет карту с мастью, достоинством и значением.
 */
public class Card {
    private String suit;  // Масть карты
    private String rank;  // Достоинство карты
    private int value;    // Очки карты

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " " + suit + " (" + value + ")";
    }
}
