package act3;

public class Main {
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
        test2();
    }

    private static void test1() {
        var thread1 = new Fibonacci("Fil 1", 89, Thread.MIN_PRIORITY);
        var thread2 = new Fibonacci("Fil 2", 35, Thread.MAX_PRIORITY);

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ignored) {}
    }

    private static void test2() {
        var thread1 = new Fibonacci("Fil 1", 43, Thread.MIN_PRIORITY);
        var thread2 = new Fibonacci("Fil 2", 35, Thread.MIN_PRIORITY);
        var thread3 = new Fibonacci("Fil 3", 44, Thread.MIN_PRIORITY);
        var thread4 = new Fibonacci("Fil 4", 41, Thread.MIN_PRIORITY);
        var thread5 = new Fibonacci("Fil 5", 27, Thread.MIN_PRIORITY);
        var thread6 = new Fibonacci("Fil 6", 61, Thread.MAX_PRIORITY);
        var thread7 = new Fibonacci("Fil 7", 35, Thread.MAX_PRIORITY);
        var thread8 = new Fibonacci("Fil 8", 17, Thread.MAX_PRIORITY);
        var thread9 = new Fibonacci("Fil 9", 82, Thread.MAX_PRIORITY);
        var thread0 = new Fibonacci("Fil 0", 19, Thread.MAX_PRIORITY);

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
            thread7.join();
            thread8.join();
            thread9.join();
            thread0.join();
        } catch (InterruptedException ignored) {}
    }
}
