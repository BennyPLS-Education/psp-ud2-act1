import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int option;
        
        System.out.println("-------------------------");
        System.out.println("Selecciona una activitat:");
        System.out.println("-------------------------");
        System.out.println("0. Sortir");
        System.out.println("1. Exercici 1");
        System.out.println("2. Exercici 2");
        System.out.println("3. Exercici 3");
        System.out.println("4. Exercici 4");
        System.out.println("5. Exercici 5");
        System.out.println("6. Exercici 6");
        System.out.println("7. Exercici 7");
        System.out.println("8. Exercici 8");
        System.out.println("9. Exercici 9");
        System.out.println("10. Exercici 10");
        System.out.println("-------------------------");
        
        option = getNumber();
        
        switch (option) {
            case 0 -> System.out.println("Adeu!");
            case 1 -> act1.Main.main(args);
            case 2 -> act2.Main.main(args);
            case 3 -> act3.Main.main(args);
            case 4 -> act4.Main.main(args);
            case 5 -> act5.Main.main(args);
            case 6 -> act6.Main.main(args);
            case 7 -> act7.Main.main(args);
            case 8 -> act8.Main.main(args);
            case 9 -> act9.Main.main(args);
            case 10 -> act10.Main.main(args);
            
            default -> System.out.println("Opció no vàlida");
        }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
        
        if (option != 0) main(args);
    }
    
    private static int getNumber() {
        var input = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.print("Opció: ");
                return input.nextInt();
            } catch (InputMismatchException e) {
                input.next();
                System.out.println("Introdueix un número vàlid");
            }
        }
    }
}
