
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

                //make sure theres no duplicate first
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

       


    }
}