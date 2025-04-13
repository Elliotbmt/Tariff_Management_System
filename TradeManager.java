
import java.io.*;
import java.util.*;
public class TradeManager {
    public static void main(String[] args) {

        System.out.println("Welcome to the Trade Manager");

        //a)
        TariffList list1 = new TariffList();
        TariffList list2 = new TariffList();

        //b)
        Scanner input = null;

        try {
            input = new Scanner(new FileInputStream("Tariffs.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");

                String destination = parts[0];
                String origin = parts[1];
                String category = parts[2];
                double minTariff = Double.parseDouble(parts[3]);

                //make sure there's no duplicate first
                if (!list1.contains(origin, destination, category)) {
                    Tariff t = new Tariff(destination, origin, category, minTariff);
                    list1.addToStart(t);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error File not found");
            return;
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }

        ArrayList<TradeRequest> request = new ArrayList<>();

        //read and store requests
        try {
            Scanner input2 = new Scanner(new FileInputStream("TariffRequests"));
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                String[] parts = line.split(" ");
                String destinationCountry=parts[0].trim();
                String originCountry=parts[1].trim();
                String productCategory=parts[2].trim();
                int tradeValue=Integer.parseInt(parts[3].trim());
                int proposedTariff = Integer.parseInt(parts[4].trim());

                TradeRequest tr=new TradeRequest(destinationCountry, originCountry, productCategory, tradeValue, proposedTariff);
                request.add(tr);
            }
            input2.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    //process requests using methods from tarifflist










    public static class TradeRequest{
        private String destinationCountry;
        private String originCountry;
        private String productCategory;
        private int tradeValue;
        private int proposedTariff;

        public TradeRequest(String destinationCountry, String originCountry,String productCategory,int tradeValue,int proposedTariff){
            this.destinationCountry = destinationCountry;
            this.originCountry = originCountry;
            this.productCategory = productCategory;
            this.tradeValue = tradeValue;
            this.proposedTariff = proposedTariff;
        }
    }
}