import java.nio.file.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.*;

enum sortOrder {
    NONE,
    ALPHABETICAL_BY_FULL_NAME,
    ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME,
    ALPHABETICAL_BY_FULL_NAME_REVERSE,
    ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE
}

/**
 * The CSV class stores multiple CSVLine objects.
 * 
 * It uses the Builder pattern to create a CSV object. The only required
 * parameter is the name of the CSV file to read. If no file name is provided,
 * a file chooser dialog is displayed to select a CSV file.
 * ```java
 * CSV csv = new CSV.Builder()
 *     .fileName("test.csv")  // optional, if not provided a file chooser dialog is displayed
 *     .build();
 * ```
 */
public class CSV {
    String fileName = null;
    CSVLine[] lines = new CSVLine[0];

    /**
     * This constructor is private. Use the Builder class to create a CSV object.
     * @param builder - the Builder object used to create the CSV object.
     */
    private CSV(Builder builder) {
        this.fileName = builder.fileName;
    }

    /**
     * The Builder class is used to create a CSV object.
     */
    public static class Builder {
        private String fileName;

        /**
         * Sets the name of the CSV file to read.
         * @param fileName the path to the CSV file.
         * @return returns the Builder object.
         */
        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        /**
         * Builds the CSV object.
         * @return the CSV object.
         */
        public CSV build() {
            if (fileName == null) {
                fileName = CSV.getCSVFileName();
            }
            CSV csv = new CSV(this);
            csv.loadCSVFile();
            return csv;
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

    /**
     * Appends a CSVLine to the end of the CSV object.
     * @param line - the line to append
     */
    public void append(CSVLine line) {
        insertAt(lines.length, line);
    }

    /**
     * Opens a file chooser dialog to select a CSV file.
     * @return The name of the selected CSV file, or null if no file was selected.
     */
    private static String getCSVFileName() {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setDialogTitle("Select a CSV file");
        j.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        j.addChoosableFileFilter(filter);  
        int r = j.showOpenDialog(null);
        String fileName = null;
        if (r == JFileChooser.APPROVE_OPTION) {
            fileName = j.getSelectedFile().getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(null, "No file selected. Exiting.");
            System.exit(0);
        }
        return fileName;
    }

    /**
     * Loads the CSV file specified by the fileName field.
     * The lines field is populated with CSVLine objects.
     * If there is an error reading the file, an error message is
     * displayed and the program exits.
     * 
     * This file is protected rather than private so that
     * it can called for testing purposes.
     */
    protected void loadCSVFile() {
        Path path = Paths.get(fileName);
        try {
            List<String> allLines = java.nio.file.Files.readAllLines(path);
            lines = new CSVLine[allLines.size()];
            for (int i = 0; i < allLines.size(); i++) {
                lines[i] = new ImageAndPersonLine(allLines.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
            System.exit(0);
        }
    }

    private HashMap<String, ImageAndPersonLine[]> buildFullNameHashMap() {
        HashMap<String, ImageAndPersonLine[]> map = new HashMap<>();
        boolean firstLine = true;
        for (CSVLine line : lines) {
            if (firstLine) {
                firstLine = false;
                continue; // skip header line
            }
            ImageAndPersonLine ipLine = (ImageAndPersonLine) line;
            String fullName = ipLine.personFullName();
            if (!map.containsKey(fullName)) {
                map.put(fullName, new ImageAndPersonLine[] { ipLine });
            } else {
                ImageAndPersonLine[] existingLines = map.get(fullName);
                ImageAndPersonLine[] newLines = new ImageAndPersonLine[existingLines.length + 1];
                System.arraycopy(existingLines, 0, newLines, 0, existingLines.length);
                newLines[existingLines.length] = ipLine;
                map.put(fullName, newLines);
            }
        }
        return map;
    }

    /**
     * Sorts the lines in the CSV object according to the specified order.
     * @param order - the sort order. See the sortOrder enum for possible values.
     * This file is protected rather than private so that
     * it can called for testing purposes.
     */
    protected void sort(sortOrder order) {
        HashMap<String, ImageAndPersonLine[]> ipMap = buildFullNameHashMap();
        switch (order) {
            case NONE:
                break;
            case ALPHABETICAL_BY_FULL_NAME:
                sortLinesAlphabeticallyByFullName(ipMap);
                break;
            case ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME:
                // Not implemented
                break;
                case ALPHABETICAL_BY_FULL_NAME_REVERSE:
                sortLinesAlphabeticallyByFullNameReverse(ipMap);
                break;
                case ALPHABETICAL_BY_LAST_NAME_THEN_FIRST_NAME_REVERSE:
                // Not implemented
                break;
        }       
    }

    private void sortLinesAlphabeticallyByFullName(HashMap<String, ImageAndPersonLine[]> ipMap  ) {
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine)this.lines[0]);
        Set<String> fullNamekeys = ipMap.keySet();
        String[] fullNames = fullNamekeys.toArray(new String[ipMap.size()]);
        Arrays.sort(fullNames);
        for (String fName: fullNames) {
            ImageAndPersonLine[] linesForName = ipMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }

    private void sortLinesAlphabeticallyByFullNameReverse(HashMap<String, ImageAndPersonLine[]> ipMap  ) {
        List<ImageAndPersonLine> entries = new ArrayList<ImageAndPersonLine>();
        entries.add((ImageAndPersonLine)this.lines[0]);
        Set<String> fullNamekeys = ipMap.keySet();
        String[] fullNames = fullNamekeys.toArray(new String[ipMap.size()]);
        Arrays.sort(fullNames, Collections.reverseOrder());
        for (String fName: fullNames) {
            ImageAndPersonLine[] linesForName = ipMap.get(fName);
            for (ImageAndPersonLine line: linesForName) {
                entries.add(line);
            }
        }
        this.lines = entries.toArray(new CSVLine[entries.size()]);
    }
}
