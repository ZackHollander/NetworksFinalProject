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
    String itemOwner;
    Timer timer;


    public AuctionItem(Socket socket, AuctionServer server, String item, String itemOwner, Integer lifespan) {
        this.socket = socket;
        this.server = server;
        this.item = item;
        this.price = 0.0;
        this.itemOwner = itemOwner;
        this.currentLeader = itemOwner;
        startTimer(lifespan);
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

    String getItemOwner(){
        return itemOwner;
    }

    void startTimer(Integer lifespan){
        timer = new Timer();
        timer.schedule(new EndAuction(this), lifespan * 60*1000); // Number of minutes live for.
    }

    void endAuction(){
        timer.cancel();
        if(currentLeader.equals(itemOwner)){
            server.broadcast("SOLD! " + item + " has been sold to " + currentLeader + ".", null);
        }else{
            server.broadcast("Nobody has bid on " + item + " and has been returned to its original owner.", null);
        }
    }
}
