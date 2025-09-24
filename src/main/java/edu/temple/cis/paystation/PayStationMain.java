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
                                changeRateStrategyAdmin();
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

    private static void changeRateStrategyAdmin() {
        if (!(ps instanceof PayStationImpl)) {
            System.out.println("This PayStation does not support changing strategies at runtime.\n");
            return;
        }

        PayStationImpl impl = (PayStationImpl) ps;

        System.out.println("Select client (rate strategy):");
        System.out.println("1. Alphatown (Linear1: 5c → 2 min)");
        System.out.println("2. Betatown (Progressive)");
        System.out.println("3. Gammatown (Alternating1: weekdays=Progressive, weekends=Linear1)");
        System.out.println("4. Deltatown (Linear2: 5c → 1 min)");
        System.out.println("5. Omegatown (Alternating2: weekdays=Linear1, weekends=Free)");
        int sel = userInput.nextInt();

        switch (sel) {
            case 1:
                impl.setRateStrategy(new Linear1());
                System.out.println("Set to Alphatown (Linear1).\n");
                break;
            case 2:
                impl.setRateStrategy(new Progressive());
                System.out.println("Set to Betatown (Progressive).\n");
                break;
            case 3:
                impl.setRateStrategy(new Alternating1());
                System.out.println("Set to Gammatown (Alternating1: weekdays Progressive, weekends Linear1).\n");
                break;
            case 4:
                impl.setRateStrategy(new Linear2());
                System.out.println("Set to Deltatown (Linear2).\n");
                break;
            case 5:
                impl.setRateStrategy(new Alternating2()); // update Alternating2 similarly
                System.out.println("Set to Omegatown (Alternating2: weekdays Linear1, weekends Free).\n");
                break;
            default:
                System.out.println("Invalid selection.\n");
        }
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
        System.out.println("\nEntered admin menu, please select your choice.");
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
        System.out.println("\nCoins inserted: 5:" + coins.getOrDefault(5, 0) +
                ", 10:" + coins.getOrDefault(10, 0) +
                ", 25:" + coins.getOrDefault(25, 0) +
                "\n");

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
            //Coins are "emptied", and does work according to the code.
            //Empty does not work as one thinks, where the coins are emptied out of the payStation
            //Cancel would do that
            //Remove explanation later.
            
            int cents = ps.empty();
            System.out.println("Machine emptied. Total collected: " + cents + " cents.\n");
        } catch (IllegalStateException e) {
        }
    }

}