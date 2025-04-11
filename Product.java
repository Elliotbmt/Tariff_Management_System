//---------------------------------------------------------------------------------------
// Assignment 3
// Question: class Product
// Written by: Nithushan Kanapathippillai (40313552) and Elliot Boismartel (40315629)
//---------------------------------------------------------------------------------------

public class Product {
    private String productName, country, category;
    private double price;

    public Product(String productName, String country, String category, double price){
        this.productName=productName;
        this.country=country;
        this.category=category;
        this.price=price;
    }

    //getters
    public String getProductName() {
        return productName;
    }

    public String getCountry() {
        return country;
    }

    public String getCategory() {
        return category;
    }

    public double getprice() {
        return price;
    }



    //setters, we're reading from a file we dont need to set anything else
    public void setprice(double price) {
        this.price = price;
    }

    //method for tariff adjustment
    public void applyTariff() {
        switch (country) {
            case "China":
                price *= 1.25;
                break;

            case "USA":
                if (category.equals("Electronics")) { price *= 1.10; }    
                break;

            case "Japan":
                if (category.equals("Automobile")) { price *= 1.15; }
                break;

            case "India":
                if (category.equals("Agriculture")) { price *= 1.05; }
                break;

            case "South Korea":
                if (category.equals("Electronics")) { price *= 1.08; }
                break;

            case "Saudi Arabia":
                if (category.equals("Energy")) { price *= 1.12; }
                break;

            case "Germany":
                if (category.equals("Manufacturing")) { price *= 1.06; }
                break;

            case "Bangladesh":
                if (category.equals("Textile")) { price *= 1.04; }
                break;

            case "Brazil":
                if (category.equals("Agriculture")) { price *= 1.09; }
                break;
        }
    }



    //toString
    @Override
    public String toString() {
        return productName + "," + country + "," + category + "," + String.format("%.2f", price);
    }




}
