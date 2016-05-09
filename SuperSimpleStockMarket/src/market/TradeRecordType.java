package market;


public enum TradeRecordType {
    BUY('B'),
    SELL('S');
    
    private char indicator;
    
    private TradeRecordType(char indicator) {
        this.indicator = indicator;
    }
    
    @Override
    public String toString() {
        return Character.toString(indicator);
    }
}
