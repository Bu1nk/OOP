package ru.nsu.abramkin;

import java.util.Scanner;

/**
 * Основной класс игры BlackjackGame.
 */
public class BlackjackGame {
    private Deck deck;  // Колода карт
    public Player player;  // Игрок
    public Dealer dealer;  // Дилер
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
                System.out.println(
                        "Введите '1', чтобы взять карту, и '0', чтобы остановиться:"
                );
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
                System.out.println("Дилер открывает закрытую карту " +
                        dealer.hand.get(1));
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
