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

/*
* Takes the specific user socket created by the AuctionServer and waits for the users input.
* UserThread will then parse the input and decided the specific action to take place next.
* This includes broadcasting a messge, adding an auction item, bidding on an acution item, or
* lisitng all items up for auction.  
*/
public class UserThread extends Thread {
    private Socket socket;
    private AuctionServer server;
    private PrintWriter writer;

    String userName;
 
    public UserThread(Socket socket, AuctionServer server) {
        this.socket = socket;
        this.server = server;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers();
 
            userName = reader.readLine();
            server.addUserName(userName);
 
            String serverMessage = "New bidder has connected: " + userName;
            server.broadcast(serverMessage, this);
 
            String clientMessage;
 
            //Parses the message and decides actions to take place after
            do {
                clientMessage = reader.readLine();
                String[] clientInput = clientMessage.split(" ");
                if (clientInput[0].equals("ADD") && clientInput.length == 3){
                    server.newAuction(clientInput[1], this, Integer.parseInt(clientInput[2]));
                }else if(clientInput[0].equals("BID") && clientInput.length == 3){

                    if(server.hasAuctions()){
                        server.bid(server.findAuction(clientInput[1]), this, Double.parseDouble(clientInput[2]));
                    } else {
                        sendMessage("There are no items to bid on.");
                    }
                    
                }else if(clientInput[0].equals("LIST") && clientInput.length == 1){
                    server.sendItemList(this);
                }else{
                    serverMessage = "[" + userName + "]: " + clientMessage;
                    server.broadcast(serverMessage, this);
                }
 
            } while (!clientMessage.equals("bye"));
 
            server.removeUser(userName, this);
            socket.close();
 
            serverMessage = userName + " has left.";
            server.broadcast(serverMessage, this);
 
        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUsers());
        } else {
            writer.println("No other users connected");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }

    Socket getSocket(){
        return socket;
    }

    String getUsername(){
        return userName;
    }
}