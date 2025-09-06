import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TitleImageTests {
    @Test
    void testConstructorValid() {
        TitleImage tl = new TitleImage("My Title", "My_Title.jpg");
        assertNotNull(tl);
        TitleImage tl2 = new TitleImage("Another Title", "another_title.JPG");
        assertNotNull(tl2);
    }
}
