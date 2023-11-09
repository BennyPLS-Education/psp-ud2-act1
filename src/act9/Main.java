package act9;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter = new AtomicInteger(0);
    private static final Scanner scanner = new Scanner(System.in);

    public static class LetterCounter extends Thread {
        private final String letter;
        private final Semaphore semaphore;
        private final File file;
        private int count;

        public LetterCounter(String letter, Semaphore semaphore, File file) {
            this.letter = letter;
            this.semaphore = semaphore;
            this.file = file;
        }

        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException ignored) {}

            try (var reader = new BufferedReader(new FileReader(file))) {
                var line = reader.readLine();

                while (line != null) {
                    if (line.contains(String.valueOf(letter))) {
                        for (var c : line.toCharArray()) {
                            if (String.valueOf(c).equals(letter)) {
                                count++;
                            }
                        }
                    }
                    line = reader.readLine();
                }
            } catch (Exception ignored) {}

            semaphore.release();
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        System.out.println("Introdueix un text:");
        var text = scanner.nextLine();
        var semaphore = new Semaphore(1);
        var file = new File("src/act9/text.txt");

        try (var writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (Exception ignored) {}

        var a = new LetterCounter("a", semaphore, file);
        var e = new LetterCounter("e", semaphore, file);
        var i = new LetterCounter("i", semaphore, file);
        var o = new LetterCounter("o", semaphore, file);
        var u = new LetterCounter("u", semaphore, file);

        a.start();
        e.start();
        i.start();
        o.start();
        u.start();

        System.out.println("Processant...");

        try {
            a.join();
            e.join();
            i.join();
            o.join();
            u.join();
        } catch (InterruptedException ignored) {}

        System.out.println("Contingut de l’arxiu resultant:");
        System.out.println("vocal a: " + a.getCount());
        System.out.println("vocal e: " + e.getCount());
        System.out.println("vocal i: " + i.getCount());
        System.out.println("vocal o: " + o.getCount());
        System.out.println("vocal u: " + u.getCount());
    }

}
