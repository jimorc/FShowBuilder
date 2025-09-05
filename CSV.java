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
}
