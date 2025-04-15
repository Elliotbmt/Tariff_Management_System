
import java.io.*;
import java.util.*;
public class TradeManager {
    public static void main(String[] args) {

        System.out.println("Welcome to the Trade Manager!");

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
            System.out.println("Error File not found (Tariffs).");
            return;
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }

        //c)
        ArrayList<TradeRequest> request = new ArrayList<>();

        //read and store requests
        try {
            Scanner input2 = new Scanner(new FileInputStream("TradeRequests.txt"));
            while (input2.hasNextLine()) {
                String line = input2.nextLine().trim();

                String[] parts = line.split(" ");
                String rNb = parts[0].trim();
                String destinationCountry = parts[1].trim();
                String originCountry = parts[2].trim();
                String productCategory = parts[3].trim();
                int tradeValue = Integer.parseInt(parts[4].trim());
                int proposedTariff = Integer.parseInt(parts[5].trim());

                TradeRequest tr = new TradeRequest(rNb, destinationCountry, originCountry, productCategory, tradeValue, proposedTariff);
                request.add(tr);
            }
            input2.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found (Trade).");
        }

        //process requests using methods from tarifflist
        System.out.println("\n--------------------------------\nResults of the trade requests:\n--------------------------------");
        for (int i = 0; i < request.size(); i++) {
            TradeRequest tr = request.get(i);
            TariffList.TariffNode node = list1.find(tr.getDCountry(),tr.getOCountry(),tr.getPCategory());

            //if find() doesn't find a matching tariff
            if (node==null){
                System.out.println("\n"+tr.getRnb()+" - Rejected. No matching Tariff.");
                continue;
            }

            String result=list1.evaluateTrade(tr.getPTariff(),node.getT().getMinimumTariff());

            System.out.println("\n"+tr.getRnb()+" - "+result);

            //Trade outcome details computed in driver because we need int tradeValue from TradeManager.
            switch (result){
                case ("Accepted"):
                    System.out.println("\nProposed tariff meets or exceeds the minimum requirement.");
                    break;

                case ("Conditionally Accepted"):
                    double surcharge = tr.getTValue() * ((node.getT().getMinimumTariff() - tr.getPTariff() / 100.0));
                    System.out.println("\nProposed tariff "+tr.getPTariff()+"% is within 20% of the required minimum tariff "+node.getT().getMinimumTariff()+"%." +
                            "\nA surcharge of "+surcharge+"$ is applied.");
                    break;

                case ("Rejected"):
                    System.out.println("\nProposed tariff "+tr.getPTariff()+"is more than 20% below the required minimum tariff "+node.getT().getMinimumTariff());
                    break;

                default:
                    System.out.println("Error");
                    break;
            }

        }

        //d)
        Scanner input3=new Scanner(System.in);
        System.out.print("\n---------------------------------------------\nd) Search for a tariff. Enter a tariff separated by spaces (destination country, origin country, product category): ");
        String destination=input3.next();
        String origin=input3.next();
        String product=input3.next();

        TariffList.TariffNode result=list1.find(origin, destination, product);

        if (result!=null){
            System.out.print("Tariff found: "+result.getT());
        } else {
            System.out.print("Tariff not found");
        }


        //e) test all methods
        System.out.print("\n-----------------------------\ne) Test all methods? (y or n): ");
        input3.nextLine();
        String choice=input3.nextLine();
        if (choice.equalsIgnoreCase("n")){
            System.out.print("Ending program.");
            System.exit(0);
        } else if (choice.equalsIgnoreCase("y")){
            testAllMethods();
            System.exit(0);
        } else {
            System.out.println("Invalid.");
            System.out.print("Ending program.");
            System.exit(0);
        }
    }




    public static class TradeRequest{
        private String rNb;
        private String destinationCountry;
        private String originCountry;
        private String productCategory;
        private int tradeValue;
        private int proposedTariff;

        private static int counter = 1;   //for request number

        public TradeRequest(String rNb, String destinationCountry, String originCountry,String productCategory,int tradeValue,int proposedTariff){
            this.rNb=rNb;
            this.destinationCountry = destinationCountry;
            this.originCountry = originCountry;
            this.productCategory = productCategory;
            this.tradeValue = tradeValue;
            this.proposedTariff = proposedTariff;
        }

        public String getRnb() {return rNb;}

        public String getDCountry() {return destinationCountry;}

        public String getOCountry() {return originCountry;}

        public String getPCategory() {return productCategory;}

        public int getTValue() {return tradeValue;}

        public int getPTariff() {return proposedTariff;}

    }

    public static void testAllMethods(){

        //Tariff tests
        System.out.println("\n---------------------------------\nTESTS\n---------------------------------\n\nTariff Tests-----------------------");
        Tariff t1=new Tariff("France", "USA", "CPU", 20);
        Tariff t2=new Tariff("Japan", "Canada", "Mouse", 30);
        Tariff t3=new Tariff(t1);
        Tariff t4=t1.clone();

        System.out.println("\nTariff 1: "+t1.toString()+"\nTariff 2: "+t2.toString()+
                "\nCopy of Tariff 1: "+t3.toString()+"\nClone of Tariff 1: "+t4.toString()+
                "\nEquals method (Tariff 1 and Tariff 2): "+t1.equals(t2)+
                "\nEquals method (Tariff 1 and Copy of Tariff 1): "+t1.equals(t3)+
                "\n\nTariffList Tests-------------------------");

        //TariffList tests
        TariffList testList1=new TariffList();
        testList1.addToStart(new Tariff(t1)); //deep copy
        System.out.println("\nList: "+TariffList.printList(testList1));

        testList1.addToStart(new Tariff(t2)); //deep copy
        System.out.println("\nList after using addToStart() to add Tariff 2: "+TariffList.printList(testList1));

        testList1.insertAtIndex(1,new Tariff(t2)); //deep copy
        System.out.println("\nList after using insertAtIndex(1) to add Tariff 2: "+TariffList.printList(testList1));

        testList1.deleteFromStart();
        System.out.println("\nList after using deleteFromStart() to remove the first Tariff: "+TariffList.printList(testList1));

        testList1.deleteFromIndex(0);
        System.out.println("\nList after using deleteFromIndex(0) to remove Tariff at index 0: "+TariffList.printList(testList1));

        testList1.replaceAtIndex(0, new Tariff(t2)); //deep copy
        System.out.println("\nList after using replaceAtIndex(0) to replace Tariff at index 0 Tariff 2: "+TariffList.printList(testList1));

        testList1.addToStart(new Tariff(t1)); //deep copy
        testList1.addToStart(new Tariff(t2));
        System.out.println("\nList after adding Tariff 1 and Tariff 2 (for later tests): "+TariffList.printList(testList1));

        System.out.println("\nUsing find() method on Tariff with origin 'USA', destination 'France', category 'CPU'.");
        TariffList.TariffNode node=testList1.find("France", "USA", "CPU");
        if (node!=null) {
            System.out.println(node.getT().toString());
        }

        System.out.println("\nUsing contain() method on Tariff with origin 'Canada', destination 'Japan', category 'Mouse'?: "
                +testList1.contains("Japan", "Canada", "Mouse"));


        //test equals method
        TariffList testList2=new TariffList();
        testList2.addToStart(new Tariff(t1)); //deep copy every time
        testList2.addToStart(new Tariff(t1));
        testList2.addToStart(new Tariff(t1));
        System.out.println("\nList 1: "+TariffList.printList(testList1)+"\nList 2: "+TariffList.printList(testList2)+
                "\nUsing equals() method on list 1 and list 2: "+testList1.equals(testList2));

        System.out.println("\nUsing evaluateTrade() method with proposed tariff 25% and minimum tariff 20%. Result: "+testList1.evaluateTrade(25, 20));

        //products test
        System.out.println("\n\nProducts test---------------------------");

        TariffApplier.main(new String[0]); //to put the products in arraylist<products>.
        TariffApplier.alphabeticalOrder(TariffApplier.products);

        System.out.println("Sort products from ArrayList<Products> in alphabetical order: ");
        for (int i=0; i<TariffApplier.products.size(); i++) {
            System.out.println(TariffApplier.products.get(i).toString());
        }

        System.out.println("\n\n----------------------------------\nDone\nEnding program.");
    }
}