import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ImageAndPersonLineTests {
    @Test
    void testNoCommaConstructor() {
        ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,image title,John Doe,John,Doe");
        assertEquals(5, ipl.length());
        assertEquals("image.jpg", ipl.imageFileName());
        assertEquals("image title", ipl.imageTitle());
        assertEquals("John Doe", ipl.personFullName());
        assertEquals("John", ipl.personFirstName());
        assertEquals("Doe", ipl.personLastName());
    }

    @Test
    void testCommaInTitleConstructor() {
        ImageAndPersonLine ipl = new ImageAndPersonLine("image.jpg,\"image, title\",John Doe,John,Doe");
        assertEquals(5, ipl.length());
        assertEquals("image.jpg", ipl.imageFileName());
        assertEquals("\"image, title\"", ipl.imageTitle());
        assertEquals("John Doe", ipl.personFullName());
        assertEquals("John", ipl.personFirstName());
        assertEquals("Doe", ipl.personLastName());
    }
}
