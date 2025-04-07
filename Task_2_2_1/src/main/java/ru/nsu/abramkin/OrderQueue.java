package ru.nsu.abramkin;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A thread-safe queue with limited capacity.
 */
public class OrderQueue<T> {
    Queue<T> queue = new LinkedList<>();
    int capacity;
    boolean isClosed = false;

    /**
     * Constructor for QueueOrder.
     *
     * @param capacity Maximum queue capacity.
     */
    public OrderQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Closes the queue and notifies all waiting threads.
     */
    public synchronized void close() {
        isClosed = true;
        notifyAll();
    }

    /**
     * Checks if the queue is closed.
     *
     * @return true if the queue is closed, false otherwise.
     */
    public synchronized boolean isClosed() {
        return isClosed;
    }

    /**
     * Returns the current size of the queue.
     *
     * @return The number of elements in the queue.
     */
    public synchronized int getSize() {
        return queue.size();
    }

    /**
     * Adds an element to the queue.
     *
     * @param elem The element to add.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public synchronized void insert(T elem) throws InterruptedException {
        while (this.getSize() >= capacity) {
            wait();
        }

        queue.add(elem);
        notifyAll();
    }

    /**
     * Views the first element in the queue without removing it.
     *
     * @return The first element, or null if the queue is empty.
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    public synchronized T see() throws InterruptedException {
        if (this.getSize() == 0) {
            return null;
        }

        return queue.peek();
    }

    /**
     * Extracts and removes the first element from the queue.
     *
     * @return The first element, or null if the queue is closed and empty.
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    public synchronized T get() throws InterruptedException {
        while (this.getSize() == 0 && !this.isClosed()) {
            wait();
        }
        if (this.isClosed() && this.getSize() == 0) {
            return null;
        }

        T elem = queue.poll();
        notifyAll();
        return elem;
    }
}
