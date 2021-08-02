import org.junit.jupiter.api.Test;

public class AnotherTest {
    @Test
    public void test1() {
        System.out.println("Properties: ");
        System.out.println(System.getProperties());
        System.out.println("\nEnv: ");
        System.out.println(System.getenv());
        System.out.println("\nConsole: ");
        System.out.println(System.console());
        System.out.println("\nIn: " + System.in);
        System.out.println("\nOut: " + System.out);
        System.out.println("\nErr: " + System.err);
    }
}
