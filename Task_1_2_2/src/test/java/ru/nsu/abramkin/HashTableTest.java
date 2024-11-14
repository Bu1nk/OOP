package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки корректности работы класса HashTable.
 */
public class HashTableTest {

    private HashTable<String, Number> hashTable;

    /**
     * Инициализация пустой хеш-таблицы перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        hashTable = new HashTable<>();
    }

    /**
     * Тест добавления и получения элементов.
     * Проверяет, что добавленные элементы можно получить по ключу.
     */
    @Test
    public void testPutAndGet() {
        hashTable.put("one", 1);
        assertEquals(1, hashTable.get("one"));

        hashTable.put("two", 2.0);
        assertEquals(2.0, hashTable.get("two"));
    }

    /**
     * Тест обновления значения по существующему ключу.
     * Проверяет, что обновление значения корректно перезаписывает старое значение.
     */
    @Test
    public void testUpdate() {
        hashTable.put("one", 1);
        hashTable.update("one", 1.5);
        assertEquals(1.5, hashTable.get("one"));
    }

    /**
     * Тест удаления элемента по ключу.
     * Проверяет, что удаленный элемент не доступен по ключу и размер таблицы уменьшается.
     */
    @Test
    public void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        assertTrue(hashTable.remove("one"));
        assertNull(hashTable.get("one"));
        assertEquals(1, hashTable.size());
    }

    /**
     * Тест проверки наличия ключа.
     * Проверяет, что метод корректно определяет наличие или отсутствие ключа.
     */
    @Test
    public void testContainsKey() {
        hashTable.put("one", 1);
        assertTrue(hashTable.containsKey("one"));
        assertFalse(hashTable.containsKey("two"));
    }

    /**
     * Тест корректной работы метода toString.
     * Проверяет, что вывод строкового представления содержит все пары ключ-значение.
     */
    @Test
    public void testToString() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        String result = hashTable.toString();
        assertTrue(result.contains("one=1"));
        assertTrue(result.contains("two=2"));
    }

    /**
     * Тест итерации по элементам хеш-таблицы.
     * Проверяет, что итерирование по таблице проходит без ошибок и корректно выбрасывает исключение.
     */
    @Test
    public void testIterationWithConcurrentModificationException() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<HashTable.Entry<String, Number>> iterator = hashTable.entries().iterator();

        assertTrue(iterator.hasNext());
        assertEquals("one=1", iterator.next().toString());

        // Добавление нового элемента должно вызвать ConcurrentModificationException
        hashTable.put("three", 3);
        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    /**
     * Тест на равенство хеш-таблиц.
     * Проверяет, что две таблицы считаются равными при одинаковых элементах.
     */
    @Test
    public void testEquals() {
        HashTable<String, Number> anotherTable = new HashTable<>();

        hashTable.put("one", 1);
        hashTable.put("two", 2.0);

        anotherTable.put("one", 1);
        anotherTable.put("two", 2.0);

        assertTrue(hashTable.equals(anotherTable));
    }

    /**
     * Тест увеличения размера таблицы.
     * Проверяет, что таблица корректно увеличивает емкость при достижении порога.
     */
    @Test
    public void testResize() {
        for (int i = 0; i < 20; i++) {
            hashTable.put("key" + i, i);
        }
        assertEquals(20, hashTable.size());
        assertEquals(20, hashTable.keys().size());
    }
}
