package edu.temple.cis.paystation;

public class RateImpl implements RateStrategy{
    public int calculateTime(int time){
        return time;
    }

    @Override
    public double calculateStrategy() {
        throw new IllegalArgumentException("Unimplemented method 'calculateStrategy'");
    }
    
}
