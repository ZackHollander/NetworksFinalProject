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

                toClient = "What would you like to bid on?" + "\r\n";
                os.writeBytes(toClient);
                String item = br.readLine();

                toClient = "How much would you like to Bid?" + "\r\n";
                os.writeBytes(toClient);
                System.out.println(toClient);
                String bid = br.readLine(); 

                editFile.editItem(item, bid, "active");

            } else if(fromClient.toLowerCase().equals("auction")){
                if(editFile.isInFile("Dog")){
                    toClient = "This item is already up for auction. Please submit the item with a different name." + "\r\n";
                    os.writeBytes(toClient);
                } else {
                    editFile.addItem("Dog", "98");
                }
            } else{
                toClient = "Please try again and enter the words exactly as seen." + "\r\n";
                os.writeBytes(toClient);
            }
       }
    }
}