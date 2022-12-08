package Task.java;

public class program {

    public static void main(String[] args) {

        Task.wait(() -> latePrinter(1000));
        //  Task.run(() -> System.out.println("hello"), 1000);

        int result = Task.wait(() -> sum(12, 12));
        System.out.println("result : " + result);

        Task.wait(() -> loopSum());

        Task.delay(5000);

        System.out.println("...");

    }

    public static void loopSum() {
        for (int i = 0; i < 10; i++) {
            System.out.println(sum(i, 1));
        }
        System.out.println("finished!");
    }

    public static int sum(int v1, int v2) {
        Task.delay(1000);
        return v1 + v2;
    }

    public static void latePrinter(int delay) {
        Task.delay(delay);
        System.out.println("Late printed after " + delay + " ms!");
    }

}
