package act2;

public class Main {
    public static void main(String[] args) {
        var storage = new int[]{1000};
        var threads = new Thread[]{
            new Thread(() -> operation(storage, 10)),
            new Thread(() -> operation(storage, -10))
        };
        
        for (var thread : threads) {
            thread.start();
        }
        
        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("Value: " + storage[0]);
    }
    
    private static void operation(int[] storage, int value) {
        String name;
        
        if (value < 0) name = "R";
        else name = "S";
        
        System.out.println(name + " => compte: " + storage[0]);
        var reg = storage[0];
        reg += value;
        System.out.println(name + " => registre: " + reg);
        storage[0] = reg;
        System.out.println(name + " => compte: " + storage[0]);
    }
}
