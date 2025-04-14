//---------------------------------------------------------------------------------------
// Assignment 3
// Question: 
// Written by: Nithushan Kanapathippillai (40313552) and Elliot Boismartel (40315629)
//---------------------------------------------------------------------------------------

import java.io.*;
import java.util.*;

public class TariffApplier {

    //create ArrayList
    static ArrayList<Product> products = new ArrayList<>(); //static to be used in testAllMethods()

    public static void main(String[] args) {



        Scanner input = null;
        PrintWriter outputStream = null;

        try {
            input = new Scanner(new FileInputStream("TradeData.txt"));

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                String [] parts = line.split(",");

                String name = parts[0].trim();
                String country = parts[1].trim();
                String category = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim()); //Double.parseDouble() to convert String to Double

                Product p = new Product(name, country, category, price);
                p.applyTariff();
                products.add(p);
            }

            input.close();


        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found");
            return;
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }

        
        //sort in alphabetical order
        alphabeticalOrder(products);


        try {
            PrintWriter output = new PrintWriter(new FileOutputStream("UpdatedTradeData.txt"));

            for (int i = 0; i<products.size(); i++) {
                output.println(products.get(i).toString());
            }
            output.close();
            
        } catch(FileNotFoundException e) {
            System.out.println("Error, file not found");
            return;
        }

        System.out.println("Updated data has been written.");
    }



    //method to sort in alphabetical order
    public static void alphabeticalOrder(ArrayList<Product> list) {

        for(int i = 0; i<list.size(); i++) {
            for (int j = i+1; j<list.size(); j++) {  

                //compareTo is used on strings
                String s1 = list.get(i).getProductName().toLowerCase();
                String s2 = list.get(j).getProductName().toLowerCase();


                //compares both elements
                if(s1.compareTo(s2)>0) {  //if >0, s2(j) comes before s1(i) in alphabetical order
                    
                    //swapping ArrayList elements  
                    Product temp = list.get(i);  
                    list.set(i, list.get(j));
                    list.set(j, temp); 
                    
                 }  
              }  
           }  
    }

}
