/**
 * The CSVLine class breaks up a CSV line into its constituent parts.
 */
public class CSVLine {
    private String[] fields;
    /**
     * Constructor - creates a CSVLine object from a CSV line.
     * @param line - the CSV input line.
     */
    public CSVLine(String line) {
        fields = line.split(",");
    }

    /**
     * Returns the number of fields in the object.
     * @return the number of fields in the CSV line that was used to create the
     * CSVLine object.
     */
    public int length() {
        return fields.length;
    }

    /**
     * Returns the field specified by the argument
     * @param index - the index of the field to return
     * @return the field specified by the index argument
     * @throws ArrayIndexOutOfBoundsException if the index is negative, or
     * greater than the number of fields.
     */
    public String field(int index) {
        return fields[index];
    }
}
