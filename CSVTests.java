import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// Most of these tests read from a file named "testing/data/test.csv"
// with the following content:
// Filename,Title,Full Name,First Name,Last Name
// image1.jpg,Image One,John Doe,John,Doe
// image2.jpg,"Image, Two",Jane Smith,Jane,Smith
//
// Other tests read from "testing/data/empty.csv" which is an empty file.
public class CSVTests {
    @Test
    void testToString() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
       String expected = "Filename,Title,Full Name,First Name,Last Name\n" +
                      "image1.jpg,Image One,John Doe,John,Doe\n" +
                          "image2.jpg,\"Image, Two\",Jane Smith,Jane,Smith\n";
        assertEquals(expected, csv.toString());
    }

    @Test
    void testInsertAt() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,\"Image, Two\",Bob Brown,Bob,Brown");
        csv.insertAt(1, newLine);
        assertEquals(4, csv.lines.length);
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[3]).imageFileName());
    }

    @Test
    void testInsertAtBeginning() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Four,Bob Brown,Bob,Brown");
        csv.insertAt(0, newLine);
        assertEquals(4, csv.lines.length);
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[3]).imageFileName());
    }

    @Test
    void testInsertAtEnd() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
       CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Three,Bob Brown,Bob,Brown");
        csv.insertAt(3, newLine);
        assertEquals(4, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.lines[3]).imageFileName());
    }

    @Test
    void testInsertAtInvalidIndex() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image3.jpg,Image Three,Bob Brown,Bob,Brown");
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            csv.insertAt(-1, newLine));
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            csv.insertAt(4, newLine));
    }

    @Test
    void testAppend() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image4.jpg,Image Four,Bob Brown,Bob,Brown");
        csv.append(newLine);
        assertEquals(4, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());
        assertEquals("image4.jpg", ((ImageAndPersonLine)csv.lines[3]).imageFileName());
    }

    @Test
    void testAppendToEmptyCSV() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/empty.csv")
            .build();
        CSVLine newLine = new ImageAndPersonLine("image1.jpg,Image One,John Doe,John,Doe");
        csv.append(newLine);
        assertEquals(1, csv.lines.length);
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
    }

    @Test
    void testAppendMultiple() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/empty.csv")
            .build();
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

    @Test
    void testLoadCSVFile() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/test.csv")
            .build();
        assertEquals(3, csv.lines.length);
        assertEquals("Filename", ((ImageAndPersonLine)csv.lines[0]).imageFileName());
        assertEquals("image1.jpg", ((ImageAndPersonLine)csv.lines[1]).imageFileName());
        assertEquals("image2.jpg", ((ImageAndPersonLine)csv.lines[2]).imageFileName());

        assertEquals(5, csv.lines[0].length());
        assertEquals("Title", ((ImageAndPersonLine)csv.lines[0]).imageTitle());
        assertEquals("Image One", ((ImageAndPersonLine)csv.lines[1]).imageTitle());
        assertEquals("\"Image, Two\"", ((ImageAndPersonLine)csv.lines[2]).imageTitle());
    }

    @Test
    void testSortAlphaByFullName() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.ALPHABETICAL_BY_FULL_NAME);
        assertEquals(7, csv.lines.length);
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.lines[1]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[2]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[3]).personFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.lines[4]).personFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.lines[5]).personFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.lines[6]).personFullName());
    }

    @Test
    void testSortAlphaByFullNameReverseOrder() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.ALPHABETICAL_BY_FULL_NAME_REVERSE);
        assertEquals(7, csv.lines.length);
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.lines[6]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[4]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[5]).personFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.lines[3]).personFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.lines[2]).personFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.lines[1]).personFullName());
    }

    @Test
    void testSortAlphaByLastNameFirstName() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME);
        assertEquals(7, csv.lines.length);
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.lines[5]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[3]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[2]).personFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.lines[4]).personFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.lines[6]).personFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.lines[1]).personFullName());
    }

    @Test
    void testSortAlphaByLastNameFirstNameREverseOrder() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE);
        assertEquals(7, csv.lines.length);
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.lines[2]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[4]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[5]).personFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.lines[3]).personFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.lines[1]).personFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.lines[6]).personFullName());
    }

    @Test
    void testSortNone() {
        CSV csv = new CSV.Builder()
            .fileName("testing/data/sort.csv")
            .build();
        csv.sort(sortOrder.NONE);
        assertEquals(7, csv.lines.length);
        assertEquals("Barney Rubble", ((ImageAndPersonLine)csv.lines[5]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[3]).personFullName());
        assertEquals("Fred Flintstone", ((ImageAndPersonLine)csv.lines[4]).personFullName());
        assertEquals("Wilma Flintstone", ((ImageAndPersonLine)csv.lines[6]).personFullName());
        assertEquals("Jane Smith", ((ImageAndPersonLine)csv.lines[2]).personFullName());
        assertEquals("John Doe", ((ImageAndPersonLine)csv.lines[1]).personFullName());
    }

}
