package market;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class StockMarket {
	Map<String,Stock> globalExchange = new HashMap<String,Stock>();
	
	public void populate() {
		Stock s1 = new Stock("TEA", StockType.COMMON, 0, 100);
		Stock s2 = new Stock("POP", StockType.COMMON, 8, 100);
		Stock s3 = new Stock("ALE", StockType.COMMON, 23, 100);
		Stock s4 = new Stock("GIN", StockType.PREFERRED, 8, 0.02, 100);
		Stock s5 = new Stock("JOE", StockType.COMMON, 13, 250);
		
		globalExchange.put(s1.getStockSymbol(), s1);
		globalExchange.put(s2.getStockSymbol(), s2);
		globalExchange.put(s3.getStockSymbol(), s3);
		globalExchange.put(s4.getStockSymbol(), s4);
		globalExchange.put(s5.getStockSymbol(), s5);
	}
	
	// Formulas regarding many stocks
	
	public double calcGBCE() {
	    int root = 0;
	    double product = 1.0;
	    double result = -1.0;
	    
	    for (Stock s : globalExchange.values()) {
	    	// Unclear from the specs whether the given price by the user,
	    	// or par value, or last traded price should be used
	        double prc = s.getGivenPrice();
	        if (prc < 0.0) {
	            continue;
	        }
	        root++;
            product *= prc;
	    }
	    
	    if (root > 0) {
	        result = Math.pow(product, 1.0 / root);
	    }
        return result;
    }
	
	public static void main(String args[]) {
		StockMarket simpleMarket = new StockMarket();
		simpleMarket.populate();
		
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(in);
		
		while (true) {
    		System.out.print("\nEnter a stock symbol or type Q to continue: ");
    		try {
                String symbol = br.readLine();
                if ("Q".equalsIgnoreCase(symbol)) {
                    break;
                }
                symbol = symbol.toUpperCase();
                System.out.print("Enter a price greater than 0: ");
                String sPrice = br.readLine();
                
                double price = Double.parseDouble(sPrice);
                
                Stock stock = simpleMarket.globalExchange.get(symbol);
                if (stock != null) {
                    stock.setGivenPrice(price);
                    System.out.printf("\nSelected stock:\n%s ", stock);
                    System.out.printf("\nDividend Yield = %f\nP/E Ratio = %f\n", 
                            stock.dividendYield(price),
                            stock.peRatio(price));
                    System.out.printf("Volume Weighted Stock Price = %f\n", stock.volumeWeightedStockPrice());
                    
                } else {
                    System.out.println("Stock not found. Try another symbol.");
                }
                
            } catch (IOException ex) {
                System.out.println("\nWrong input. Try again.");
            } catch (NumberFormatException ex) {
                System.out.println("\nWrong number format. The price should be of the format 0.00");
            }
		}
		
		System.out.printf("GBCE All Share Index = %f\n", simpleMarket.calcGBCE());
	}
}
