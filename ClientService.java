/*
* Auction Application 
* 2020
*
* This application is made by Zack Hollander for his Networks 352 class at Dickinson College. 
* Please feel free to edit and play around with the code. Use this code to learn about TCP 
* applications. The application simulates a chat with an auction functionality. ENJOY!
*/

import java.net.*;
import java.io.*;

/*
* This class is the main class for the client. It takes in the IP address and port number to create a
* socket to connect to the network server. It then creates two threads a ReadThread and WriteThread class. 
* These work as input and out functions to comunicate with the network server.
*/
public class ClientService {
    private String hostname;
    private int port;
    private String userName;

    public ClientService(String hostname, int port) {
            this.hostname = hostname;
            this.port = port;
        }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);

            System.out.println("\nConnected to the Auction server! Feel Free to chat among the active people.");
            System.out.println("To get a List of active auctions and the current price type [LIST]. \n" +
                                "If you would like to bid on an item type [BID <item> <amount to increase by>].\n" +
                                "To put an item up for auction type [ADD <Item's name> <Auction lifeSpan in  whole minuets>]. All start prices start at 0.\n" +
                                "To leave the auction type [bye].");

            //Creates input and output threads
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }

    void setUser(String userName) {
        this.userName = userName;
    }

    String getUser() {
        return this.userName;
    }

    public static void main(String[] args) {
        if (args.length < 2)
            return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        ClientService client = new ClientService(hostname, port);
        client.execute();
    }
}
