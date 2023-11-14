package act10;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static class TicTacToc extends Thread {
        private final Semaphore internalSemaphore = new Semaphore(1);
        private final Semaphore internalgroupSemaphore = new Semaphore(1);
        private final Semaphore semaphore;

        private TicTacToc(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException ignored) {}

            var tictactoc = new Thread[]{
                new Print("TIC", internalSemaphore),
                new Print("TAC", internalSemaphore, internalgroupSemaphore),
                new Print("TOC", internalSemaphore, internalgroupSemaphore)
            };

            startAndJoin(tictactoc);


            semaphore.release();
        }
    }

    private static class Print extends Thread {
        private final Semaphore groupSemaphore;
        private final Semaphore semaphore;
        private final String string;

        Print(String string, Semaphore semaphore, Semaphore groupSemaphore) {
            this.groupSemaphore = groupSemaphore;
            this.semaphore = semaphore;
            this.string = string;
        }

        Print(String string, Semaphore semaphore) {
            this.groupSemaphore = new Semaphore(1);
            this.semaphore = semaphore;
            this.string = string;
        }

        @Override
        public void run() {
            try {
                groupSemaphore.acquire();
                semaphore.acquire();
            } catch (InterruptedException ignored) {}

            System.out.println(string);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {}

            semaphore.release();
            groupSemaphore.release();
        }
    }

    public static void main(String[] args) {
        var semaphore = new Semaphore(1);
        int quantity = getNumber();
        var threads = new Thread[quantity];

        for (int i = 0; i < quantity; i++) {
            threads[i] = new TicTacToc(semaphore);
        }

        startAndJoin(threads);

        System.out.println("Programa finalitzat");
    }

    private static int getNumber() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ignored) {
            System.out.println("El valor introduït no és un número.");
            return getNumber();
        }
    }

    private static void startAndJoin(Thread[] threads) {
        for (var thread : threads) thread.start();


        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ignored) {}
    }
}
