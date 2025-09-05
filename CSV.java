/**
 * The CSV class stores multiple CSVLine objects.
 */
public class CSV {
    CSVLine[] lines;

    /**
     * Constructor - creates a CSV object from an array of CSV lines.
     * @param ipLines  - the CSV input lines. Each line must be in the format required
     * by the ImageAndPersonLine class.
     */
    public CSV(String[] ipLines) {
        lines = new CSVLine[ipLines.length];
        for (int i = 0; i < ipLines.length; i++) {
            lines[i] = new ImageAndPersonLine(ipLines[i]);
        }
    }

    /**
     * Returns a string representation of the CSV object.
     * @return a string representation of the CSV object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CSVLine line : lines) {
            for (int i = 0; i < line.length(); i++) {
                sb.append(line.field(i));
                if (i < line.length() - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Inserts a CSVLine at the specified index.   
     * @param index - the index at which to insert the line
     * @param line  - the line to insert
     * @throws ArrayIndexOutOfBoundsException if the index is negative, or
     * greater than the number of lines.
     */
    public void insertAt(int index, CSVLine line) {
        if (index < 0 || index > lines.length) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + index);
        }
        CSVLine[] newLines = new CSVLine[lines.length + 1];
        for (int i = 0; i < index; i++) {
            newLines[i] = lines[i];
        }
        newLines[index] = line;
        for (int i = index; i < lines.length; i++) {
            newLines[i + 1] = lines[i];
        }
        lines = newLines;
    }
}
