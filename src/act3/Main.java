package act3;

import java.util.Random;

public class Main {
    private static final Random random = new Random();

    private static class Fibonacci extends Thread {
        private final int input;

        private Fibonacci(String name, int input, int priority) {
            super(name);
            this.input = input;

            setPriority(priority);
            start();
        }

        @Override
        public void run() {
            int result = fib(input);

            System.out.println(getName() + " - " + getPriority() + ": " + result);
        }

        private static int fib(int number) {
            if (number <= 1) {
                return number;
            }

            return fib(number - 1) + fib(number - 2);
        }
    }

    public static void main(String[] args) {
        System.out.println("-------- Test 1 --------");
        test1();
        System.out.println("-------- Test 2 --------");
        test2();
    }

    private static void test1() {
        var threads = new Thread[]{
                new Fibonacci("Fil 1", random.nextInt(10), Thread.MIN_PRIORITY),
                new Fibonacci("Fil 2", random.nextInt(10), Thread.MAX_PRIORITY)
        };

        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ignored) {}
    }

    private static void test2() {
        final int quantity = 5;

        final Thread[] minPriority = getMinPriority(quantity, 0);
        final Thread[] maxPriority = getMaxPriority(quantity, quantity);

        try {
            for (int i = 0; i < quantity; i++) {
                minPriority[i].join();
                maxPriority[i].join();
            }
        } catch (InterruptedException ignored) {}
    }

    private static Thread[] getMinPriority(int quantity, int startingPoint) {
        var threads = new Thread[quantity];

        for (int i = startingPoint; i < quantity; i++) {
            threads[i] = new Fibonacci("Fil " + i, random.nextInt(10), Thread.MIN_PRIORITY);
        }

        return threads;
    }

    private static Thread[] getMaxPriority(int quantity, int startingPoint) {
        var threads = new Thread[quantity];

        for (int i = startingPoint; i < quantity; i++) {
            threads[i] = new Fibonacci("Fil " + i, random.nextInt(10), Thread.MAX_PRIORITY);
        }

        return threads;
    }
}
