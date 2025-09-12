package edu.temple;
import java.util.Scanner;

public class PayStationMain {
    public static void main (String[] args){
        Scanner userInput = new Scanner(System.in);
        System.out.println("Menu:");
        System.out.println("1. Deposit coins");
        System.out.println("2. Display");
        System.out.println("3. Buy Ticket");
        System.out.println("4. Cancel");
        System.out.println("0. Admin interface");

        int input = userInput.nextInt();
        System.out.println("You entered: " + input);
    }
}
