package ru.nsu.abramkin;

/**
 * Класс с реализацией функций heapsort
 */
class Heapsort {
    public static void heapsort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    static void heapify(int[] arr, int n, int i)  {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }

        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }
}

/**
 * Главный класс программы, содержащий метод main.
 */
public class Main {
    static void printArray(int[] arr) {
        int n = arr.length;
        System.out.print("[");
        for (int i = 0; i < n; ++i) {
            if (i < n - 1) {
                System.out.print(arr[i] + ", ");
            } else {
                System.out.print(arr[i]);
            }
        }
        System.out.println("]");
    }

    /**
     * Точка входа в программу.
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        int[] arr = {12, 11, 19, 5, 6, 7};

        Heapsort.heapsort(arr);

        System.out.println("Sorted array is");
        printArray(arr);
    }
}
