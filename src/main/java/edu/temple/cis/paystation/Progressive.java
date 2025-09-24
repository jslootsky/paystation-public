package edu.temple.cis.paystation;

public class Progressive implements RateStrategy {
    public int calculateTime(int amountInCents) {
        if (amountInCents <= 0)
            return 0;

        if (amountInCents < 150) {
            // Less than 1 hour - same as Linear1
            return (amountInCents * 2) / 5;
        } else if (amountInCents < 350) {
            // Between 1st and 2nd hour
            return 60 + ((amountInCents - 150) * 3) / 10;
        } else {
            // Beyond 2 hours
            return 120 + (amountInCents - 350) / 5;
        }
    }
}
