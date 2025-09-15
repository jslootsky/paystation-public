package edu.temple.cis.paystation;
import java.util.Scanner;

public class PayStationMain {
    private static PayStation ps;

    public static void main(String[] args) throws IllegalCoinException {
        Scanner userInput = new Scanner(System.in);
        ps = new PayStationImpl();

        boolean running = true;
        
        while(running){
            printMenu();

            int input = userInput.nextInt();
            switch (input){
                case(1):
                    depositCoins();
                case(4):
                running = false;
            }
        }

        userInput.close();
    }

        
    /// function that prints the menu
    private static void printMenu(){
        System.out.println("Menu:");
        System.out.println("1. Deposit coins");
        System.out.println("2. Display");
        System.out.println("3. Buy Ticket");
        System.out.println("4. Cancel");
        System.out.println("0. Admin interface");
    }

    //functions that carry out menu functions 
    private static void depositCoins() throws IllegalCoinException{
        System.out.println("Enter -1 to exit");
        Scanner input = new Scanner(System.in);
        int coinInput = input.nextInt();
        while(coinInput != -1){
            try{
                ps.addPayment(coinInput);
            }catch (Exception e){
                System.out.println(e.toString());
            }
            coinInput = input.nextInt();
            
            System.out.println("Enter -1 to exit");
        }
        
    }

}