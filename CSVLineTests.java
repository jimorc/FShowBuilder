import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CSVLineTests {
    @Test
    void constructor() {
        CSVLine csvL = new CSVLine("a,b,c");
        assertEquals(3, csvL.length());
        assertEquals("a", csvL.field(0));
        assertEquals("b", csvL.field(1));
        assertEquals("c", csvL.field(2));
    }

    @Test
    void fieldOutOfBounds() {
        CSVLine csvL = new CSVLine("a,b,c");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> 
            csvL.field(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            csvL.field(3));
    }
}
