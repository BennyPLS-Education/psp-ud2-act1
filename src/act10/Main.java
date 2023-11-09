package act10;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

    private static class printingThread extends Thread {
        private final String string;
        private final Semaphore semaphore;

        printingThread(String string, Semaphore semaphore) {
            this.semaphore = semaphore;
            this.string = string;
        }

        printingThread(String string) {
            this.semaphore = new Semaphore(1);
            this.string = string;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException ignored) {}

            System.out.println(string);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {}

            semaphore.release();
        }
    }

    public static void main(String[] args) {
        int quantity = getNumber();

        version1(quantity);
//        version2(quantity);
    }

    private static void version1(int quantity) {
        for (int i = 0; i < quantity; i++) {
            var tic = new printingThread("TIC");
            var tac = new printingThread("TAC");

            try {
                tic.start();
                tic.join();
                tac.start();
                tac.join();
            } catch (InterruptedException ignored) {}
        }
    }

    private static int getNumber() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ignored) {
            System.out.println("El valor introduït no és un número.");
            return getNumber();
        }
    }
}
