package market;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TradeRecord {
    private Date timestamp;
    private int sharesQuantity;
    private TradeRecordType indicator;
    private double tradedPrice;
    
    public TradeRecord(Date timestamp, int sharesQuantity, TradeRecordType indicator, double tradedPrice) {
        this.timestamp = timestamp;
        this.sharesQuantity = sharesQuantity;
        this.indicator = indicator;
        this.tradedPrice = tradedPrice;
    }
    
    // Getters - Setters
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getSharesQuantity() {
        return sharesQuantity;
    }
    
    public void setSharesQuantity(int sharesQuantity) {
        this.sharesQuantity = sharesQuantity;
    }
    
    public TradeRecordType getIndicator() {
        return indicator;
    }
    
    public void setIndicator(TradeRecordType indicator) {
        this.indicator = indicator;
    }
    
    public double getTradedPrice() {
        return tradedPrice;
    }
    
    public void setTradedPrice(double tradedPrice) {
        this.tradedPrice = tradedPrice;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        return String.format("TradeRecord [timestamp=%s, sharesQuantity=%s, indicator=%s, tradedPrice=%s]",
                formatter.format(getTimestamp()), sharesQuantity, indicator, tradedPrice);
    }
    
}
