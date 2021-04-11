import java.io.*;
import java.net.*;

class TCPClient {

    public static void receive(BufferedReader inFromServer) throws Exception{
        String fromServer = inFromServer.readLine();
        System.out.println(fromServer + "\n");
    }

    public static void send(BufferedReader inFromUser, DataOutputStream outToServer) throws Exception{
        String toServer = inFromUser.readLine();
        outToServer.writeBytes(toServer + "\r\n");
    }

    public static void main(String argv[]) throws Exception {

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 5000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (true) {
            receive(inFromServer);
            System.out.println("finished recieved");
            send(inFromUser, outToServer);
        }

    }
}