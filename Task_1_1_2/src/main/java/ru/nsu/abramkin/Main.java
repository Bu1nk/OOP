package ru.nsu.abramkin;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Добро пожаловать в Блэкджек!");
        int nowRound = 1;
        Scanner chContin = new Scanner(System.in);
        BlackjackGame game = new BlackjackGame();
        while (true) {
            if (nowRound != 1) {
                System.out.println(
                        "Введите '1', если хотите продолжить, и '0', чтобы закончить игру:"
                );
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
