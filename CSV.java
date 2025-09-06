import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * The CSV class stores multiple CSVLine objects.
 */
public class CSV {
    String fileName;
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

    public CSV() {
        getCSVFileName();
        if (fileName == null) {
            JOptionPane.showMessageDialog(null, "No file selected. Exiting.");
            System.exit(0);
        }
        // For now, just create an empty CSV object.
        this.lines = new CSVLine[0];
        System.out.printf("CSVFile Selected: %s%n", fileName);
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

    /**
     * Appends a CSVLine to the end of the CSV object.
     * @param line - the line to append
     */
    public void append(CSVLine line) {
        insertAt(lines.length, line);
    }

    /**
     * Opens a file chooser dialog to select a CSV file.
     * @return The name of the seleceted CSV file, or null if no file was selected.
     */
    private void getCSVFileName() {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setDialogTitle("Select a CSV file");
        j.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        j.addChoosableFileFilter(filter);  
        int r = j.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            fileName = j.getSelectedFile().getAbsolutePath();
        } else {
            fileName = null;
        }
    }
}
