package ru.nsu.abramkin;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс, представляющий общие операции с графом.
 * Поддерживает добавление и удаление вершин, добавление и удаление рёбер,
 * получение списка соседей вершины, загрузку графа из файла, а также проверку равенства и преобразование в строку.
 */
public interface Graph {

    /**
     * Добавляет новую вершину в граф без указания индекса.
     */
    void addVertex();

    /**
     * Удаляет вершину из графа по её индексу.
     *
     * @param v индекс вершины, которую нужно удалить.
     * @throws IllegalArgumentException если вершина с указанным индексом не существует.
     */
    void removeVertex(int v);

    /**
     * Добавляет ребро между двумя вершинами.
     *
     * @param v1 индекс первой вершины.
     * @param v2 индекс второй вершины.
     * @throws IllegalArgumentException если одна или обе вершины с указанными индексами не существуют.
     */
    void addEdge(int v1, int v2);

    /**
     * Удаляет ребро между двумя вершинами.
     *
     * @param v1 индекс первой вершины.
     * @param v2 индекс второй вершины.
     * @throws IllegalArgumentException если одна или обе вершины с указанными индексами не существуют.
     */
    void removeEdge(int v1, int v2);

    /**
     * Возвращает список индексов соседних вершин для заданной вершины.
     *
     * @param v индекс вершины.
     * @return список соседних вершин.
     * @throws IllegalArgumentException если вершина с указанным индексом не существует.
     */
    List<Integer> getNeighbors(int v);

    /**
     * Загружает граф из файла.
     * Файл должен содержать описание графа в формате:
     * первая строка — количество вершин и рёбер,
     * последующие строки — пары индексов вершин, соединённых рёбрами.
     *
     * @param filename имя файла, содержащего описание графа.
     * @throws IOException если произошла ошибка ввода-вывода при чтении файла.
     * @throws IllegalArgumentException если файл содержит некорректное описание графа.
     */
    void loadFromFile(String filename) throws IOException;

    /**
     * Сравнивает текущий граф с другим объектом.
     *
     * @param o объект для сравнения.
     * @return true, если объекты равны, иначе false.
     */
    boolean equals(Object o);

    /**
     * Преобразует граф в строку для текстового представления.
     *
     * @return строковое представление графа.
     */
    String toString();
}