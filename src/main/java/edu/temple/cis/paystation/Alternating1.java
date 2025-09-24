package edu.temple.cis.paystation;
import java.util.Calendar;

public class Alternating1 implements RateStrategy{
    private RateStrategy weekdayStrategy = new Progressive();
    private RateStrategy weekendStrategy = new Linear1();
    public int calculateTime(int amount) {
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_WEEK);

        // Weekend: Saturday or Sunday
            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                return weekendStrategy.calculateTime(amount);
            } else {
                return weekdayStrategy.calculateTime(amount);
            }
        }
    }
