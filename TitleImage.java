public class TitleImage {
    String imageName;
    String title;
    /**
     * Constructor - creates a TitleImage object from a title string.
     * @param title - the title string
     * @param imageName - the image file name
     */
    public TitleImage(String title, String imageName) {
        this.imageName = imageName;
        this.title = title;    
    }
}