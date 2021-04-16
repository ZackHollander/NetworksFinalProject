import java.util.*;

public class EndAuction extends TimerTask{
    AuctionItem item;

    public EndAuction (AuctionItem item){
        this.item = item;
    }

    public void run() {
        item.endAuction();
    }

}
