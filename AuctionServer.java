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
* This is the main class for the server. It listens for conections from the clients and creates an 
* user Thread for each. It handles all actions needed revolving the application. This includes, 
* creating an auction, biding on an auction, sending a message to all clients, and sending a message 
* to an individual client.
*/
public class AuctionServer {
    private int port;
    private Set<String> users = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    private Set<AuctionItem> auctionList = new HashSet<>(); 
    
    public AuctionServer(int port) {
        this.port = port;
    }

    public void execute(){
        try (ServerSocket serverSocket = new ServerSocket(port)){
            
            System.out.println("Auction Server is listening on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException ex) {
            System.out.println("Error in Auction Server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    void addUserName(String userName) {
        users.add(userName);
    }

    void removeUser(String userName, UserThread aUser) {
        boolean removed = users.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }
 
    Set<String> getUsers() {
        return this.users;
    }

   void sendItemList(UserThread aUser){
        Set<String> items = new HashSet<>();
        for(AuctionItem aItem : auctionList){
            String itemInfo = aItem.getItem() + " $" + aItem.getPrice();
            items.add(itemInfo);
        }
        aUser.sendMessage(items.toString());
    }
 
    boolean hasUsers() {
        return !this.users.isEmpty();
    }

    boolean hasAuctions(){
        return !this.auctionList.isEmpty();
    }

    void newAuction(String item, UserThread aUser, Integer lifespan){
        if(findAuction(item) != null){
            aUser.sendMessage("This item is already up for bid.");
        } else {
            broadcast(("New auction started for: " + item), null);
            AuctionItem auctionItem = new AuctionItem(aUser.getSocket(), this, item, aUser.getUsername(), lifespan);
            auctionList.add(auctionItem);
        }
    }

    void bid(AuctionItem auction, UserThread aUser, Double amount){
        if(auction == null) {
            aUser.sendMessage("This item can not be bid on.");
        }else if(aUser.getUsername().equals(auction.getItemOwner())){
            aUser.sendMessage("You own this item currently and can't bid on it.");
        } else{
            broadcast(("There has been a bid for: " + auction.getItem()), null);
            auction.increasePrice(amount, aUser.getUsername());
        }
    }

    AuctionItem findAuction(String item){
        for (AuctionItem auction : auctionList){
            if (auction.getItem().equals(item)){
                return auction;
            }
        }
        return null;
    }

    void removeItem(String item){
        auctionList.remove(findAuction(item));
    }
    
    public static void main(String[] args){
        if (args.length < 1){
            System.out.println("Syntax: java AuctionServer <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        AuctionServer server = new AuctionServer(port);
        server.execute();
    }
}
 