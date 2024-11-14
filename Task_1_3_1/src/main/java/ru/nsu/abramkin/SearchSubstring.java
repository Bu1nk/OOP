package ru.nsu.abramkin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для поиска вхождений подстроки в большом текстовом файле.
 *
 * Предназначен для работы с файлами большого объема, когда размер
 * файла превышает объем оперативной памяти. Поддерживает поиск
 * подстрок с учетом кодировки UTF-8.
 */
public class SearchSubstring {

    /**
     * Метод для поиска индексов начала вхождений подстроки в текстовом файле.
     *
     * @param fileName     путь к файлу, в котором осуществляется поиск
     * @param searchString подстрока, которую нужно найти в файле
     * @return список индексов начала каждого вхождения подстроки
     * @throws IOException если файл не найден или возникла ошибка чтения
     */
    public static List<Integer> find(String fileName, String searchString)
            throws IOException {
        List<Integer> result = new ArrayList<>();
        int searchLength = searchString.length();
        int index = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName),
                        StandardCharsets.UTF_8)
        )) {
            char[] buffer = new char[1024];
            StringBuilder window = new StringBuilder();

            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                window.append(buffer, 0, bytesRead);

                int windowIndex;
                while ((windowIndex = window.indexOf(searchString)) != -1) {
                    result.add(index + windowIndex);
                    window.delete(0, windowIndex + 1);
                    index += windowIndex + 1;
                }

                if (window.length() > searchLength) {
                    int excessLength = window.length() - searchLength;
                    window.delete(0, excessLength);
                    index += excessLength;
                }
            }
        }

        return result;
    }
}
