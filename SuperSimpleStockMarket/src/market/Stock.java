package market;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class Stock {
	
	private String stockSymbol;
	private StockType type;
	private int lastDividend;
	private double fixedDividend;
	private int parValue;
	private TreeMap<Date, TradeRecord> stockTrades;
	private double givenPrice = -1;
	
	public Stock(String stockSymbol, StockType type, int lastDividend, double fixedDividend, int parValue) {
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
		this.stockTrades = new TreeMap<>();
		generateTrades();
	}

    public Stock(String stockSymbol, StockType type, int lastDividend, int parValue) {
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = -1;
		this.parValue = parValue;
		this.stockTrades = new TreeMap<>();
		generateTrades();
	}
    
    /**
     * Generates 5 arbitrary TradeRecords and puts them in the TreeMap
     */
    private void generateTrades() {
		System.out.println("\nGenerating fake trade records for stock: " + stockSymbol);
        Random rd = new Random();
        
        for (int i = 0; i < 5; i++) {
            // Create arbitrary timestamp and TradeRecord data 
        	// within the last 20 minutes
            
            int minutes   = rd.nextInt(21);
            int sharesQty = rd.nextInt(100) + 1;
            boolean isBuy = rd.nextBoolean();
            double price  = (rd.nextDouble() + 0.1) * 100;
            // truncate price to 2 decimal places
            String tmpPrc = String.format(Locale.ENGLISH, "%.2f", price);
            price = Double.parseDouble(tmpPrc);
            TradeRecordType indicator = isBuy ? TradeRecordType.BUY : TradeRecordType.SELL;
            
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -minutes);
            Date date = cal.getTime();
            
            // Create and save fake trade record
            TradeRecord rec = new TradeRecord(date, sharesQty, indicator, price);
            System.out.println(rec);
            this.stockTrades.put(date, rec);
        }
    }

	// Getters - Setters
	
	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public int getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(int lastDividend) {
		this.lastDividend = lastDividend;
	}

	public double getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public int getParValue() {
		return parValue;
	}

	public void setParValue(int parValue) {
		this.parValue = parValue;
	}
	
	public TreeMap<Date, TradeRecord> getStockTrades() {
	    return this.stockTrades;
	}
	
	public void setStockTrades(TreeMap<Date, TradeRecord> stockTrades) {
        this.stockTrades = stockTrades;
    }
	
	public double getGivenPrice() {
	    return this.givenPrice;
	}
	
	public void setGivenPrice(double givenPrice) {
	    this.givenPrice = givenPrice;
	}

	// Formulas about a stock
	
	public double dividendYield(double price) {
	    if (price <= 0.0) {
            return -1;
        }
	    
	    if (StockType.COMMON == this.type) {
	        return dividendYield(this.lastDividend, price);
	    } else {
	        return dividendYield(this.fixedDividend, this.parValue, price);
	    }
	}
	
	// Dividend Yield formula for stock type: Common
	private double dividendYield(int lastDividend, double price) {
	    return lastDividend / price;
    }
	
	// Dividend Yield formula for stock type: Preferred
	private double dividendYield(double fixedDividend, int parValue, double price) {
	    return (fixedDividend * parValue) / price;
    }

	public double peRatio(double price) {
	    if (price <= 0.0) {
            return -1;
        }
	    // Unclear from the specs whether dividend yield 
	    // or last dividend should be used
	    return price / dividendYield(price);
	}
	
	public double volumeWeightedStockPrice() {
		System.out.println("\nCalculating Volume Weighted Stock Price for trades over the last 15 minutes");
	    
		double result = -1;
	    double tradedPriceSum = 0.0;
	    int quantitySum = 0;
	    
	    Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -15);
        Date date = cal.getTime();
        
        SortedMap<Date, TradeRecord> someRecs = getStockTrades().tailMap(date);
        for (TradeRecord rec : someRecs.values()) {
            System.out.println(rec);
            tradedPriceSum += rec.getTradedPrice() * rec.getSharesQuantity();
            quantitySum += rec.getSharesQuantity();
        }
        
        if (quantitySum > 0) {
            result = tradedPriceSum / quantitySum;
        }
        return result;
    }

    
	@Override
    public String toString() {
        return String.format(
                "Stock %s [type=%s, lastDividend=%s, fixedDividend=%s, parValue=%s, givenPrice=%s]",
                stockSymbol, type, lastDividend, fixedDividend, parValue, givenPrice);
    }
	
}
