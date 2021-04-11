import java.io.*;
import java.net.*;

public class serveRequest implements Runnable {
    Socket socket;

    public serveRequest(Socket socket) throws Exception {
        this.socket = socket;
    }

    public void run() {
        
        try {
            bidApplication();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void bidApplication() throws Exception {

        InputStream is = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));



        while(true){
            String toClient = "Would you like to put an item up for auction or bid on one?" 
                                + " If Bid type 'bid'. If auction type 'auction'." + "\r\n";
            os.writeBytes(toClient);
            System.out.println("Sent to client: " + toClient + "\n");

            String fromClient = br.readLine();
            if(fromClient.toLowerCase().equals("bid")){
                // bid();
                System.out.println("bid()");
            } else if(fromClient.toLowerCase().equals("auction")){
                editFile.addItem("Dog", "98");
                System.out.println("newItem()");
            } else{
                toClient = "Please try again and enter the words exactly as seen." + "\r\n";
                os.writeBytes(toClient);
            }
       }
    }
}