import java.io.*;
import java.net.*;
import java.util.*;

public class editFile {

    static File file = new File("itemList.txt");

    public static void editItem(String item, String price, String status){
        
    }

    public static void addItem(String item, String price){
        try{
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);

            String entry = item + "|" + price + "|active";
            pr.println(entry);

            try {
				pr.close();
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
            }   
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printFile(){
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              System.out.println(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}