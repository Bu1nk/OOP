package ru.nsu.abramkin;

/**
 * Класс Dealer - наследует Player и добавляет поведение дилера.
 */
public class Dealer extends Player {
    /**
     * Метод проверки, нужно ли дилеру добирать карты (пока меньше 17).
     */
    public boolean shouldTakeCard() {
        return score < 17;
    }

    /**
     * Метод для открытия первой карты.
     */
    public void revealFirstCard() {
        System.out.println("    Карты дилера: " +
                "[" +
                hand.get(0) +
                ", <закрытая карта>]");
    }
}
