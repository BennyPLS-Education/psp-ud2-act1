package act6;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);
    
    private static class RandomPrint extends Thread {
        private final int quantity;
        private Thread partner = new Thread();
        
        RandomPrint(int quantity) {
            this.quantity = quantity;
        }
        
        public void setPartner(Thread partner) {
            this.partner = partner;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < quantity; i++) {
                
                System.out.println("Fill 1: " + random.nextInt(100));
                synchronized (partner) {
                    partner.notify();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
            
            partner.interrupt();
        }
    }
    
    private static class PartnerThread extends Thread {
        private final Thread partner;
        
        PartnerThread(Thread partner) {
            this.partner = partner;
        }
        
        @Override
        public synchronized void run() {
            while (partner.isAlive() && !partner.isInterrupted()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    return;
                }
                System.out.println("Fill 2: " + random.nextInt(100));
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Introdueix la quantitat de nombres aleatoris:");
        var quantity = getNumber();
        
        var randomPrint = new RandomPrint(quantity);
        var partnerThread = new PartnerThread(randomPrint);
        randomPrint.setPartner(partnerThread);
        
        randomPrint.start();
        partnerThread.start();
        
        try {
            randomPrint.join();
            partnerThread.join();
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
