public class TimeProduct {
    private String productName;
    private int minutes;
    private int price;

    public TimeProduct(String productName, int minutes, int price) {
        this.productName = productName;
        this.minutes = minutes;
        this.price = price;
    }

    public String getName() {
        return productName;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getPrice() {
        return price;
    }
}