import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CSVTests {
    @Test
    void testConstructor() {
        String[] lines = {
            "image1.jpg,Image One,John Doe,John,Doe",
            "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith"
        };
        CSV csv = new CSV(lines);
        assertNotNull(csv);
        assertEquals(2, csv.lines.length);
        assertEquals(5, csv.lines[0].length());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
        assertEquals("Image One", ((ImageAndPersonLine)csv.lines[0]).imageTitle());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.lines[0]).personFullName());
        assertEquals("John", ((ImageAndPersonLine)csv.lines[0]).personFirstName());
        assertEquals("Doe", ((ImageAndPersonLine)csv.lines[0]).personLastName());
        assertEquals(5, csv.lines[1].length());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("\"Image, Two\"", ((ImageAndPersonLine)csv.lines[1]).imageTitle());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.lines[1]).personFullName());
        assertEquals("Jane", ((ImageAndPersonLine)csv.lines[1]).personFirstName());
        assertEquals("Smith", ((ImageAndPersonLine)csv.lines[1]).personLastName());
    }

    @Test
    void testToString() {
        String[] lines = {
            "image1.jpg,Image One,John Doe,John,Doe",
            "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith"
        };
        CSV csv = new CSV(lines);
        String expected = "image1.jpg,Image One,John Doe,John,Doe\n" +
                          "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith\n";
        assertEquals(expected, csv.toString());
    }
}
