import org.junit.jupiter.api.Test;

public class SeasonsTest {
    @Test
    public void test1() {
        try {
            int i = 899 / 0;
        } catch (Exception e) {
            System.out.println("1. " + e);
            System.out.println("2. " + e.toString());
            System.out.println("3. " + e.getMessage());
        }
    }
}
