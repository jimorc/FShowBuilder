public class ImageAndPersonLine extends CSVLine {
    /**
     * Constructor - creates an ImageAndPersonLine object from a CSV line. Special
     * processing is required because the image title may contain commas. In that case,
     * the title must be enclosed in double quotes, although no checking is done to
     * ensure that this is the case.
     * @param line - the CSV input line. The fields must be in the following order:
     *  0. image file name
     *  1. image title
     *  2. person's full name
     *  3. person's first name
     *  4. person's last name
     */
    public ImageAndPersonLine(String line) {
        int first = line.indexOf(',');
        int fourth = line.lastIndexOf(',');
        int third = line.lastIndexOf(',', fourth - 1);
        int second = line.lastIndexOf(',', third - 1); 
        String[] fields = new String[5];
        fields[0] = line.substring(0, first);
        fields[1] = line.substring(first + 1, second);
        fields[2] = line.substring(second + 1, third);
        fields[3] = line.substring(third + 1, fourth);
        fields[4] = line.substring(fourth + 1);
        super(fields);
    }

    /**
     * Returns the image file name.
     * @return the image file name.
     */
    public String imageFileName() {
        return field(0);
    }

    /**
     * Returns the image title.
     * @return the image title.
     */
    public String imageTitle() {
        return field(1);
    }

    /**
     * Returns the person's full name.
     * @return the person's full name.
     */
    public String personFullName() {
        return field(2);
    }
    
    /**
     * Returns the person's first name.
     * @return the person's first name.
     */
    public String personFirstName() {
        return field(3);
    }

    /**
     * Returns the person's last name.
     * @return the person's last name.
     */
    public String personLastName() {
        return field(4);
    }

    /**
     * Returns a String representation of the object.
     * @return a String representation of the object.
     * @throws ArrayIndexOutOfBoundsException if the object does not
     * contain at least five fields.
     */
    @Override
    public String toString() {
        return imageFileName() + "," + imageTitle() + "," + personFullName() + "," +
            personFirstName() + "," + personLastName();
    }
}
