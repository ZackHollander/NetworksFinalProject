import java.io.*;
import java.net.*;
import java.util.*;

public class editFile {

    static File file = new File("itemList.txt");

    public static void editItem(String item, String price, String status) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();

            while ((strLine = br.readLine()) != null) {
                String tokens[] = strLine.split("@");
                if (tokens.length > 0) {
                    if (tokens[0].equals(item)) {
                        tokens[1] = price;
                        tokens[2] = status;
                        String newLine = tokens[0] + "@" + tokens[1] + "@" + tokens[2];
                        fileContent.append(newLine);
                        fileContent.append("\n");
                    } else {
                        fileContent.append(strLine);
                        fileContent.append("\n");
                    }
                }
            }
            FileWriter fstreamWrite = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fstreamWrite);
            out.write(fileContent.toString());
            out.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void addItem(String item, String price) {
        try {
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);

            String entry = item + "@" + price + "@active";
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

    public static void printFile() {
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred, while parsing the items.");
            e.printStackTrace();
        }
    }

    public static boolean isInFile(String item) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String tokens[] = strLine.split("@");
                if (tokens[0].equals(item)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static int priceOfItem(String item){
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();

            int price;

            while ((strLine = br.readLine()) != null) {
                String tokens[] = strLine.split("@");
                if (tokens.length > 0) {
                    if (tokens[0].equals(item)) {
                        price = Intiger.parseInt(tokens[1]);
                    }
                }
            }
            return price;
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}