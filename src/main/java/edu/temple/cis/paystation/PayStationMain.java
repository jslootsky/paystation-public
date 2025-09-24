package edu.temple.cis.paystation;
import java.util.HashMap;
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
                    System.out.println("\nTime bought: " + ps.readDisplay() + " minutes\n");
                    break;
                case(3)://returns a receipt
                    buyTicket();
                    break;
                case(4):
                    doCancel();
                    break;
                case(5):
                boolean adminRunning = true;
                    while (adminRunning){
                        adminMenu();
                        int adminChoice = userInput.nextInt();
                        switch(adminChoice){
                            case(1):
                                coinEmptied();
                                break;
                            case(2):
                                System.out.println("\nTest:RATE SELECTED\n");
                                break;
                            case(0):
                                adminRunning = false;
                                break;
                        }
                    }
                    break;
                case(0):
                running = false;
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
        System.out.println("5. Admin interface");
        System.out.println("0. Exit");
    }
    private static void adminMenu(){
        System.out.println("Entered admin menu, please select your choice.");
        System.out.println("Admin Menu:");
        System.out.println("1. Empty");
        System.out.println("2. Change Rate");
        System.out.println("0. Exit");
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

    private static void doCancel() {
        Map<Integer, Integer> returned = ps.cancel();
        Map<Integer, Integer> coins = safeCoinMap(returned);
        System.out.println("\nTransaction canceled. Returned coins:");
        System.out.printf("5c: %d, 10c: %d, 25c: %d%n%n",
                coins.getOrDefault(5, 0),
                coins.getOrDefault(10, 0),
                coins.getOrDefault(25, 0));
    }

    private static Map<Integer, Integer> safeCoinMap(Map<Integer, Integer> m) {
        return (m == null) ? new HashMap<>() : m;
    }

    private static void coinEmptied(){
        try {
            System.out.println("Coins have been emptied.");
            //Coins are "emptied", and does work according to the code.
            //Empty does not work as one thinks, where the coins are emptied out of the payStation
            //Cancel would do that
            //Remove explanation later.
            ps.empty();
        } catch (IllegalStateException e) {
        }
    }

}