import java.io.*;
import java.net.*;
import java.util.*;

public class AuctionServer {
    private int port;
    private Set<String> users = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    
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
 
    boolean hasUsers() {
        return !this.users.isEmpty();
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
 