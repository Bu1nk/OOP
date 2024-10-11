package ru.nsu.abramkin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Класс Card - представляет карту с мастью, достоинством и значением.
 */
class Card {
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

/**
 * Класс Deck - представляет колоду карт и методы для тасовки и вытаскивания карт.
 */
class Deck {
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

/**
 *Класс Player - представляет игрока и хранит его карты и очки.
 */
class Player {
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

/**
 * Класс Dealer - наследует Player и добавляет поведение дилера.
 */
class Dealer extends Player {
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
        System.out.println("    Карты дилера: " + "[" + hand.get(0) + ", <закрытая карта>]");
    }
}

/**
 * Основной класс игры BlackjackGame.
 */
class BlackjackGame {
    private Deck deck;  // Колода карт
    private Player player;  // Игрок
    private Dealer dealer;  // Дилер
    private int plScore;
    private int dlScore;

    public BlackjackGame() {
        deck = new Deck();  // Создаем новую колоду
        int plScore = 0;
        int dlScore = 0;
    }

    /**
     * Основной игровой процесс.
     */
    public void play() {
        if (deck.hmCards() <= 18) {
            System.out.println();
            System.out.println("-------");
            deck = new Deck();
            System.out.println("Колода обновлена!");
            System.out.println("-------");
            System.out.println();
        }

        player = new Player();  // Создаем игрока
        dealer = new Dealer();  // Создаем дилера

        // Начальный раунд - раздаем по 2 карты игроку и дилеру
        player.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());

        // Показ карт игрока и первой карты дилера
        System.out.println("Дилер раздал карты");
        System.out.println("    Ваши карты: " + player);
        dealer.revealFirstCard();
        System.out.println();

        if (player.getScore() == 21) {
            System.out.print("Вы выиграли раунд!");
            plScore++;
        } else {
            // Ход игрока
            System.out.println("Ваш ход");
            System.out.println("-------");
            Scanner scanner = new Scanner(System.in);
            Card newCard;
            while (true) {
                System.out.println("Введите '1', чтобы взять карту, и '0', чтобы остановиться:");
                int choice = scanner.nextInt();  // Получаем выбор игрока
                if (choice == 1) {
                    newCard = deck.drawCard();
                    player.takeCard(newCard);  // Игрок берет карту
                    System.out.println("Вы открыли карту " + newCard);
                    System.out.println("    Ваши карты: " + player);
                    dealer.revealFirstCard();
                    System.out.println();
                    // Если очков больше 21, игрок проигрывает
                    if (player.getScore() > 21) {
                        System.out.print("Вы проиграли раунд.");
                        dlScore++;
                        return;
                    }
                } else {
                    break;  // Игрок останавливается
                }
            }

            if (player.getScore() == 21) {
                System.out.print("Вы выиграли раунд!");
                plScore++;
            } else {
                // Ход дилера
                System.out.println();
                System.out.println("Ход дилера");
                System.out.println("-------");
                System.out.println("Дилер открывает закрытую карту " + dealer.hand.get(1));
                System.out.println("    Ваши карты: " + player);
                System.out.println("    Карты дилера: " + dealer);  // Показываем карты дилера
                System.out.println();
                while (dealer.shouldTakeCard()) {
                    newCard = deck.drawCard();
                    dealer.takeCard(newCard);  // Дилер берет карту
                    System.out.println("Дилер открывает карту " + newCard);
                    System.out.println("    Ваши карты: " + player);
                    System.out.println("    Карты дилера: " + dealer);
                    System.out.println();
                }

                // Определение победителя
                if (dealer.getScore() > 21) {
                    System.out.print("Вы выиграли раунд!");
                    plScore++;
                } else if (player.getScore() > dealer.getScore()) {
                    System.out.print("Вы выиграли раунд!");
                    plScore++;
                } else if (player.getScore() == dealer.getScore()) {
                    System.out.print("Ничья!");
                } else {
                    System.out.print("Дилер выиграл.");
                    dlScore++;
                }
            }
        }
    }

    public int showPlScore() {
        return plScore;
    }

    public int showDlScore() {
        return dlScore;
    }
}

public class Main {

    public static void main(String[] args) {

        System.out.println("Добро пожаловать в Блэкджек!");
        int nowRound = 1;
        Scanner chContin = new Scanner(System.in);
        BlackjackGame game = new BlackjackGame();
        while (true) {
            if (nowRound != 1) {
                System.out.println("Введите '1', если хотите продолжить, и '0', чтобы закончить игру:");
                int choice = chContin.nextInt();  // Получаем выбор игрока
                if (choice == 0) {
                    break;
                }
            }

            System.out.println("Раунд " + nowRound);
            game.play(); // Запуск игры
            if (game.showPlScore() > game.showDlScore()) {
                System.out.println(" Счет " + game.showPlScore() +
                        ":" + game.showDlScore() + " в Вашу пользу");
            } else if (game.showPlScore() == game.showDlScore()) {
                System.out.println(" Счет " + game.showPlScore() +
                        ":" + game.showDlScore());
            } else {
                System.out.println(" Счет " + game.showDlScore() +
                        ":" + game.showPlScore() + " в пользу дилера");
            }
            nowRound++;
        }
    }
}
