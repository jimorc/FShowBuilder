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

    @Test
    void testInsertAt() {
        String[] lines = {
            "image1.jpg,Image One,John Doe,John,Doe",
            "image3.jpg,Image Three,Bob Brown,Bob,Brown"
        };
        CSV csv = new CSV(lines);
        CSVLine newLine = new ImageAndPersonLine("image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith");
        csv.insertAt(1, newLine);
        assertEquals(3, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image3.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
    }

    @Test
    void testInsertAtBeginning() {
        String[] lines = {
            "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith",
            "image3.jpg,Image Three,Bob Brown,Bob,Brown"
        };
        CSV csv = new CSV(lines);
        CSVLine newLine = new ImageAndPersonLine("image1.jpg,Image One,John Doe,John,Doe");
        csv.insertAt(0, newLine);
        assertEquals(3, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image3.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
    }

    @Test
    void testInsertAtEnd() {
        String[] lines = {
            "image1.jpg,Image One,John Doe,John,Doe",
            "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith"
        };
        CSV csv = new CSV(lines);
        CSVLine newLine = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        csv.insertAt(2, newLine);
        assertEquals(3, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image3.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
    }

    @Test
    void testInsertAtInvalidIndex() {
        String[] lines = {
            "image1.jpg,Image One,John Doe,John,Doe",
            "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith"
        };
        CSV csv = new CSV(lines);
        CSVLine newLine = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            csv.insertAt(-1, newLine));
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            csv.insertAt(3, newLine));
    }

    @Test
    void testAppend() {
        String[] lines = {
            "image1.jpg,Image One,John Doe,John,Doe",
            "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith"
        };
        CSV csv = new CSV(lines);
        CSVLine newLine = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        csv.append(newLine);
        assertEquals(3, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image3.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
    }

    @Test
    void testAppendToEmptyCSV() {
        String[] lines = {};
        CSV csv = new CSV(lines);
        CSVLine newLine = new ImageAndPersonLine("image1.jpg,Image One,John Doe,John,Doe");
        csv.append(newLine);
        assertEquals(1, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
    }

    @Test
    void testAppendMultiple() {
        String[] lines = {};
        CSV csv = new CSV(lines);
        CSVLine line1 = new ImageAndPersonLine("image1.jpg,Image One,John Doe,John,Doe");
        CSVLine line2 = new ImageAndPersonLine("image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith");
        CSVLine line3 = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        csv.append(line1);
        csv.append(line2);
        csv.append(line3);
        assertEquals(3, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image3.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
    }
}
