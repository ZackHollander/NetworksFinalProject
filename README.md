Networks Final Project. 

Auction Application can run on any machine that supports java. There are two steps in executing the application. The first step can be skipped if a server is already running. If a server is not already running one must start the Server Component before the Client Component. 

1) To start the Server Component, navigate to the NetworksFinalProject directory and run the command below. A message saying: Auction Server is listening on port: <port to be ran on>, will be printed on the screen.
   
java AuctionServer {port to be ran on}
  
  
2) After the Server Component is up and running the Client Component can be ran. To run the Client Component again navigate the NetworksFinalProject directory. Type the command below. A message will appear with instructions on how to control the application. The IP address and the port number are needed, do to the use of the java.net.socket package.

java ClientService {Server Component IP address} {Server Component port} 

The IP address is not needed for the Server Component as the socket package takes the hosts IP address.
