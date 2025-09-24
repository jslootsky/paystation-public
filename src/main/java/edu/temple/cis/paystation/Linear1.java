package edu.temple.cis.paystation;

public final class Linear1 implements RateStrategy{
    @Override
    public int calculateTime(int cents){
        if (cents <= 0){
            return 0;
        }
        return (cents * 2) / 5; //5c buys 2 mins
    }
}
