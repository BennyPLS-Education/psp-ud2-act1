package act1;

import java.util.Random;

public class Main {
    private static class RandomThread extends Thread {
        private final Random random = new Random();

        public RandomThread(int number) {
            super("Fil " + number);
            start();
        }

        public void run() {
            System.out.println(currentThread().getName() + ": iniciat");

            System.out.println(currentThread().getName() + ": valor " + random.nextInt(101));

            System.out.println(currentThread().getName() + ": finalitzat");
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new RandomThread(i);
        }
    }
}

