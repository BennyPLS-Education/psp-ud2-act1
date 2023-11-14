package act4;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int limit = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Thread fiboThread = new Thread(() -> {
            try {
                for (int i = 1; i < limit; i++) {
                    System.out.println(fib(i));
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println("Fil interromput.");
            }
        });

        fiboThread.start();

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                fiboThread.interrupt();
                break;
            }
        }

        try {
            fiboThread.join();
        } catch (InterruptedException ignored) {}

        System.out.println("Programa finalitzat.");
    }

    private static int fib(int number) throws InterruptedException {
        if (number <= 1) {
            return number;
        }

        if (Thread.interrupted()) throw new InterruptedException();

        return fib(number - 1) + fib(number - 2);
    }
}
