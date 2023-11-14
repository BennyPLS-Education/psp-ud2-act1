package act7;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter = new AtomicInteger(0);
    private static final Scanner scanner = new Scanner(System.in);

    public static class ReverseCounter extends Thread {
        public void run() {
            while (counter.getAndDecrement() > 0) {
                System.out.println(counter.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    public static class Subscriber extends Thread {
        private final int half;
        private final int quarter;
        private final int threeQuarters;

        public Subscriber(int total) {
            this.half = total / 2;
            this.quarter = (int) Math.ceil(total / 4.0);
            this.threeQuarters = total - quarter;
        }

        public void run() {
            while (counter.get() != threeQuarters) ;
            System.out.println("Queden 3/4");
            while (counter.get() != half) ;
            System.out.println("Queda la meitat");
            while (counter.get() != quarter) ;
            System.out.println("Queden 1/4");
            while (counter.get() != 0) ;
            System.out.println("TIMEOUT");
        }
    }

    public static void main(String[] args) {
        counter.set(getNumber());

        var reverseCounter = new ReverseCounter();
        var subscriber = new Subscriber(counter.get());

        reverseCounter.start();
        subscriber.start();

        try {
            reverseCounter.join();
            subscriber.join();
        } catch (InterruptedException ignored) {}

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
}
