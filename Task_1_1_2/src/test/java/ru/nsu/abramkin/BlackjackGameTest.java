package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса BlackjackGame.
 * Проверяем общий игровой процесс — раздачу карт, работу методов подсчета очков.
 */
class BlackjackGameTest {

    /**
     * Тест для проверки счета по раундам в начале игры.
     */
    @Test
    void testInitialScores() {
        BlackjackGame game = new BlackjackGame();
        assertEquals(0, game.showPlScore());
        assertEquals(0, game.showDlScore());
    }
}
