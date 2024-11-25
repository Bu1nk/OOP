package ru.nsu.abramkin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для класса SearchSubstring, предназначенные для проверки корректности
 * поиска подстроки в текстовом файле.
 *
 * Тесты проверяют различные сценарии работы метода find с маленькими и большими
 * файлами, а также с различными вариантами вхождений подстроки.
 */
public class SearchSubstringTest {

    private File tempFile;

    /**
     * Метод, который выполняется перед каждым тестом.
     * Создает временный файл для использования в тестах.
     *
     * @throws IOException если возникла ошибка при создании файла
     */
    @BeforeEach
    public void setUp() throws IOException {
        tempFile = File.createTempFile("testFile", ".txt");
    }

    /**
     * Метод, который выполняется после каждого теста.
     * Удаляет временный файл, созданный в setUp.
     */
    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    /**
     * Тест для маленького файла, содержащего одно вхождение подстроки.
     * Ожидается, что метод вернет список с индексами начала вхождения подстроки.
     *
     * @throws IOException если возникла ошибка при чтении файла
     */
    @Test
    public void testSmallFileSingleOccurrence() throws IOException {
        writeToFile(tempFile, "абракадабра");
        List<Integer> result = SearchSubstring.find(
                tempFile.getAbsolutePath(), "бра");
        assertEquals(List.of(1, 8), result);
    }

    /**
     * Тест для маленького файла, в котором подстрока не встречается.
     * Ожидается, что метод вернет пустой список.
     *
     * @throws IOException если возникла ошибка при чтении файла
     */
    @Test
    public void testSmallFileNoOccurrence() throws IOException {
        writeToFile(tempFile, "абракадабра");
        List<Integer> result = SearchSubstring.find(
                tempFile.getAbsolutePath(), "кра");
        assertEquals(List.of(), result);
    }

    /**
     * Тест для маленького файла с несколькими вхождениями подстроки.
     * Ожидается, что метод вернет список с индексами всех вхождений подстроки.
     *
     * @throws IOException если возникла ошибка при чтении файла
     */
    @Test
    public void testSmallFileMultipleOccurrences() throws IOException {
        writeToFile(tempFile, "абраабрабрабра");
        List<Integer> result = SearchSubstring.find(
                tempFile.getAbsolutePath(), "бра");
        assertEquals(List.of(1, 5, 8, 11), result);
    }

    /**
     * Тест для большого файла, содержащего множество вхождений подстроки.
     * Ожидается, что метод вернет список с индексами всех вхождений подстроки
     * с учетом их положения в файле.
     *
     * @throws IOException если возникла ошибка при чтении файла
     */
    @Test
    public void testLargeFileWithMultipleOccurrences() throws IOException {
        generateLargeFile(tempFile, "бра", 100000, "кедфыьлппге");
        List<Integer> result = SearchSubstring.find(
                tempFile.getAbsolutePath(), "бра");

        for (int i = 0; i < 100000; i++) {
            // Проверяем, что каждый индекс соответствует правильному положению
            assertEquals(i + 13 * i, result.get(i));
        }
    }

    /**
     * Тест для большого файла, в котором нет вхождений подстроки.
     * Ожидается, что метод вернет пустой список.
     *
     * @throws IOException если возникла ошибка при чтении файла
     */
    @Test
    public void testLargeFileWithNoOccurrences() throws IOException {
        generateLargeFile(tempFile, "", 100000, "abcdefg");
        List<Integer> result = SearchSubstring.find(
                tempFile.getAbsolutePath(), "бра");
        assertEquals(List.of(), result, "Ожидается, что 'бра' не найдено в файле");
    }

    /**
     * Тест сравнивает результаты работы алгоритма поиска подстроки с
     * результатами стандартного метода String.indexOf на случайных данных.
     *
     * @throws IOException если возникает ошибка записи в файл или чтения из него
     */
    @Test
    public void testRandomStringComparison() throws IOException {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String searchString = "test";
        int fileLength = 1_000_000;

        StringBuilder randomContent = new StringBuilder(fileLength);
        for (int i = 0; i < fileLength; i++) {
            randomContent.append(
                    alphabet.charAt(
                            (int) (Math.random() * alphabet.length())));
        }

        for (int i = 0; i < 100; i++) {
            int randomIndex = (int) (Math.random()
                    * (fileLength
                    - searchString.length()));
            randomContent.replace(randomIndex,
                    randomIndex + searchString.length(),
                    searchString);
        }

        writeToFile(tempFile, randomContent.toString());

        List<Integer> expectedResults = new ArrayList<>();
        int index = 0;
        String content = randomContent.toString();
        while ((index = content.indexOf(searchString, index)) != -1) {
            expectedResults.add(index);
            index += 1;
        }

        List<Integer> actualResults = SearchSubstring.find(
                tempFile.getAbsolutePath(), searchString);
        assertEquals(expectedResults, actualResults);
    }

    /**
     * Вспомогательный метод для записи строки в файл.
     *
     * @param file    файл, в который нужно записать данные
     * @param content строка, которую нужно записать в файл
     * @throws IOException если возникла ошибка при записи в файл
     */
    private void writeToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    /**
     * Вспомогательный метод для создания большого файла с повторяющимися строками.
     * Генерирует файл, в котором заданная подстрока записана несколько раз,
     * каждый раз с добавлением строки "padding" (заполнения) после подстроки.
     *
     * @param file         файл, в который нужно записать данные
     * @param searchString строка, которую нужно записывать
     * @param repetitions  количество повторений строки
     * @param padding      строка, которая будет добавлена после каждой подстроки
     * @throws IOException если возникла ошибка при записи в файл
     */
    private void generateLargeFile(File file, String searchString,
                                   int repetitions, String padding)
            throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < repetitions; i++) {
                writer.write(searchString + padding);
            }
        }
    }
}
