/*
* Auction Application 
* 2020
*
* This application is made by Zack Hollander for his Networks 352 class at Dickinson College. 
* Please feel free to edit and play around with the code. Use this code to learn about TCP 
* applications. The application simulates a chat with an auction functionality. ENJOY!
*/

import java.util.*;

/*
*This class keeps track of the timer for each auction. Once the timer ends it ends the auction.
*/
public class EndAuction extends TimerTask{
    AuctionItem item;

    public EndAuction (AuctionItem item){
        this.item = item;
    }

    public void run() {
        item.endAuction();
    }

}
