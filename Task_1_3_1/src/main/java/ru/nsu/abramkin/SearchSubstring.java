package ru.nsu.abramkin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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

        try (BufferedInputStream inputStream = new BufferedInputStream(
                new FileInputStream(fileName))) {

            byte[] buffer = new byte[1024];
            StringBuilder window = new StringBuilder();
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String chunk = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                window.append(chunk);

                int windowIndex;
                while ((windowIndex = window.indexOf(searchString)) != -1) {
                    result.add(index + windowIndex);
                    window.delete(0, windowIndex + 1);
                    index += windowIndex + 1;
                }

                if (window.length() >= searchLength) {
                    int excessLength = window.length() - searchLength + 1;
                    window.delete(0, excessLength);
                    index += excessLength;
                }
            }
        }

        return result;
    }
}
