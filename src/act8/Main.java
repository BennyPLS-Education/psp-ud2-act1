package act8;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter = new AtomicInteger(0);
    private static final Scanner scanner = new Scanner(System.in);

    public static class Counter {
        private int count;

        public Counter(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class Adder extends Thread {
        private final Counter counter;
        private final int number;
        private final Semaphore semaphore;

        public Adder(Counter counter, int number, Semaphore semaphore) {
            this.counter = counter;
            this.number = number;
            this.semaphore = semaphore;
        }

        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException ignored) {}

            var buff = counter.getCount();
            buff++;
            counter.setCount(buff);
            System.out.println("Fil " + number + " - Nou valor del comptador: " + counter.getCount());

            semaphore.release();
        }

    }

    public static void main(String[] args) {
        var counter = new Counter(0);
        var threads = new ArrayList<Thread>();
        var semaphore = new Semaphore(1);

        for (int i = 0; i < 10; i++) {
            threads.add(new Adder(counter, i, semaphore));
        }

        for (var thread : threads) {
            thread.start();
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        }

        System.out.println("Programa finalitzat");
    }

}
