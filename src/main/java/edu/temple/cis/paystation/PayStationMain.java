package edu.temple.cis.paystation;
import java.util.Map;
import java.util.Scanner;

public class PayStationMain {
    private static PayStation ps;
    private static Scanner userInput;

    public static void main(String[] args) throws IllegalCoinException {
        userInput = new Scanner(System.in);
        ps = new PayStationImpl();

        boolean running = true;
        
        while(running){
            printMenu();

            int input = userInput.nextInt();
            switch (input){
                case(1):
                    depositCoins();
                    break; //prevents falling straight to case 4 after case 1 is executed
                case(2):
                    System.out.println("Time bought: " + ps.readDisplay() + " minutes");
                    break;
                case(3)://returns a receipt
                    buyTicket();
                    break;
                case(4):
                     Map<Integer, Integer> c = ps.cancel();
                     System.out.println("Coins cancelled: "+ c);
                    break;
            }
        }

        userInput.close();
    }

    /// function that prints the menu
    private static void printMenu(){
        System.out.println("Hello! Welcome to the PayStation. Please select your choice:");
        System.out.println("Menu:");
        System.out.println("1. Deposit coins");
        System.out.println("2. Display");
        System.out.println("3. Buy Ticket");
        System.out.println("4. Cancel");
        System.out.println("0. Admin interface");
    }

    //functions that carry out menu functions 
    private static void depositCoins() throws IllegalCoinException{
        System.out.println("You have selected: Deposit coins. Enter -1 to exit");

        int coinInput = userInput.nextInt();
        while(coinInput != -1){
            try{
                ps.addPayment(coinInput);
                System.out.println("You have entered: " + coinInput + ". Enter -1 to exit");
            }catch (Exception e){
                System.out.println(e.toString());
            }
            coinInput = userInput.nextInt();
        }

        //summary
        Map<Integer, Integer> coins = ps.returnMap();
        System.out.println("Coins inserted: 5:" + coins.getOrDefault(5, 0) +
                ", 10:" + coins.getOrDefault(10, 0) +
                ", 25:" + coins.getOrDefault(25, 0));

    }
    
    private static void buyTicket() {
        try {
            Receipt r = ps.buy();
            int minutes = r.value();
            int h = minutes / 60, m = minutes % 60;
            System.out.println("\n=== Receipt ===");
            System.out.printf("Time: %d min (%dh %02dm)%n", minutes, h, m);
            System.out.println("===============\n");
        } catch (IllegalStateException e) {
            System.out.println("Nothing to buy. Insert coins first.\n");
        }
    }

}