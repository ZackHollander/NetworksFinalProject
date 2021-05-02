/*
* Auction Application 
* 2020
*
* This application is made by Zack Hollander for his Networks 352 class at Dickinson College. 
* Please feel free to edit and play around with the code. Use this code to learn about TCP 
* applications. The application simulates a chat with an auction functionality. ENJOY!
*/

import java.io.*;
import java.net.*;
import java.util.*;

/*
* This class keeps track of the individual auction. It starts and stops an auction along with
* keeping track of the auciton as a whole.
*/
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
        server.removeItem(item);
        if(currentLeader.equals(itemOwner)){
            server.broadcast("Nobody has bid on " + item + " and has been returned to its original owner.", null);
        }else{
            server.broadcast("SOLD! " + item + " has been sold to " + currentLeader + " at $" + price + ".", null);
        }
    }
}
