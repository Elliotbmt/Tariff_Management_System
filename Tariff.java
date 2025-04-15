



public class Tariff {

    //attributes  
    //It is assumed that String entries are recorded as a single word (_ is used to combine multiple words)
    private String destinationCountry;
    private String originCountry;
    private String productCategory;
    private double minimumTariff;
    
    //a) parameterized constructor
    public Tariff(String destinationCountry, String originCountry, String productCategory, double minimumTariff) {
        this.destinationCountry = destinationCountry;
        this.originCountry = originCountry;
        this.productCategory = productCategory;
        this.minimumTariff = minimumTariff;
    }

    //b) copy constructor
    public Tariff(Tariff other) {
        this.destinationCountry = other.destinationCountry;
        this.originCountry = other.originCountry;
        this.productCategory = other.productCategory;
        this.minimumTariff = other.minimumTariff;
    }

    //getters & setters
    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getMinimumTariff() {
        return minimumTariff;
    }

    public void setMinimumTariff(double minimumTariff) {
        this.minimumTariff = minimumTariff;
    }


    //c) clone method
     public Tariff clone() {
        return new Tariff(this);
    }


    //d)
    //toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tariff{");
        sb.append("destinationCountry=").append(destinationCountry);
        sb.append(", originCountry=").append(originCountry);
        sb.append(", productCategory=").append(productCategory);
        sb.append(", minimumTariff=").append(minimumTariff);
        sb.append('}');
        return sb.toString();
    }

    //equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tariff other = (Tariff) obj;

        final double EPSILON = 0.0001;

        return destinationCountry.equalsIgnoreCase(other.destinationCountry)
        && originCountry.equalsIgnoreCase(other.originCountry)
        && productCategory.equalsIgnoreCase(other.productCategory)
        && Math.abs(minimumTariff - other.minimumTariff) < EPSILON;

        //we think maybe the equals method isn't working because of tiny differences like 1.0 != 1.0000001
    }
}