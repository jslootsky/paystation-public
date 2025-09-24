package edu.temple.cis.paystation;

public final class Linear2 implements RateStrategy {
    @Override
    public int calculateTime(int cents) {
        if (cents <= 0){
            return 0;
        }
        return cents / 5; // 5c buys 1 minute
    }
}
