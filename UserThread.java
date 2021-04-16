import java.io.*;
import java.net.*;

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