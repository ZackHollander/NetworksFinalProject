import java.io.*;
import java.net.*;
import java.util.*;

public class Bid {

    InputStream is;
    DataOutputStream os;
    BufferedReader br;

    public Bid(InputStream is, DataOutputStream os, BufferedReader br) {
        this.is = is;
        this.os = os;
        this.br = br;
    }

    public String[] newBid(BufferedReader br){
        
        String bid[] = new String[3]; // Three becasue there are three objects for an entry in the list.
        String toClient;
        
        toClient = "What would you like to bid on?" + "\r\n";
                os.writeBytes(toClient);
                bid[0] = br.readLine(); // Stores the item
                
                if(!editFile.isInFile(bid[0])){
                    toClient = "The item is not up for auction. Please choose another Item.";
                    newBid(br);
                }

                toClient = "How much would you like to Bid?" + "\r\n";
                os.writeBytes(toClient);
                System.out.println(toClient);
                String bid = br.readLine(); 

                editFile.editItem(item, bid, "active");

        return bid;
    }
    
    
}
