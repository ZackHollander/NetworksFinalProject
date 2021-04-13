import java.net.*;
import java.io.*;

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

            System.out.println("Connected to the Auction server! Feel Free to chat among the active people.");
            System.out.println("To get a List of active auctions and the current price type [LIST]. \n" +
                                "If you would like to bid on an item type [BID <item> <amount to increase by>].\n" +
                                "To put an item up for auction type [ADD <Item's name>]. All start prices start at 0.\n" +
                                "To leave the auction type [bye].");

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
