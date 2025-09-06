public class FShowBuilder {
    public static void main(String[] args) {
        CSV csv = new CSV.Builder()
        .fileName("test.csv")
        .build();
        System.out.println(csv);
    }
}
