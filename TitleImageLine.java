/**
 * The TitleImageLine class stores the name of a title image.
 */
public class TitleImageLine extends CSVLine {    
    /**
     * Constructor - creates a TitleImageLine object from a title.
     * @param title - the title
     */
    public TitleImageLine(String imageName) {
        if (!imageName.toLowerCase().endsWith(".jpg")) {
            throw new IllegalArgumentException("imageName must end with .jpg");
        }
        String[] imageField = {imageName};
        super(imageField);
    }
}
