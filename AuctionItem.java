import java.io.*;
import java.net.*;
import java.util.*;

public class AuctionItem extends Thread {
    private Socket socket;
    private AuctionServer server;
    private PrintWriter writer;
    
    Double price;
    String item;
    String currentLeader;


    public AuctionItem(Socket socket, AuctionServer server, String item) {
        this.socket = socket;
        this.server = server;
        this.item = item;
        this.price = 0.0;
    }

    void increasePrice(Double amount, String user){
        price += amount;
        server.broadcast(item + " has increased to " + price + ".", null);
        currentLeader = user;
    }

    String getItem(){
        return item;
    }

    Double getPrice(){
        return price;
    }

    String getCurrentLeader() {
        return currentLeader;
    }
}
