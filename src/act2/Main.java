package act2;

public class Main {
    public static void main(String[] args) {
        var storage = new int[]{ 0 };

        var threadOne = new Thread(() -> {
            System.out.println("S => compte: " + storage[0]);
            var reg = storage[0];
            reg += 10;
            System.out.println("S => registre: " + reg);
            storage[0] = reg;
            System.out.println("S => compte: " + storage[0]);
        });

        var threadTwo = new Thread(() -> {
            System.out.println("R => compte: " + storage[0]);
            var reg = storage[0];
            reg -= 10;
            System.out.println("R => registre: " + reg);
            storage[0] = reg;
            System.out.println("R => compte: " + storage[0]);
        });

        threadOne.start();
        threadTwo.start();

        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Value: " + storage[0]);
    }
}
