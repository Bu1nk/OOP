package ru.nsu.abramkin;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

/**
 * Класс {@code HashTable} представляет собой параметризованный контейнер для хранения пар ключ-значение.
 * Позволяет добавлять, искать, удалять и обновлять элементы за константное время.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashTable<K, V> {
    private ArrayList<LinkedList<Entry<K, V>>> table;
    private int size;
    private int capacity;
    private int modCount;

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    /**
     * Создает пустую хеш-таблицу с емкостью по умолчанию.
     */
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Создает пустую хеш-таблицу с указанной начальной емкостью.
     *
     * @param initialCapacity начальная емкость хеш-таблицы
     */
    public HashTable(int initialCapacity) {
        this.capacity = initialCapacity;
        this.size = 0;
        this.modCount = 0;
        this.table = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            table.add(new LinkedList<>());
        }
    }

    /**
     * Вычисляет хеш-индекс для заданного ключа.
     *
     * @param key ключ для которого вычисляется хеш
     * @return индекс в массиве таблицы
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Увеличивает размер хеш-таблицы и перераспределяет существующие элементы при превышении
     * порога загрузки {@code LOAD_FACTOR}.
     */
    private void resize() {
        if (size >= capacity * LOAD_FACTOR) {
            ArrayList<LinkedList<Entry<K, V>>> oldTable = table;
            capacity *= 2;
            size = 0;
            table = new ArrayList<>(capacity);
            for (int i = 0; i < capacity; i++) {
                table.add(new LinkedList<>());
            }
            for (LinkedList<Entry<K, V>> bucket : oldTable) {
                for (Entry<K, V> entry : bucket) {
                    put(entry.key, entry.value);
                }
            }
        }
    }

    /**
     * Добавляет новую пару ключ-значение в хеш-таблицу или обновляет значение, если ключ уже существует.
     *
     * @param key   ключ
     * @param value значение
     */
    public void put(K key, V value) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table.get(index);
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                modCount++;
                return;
            }
        }
        bucket.add(new Entry<>(key, value));
        size++;
        modCount++;
        resize();
    }

    /**
     * Возвращает значение, связанное с указанным ключом.
     *
     * @param key ключ для поиска
     * @return значение, если ключ найден, иначе {@code null}
     */
    public V get(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table.get(index);
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Удаляет пару ключ-значение по указанному ключу.
     *
     * @param key ключ элемента для удаления
     * @return {@code true}, если элемент был удален, {@code false} иначе
     */
    public boolean remove(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table.get(index);
        Iterator<Entry<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.key.equals(key)) {
                iterator.remove();
                size--;
                modCount++;
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, содержится ли указанный ключ в хеш-таблице.
     *
     * @param key ключ для проверки
     * @return {@code true}, если ключ присутствует, {@code false} иначе
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Обновляет значение, связанное с указанным ключом. Если ключ отсутствует, добавляет новую пару.
     *
     * @param key   ключ
     * @param value новое значение
     */
    public void update(K key, V value) {
        put(key, value);
    }

    /**
     * Возвращает количество элементов в хеш-таблице.
     *
     * @return количество элементов
     */
    public int size() {
        return size;
    }

    /**
     * Сравнивает текущую хеш-таблицу с другой на равенство по содержимому.
     *
     * @param other другая хеш-таблица для сравнения
     * @return {@code true}, если таблицы равны, {@code false} иначе
     */
    public boolean equals(HashTable<K, V> other) {
        if (this.size != other.size) return false;
        for (K key : this.keys()) {
            if (!Objects.equals(this.get(key), other.get(key))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает строковое представление хеш-таблицы в формате {@code {key1=value1, key2=value2, ...}}.
     *
     * @return строковое представление таблицы
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (LinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                sb.append(entry.key).append("=").append(entry.value).append(", ");
            }
        }
        if (sb.length() > 1) sb.setLength(sb.length() - 2); // Убираем последнюю запятую и пробел
        sb.append("}");
        return sb.toString();
    }

    /**
     * Возвращает итератор для обхода всех элементов хеш-таблицы.
     * При изменении таблицы во время итерации выбрасывается {@link ConcurrentModificationException}.
     *
     * @return итератор по элементам таблицы
     */
    public Iterable<Entry<K, V>> entries() {
        return () -> new Iterator<Entry<K, V>>() {
            private final int expectedModCount = modCount;
            private int bucketIndex = 0;
            private Iterator<Entry<K, V>> bucketIterator = getBucketIterator();

            /**
             * Возвращает итератор для текущего непустого бакета.
             *
             * @return итератор или {@code null}, если больше бакетов нет
             */
            private Iterator<Entry<K, V>> getBucketIterator() {
                while (bucketIndex < capacity && table.get(bucketIndex).isEmpty()) {
                    bucketIndex++;
                }
                return bucketIndex < capacity ? table.get(bucketIndex).iterator() : null;
            }

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) throw new ConcurrentModificationException();
                return bucketIterator != null && bucketIterator.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                if (modCount != expectedModCount) throw new ConcurrentModificationException();
                Entry<K, V> next = bucketIterator.next();
                if (!bucketIterator.hasNext()) {
                    bucketIndex++;
                    bucketIterator = getBucketIterator();
                }
                return next;
            }
        };
    }

    /**
     * Возвращает набор всех ключей, содержащихся в хеш-таблице.
     *
     * @return {@link Set} всех ключей
     */
    public Set<K> keys() {
        Set<K> keys = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                keys.add(entry.key);
            }
        }
        return keys;
    }

    /**
     * Внутренний класс {@code Entry} представляет собой пару ключ-значение.
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    public static class Entry<K, V> {
        public K key;
        public V value;

        /**
         * Создает новую пару ключ-значение.
         *
         * @param key   ключ
         * @param value значение
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Возвращает строковое представление пары ключ-значение.
         *
         * @return строка в формате {@code key=value}
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
