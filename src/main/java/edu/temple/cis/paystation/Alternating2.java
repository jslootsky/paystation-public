package edu.temple.cis.paystation;
import java.util.Calendar;

public class Alternating2 implements RateStrategy{
    private RateStrategy weekdayStrategy = new Linear1();
    public int calculateTime(int amount) {
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_WEEK);

        // Weekend: Saturday or Sunday
            if (!(day == Calendar.SATURDAY || day == Calendar.SUNDAY)) {
                return weekdayStrategy.calculateTime(amount);
            } else {
                return calculateTime(amount);
            }
        }
    }