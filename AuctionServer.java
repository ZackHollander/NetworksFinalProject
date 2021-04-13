import java.io.*;
import java.net.*;
import java.util.*;

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

    void newAuction(String item, UserThread aUser){
        if(findAuction(item) != null){
            aUser.sendMessage("This item is already up for bid.");
        } else {
            broadcast(("New auction started for: " + item), null);
            AuctionItem auctionItem = new AuctionItem(aUser.getSocket(), this, item);
            auctionList.add(auctionItem);
        }
    }

    void bid(AuctionItem auction, UserThread aUser, Double amount){
        if(auction == null) {
            aUser.sendMessage("This item can not be bid on.");
        }else{
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
 