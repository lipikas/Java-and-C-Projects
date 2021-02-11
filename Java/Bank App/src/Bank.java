package practice.src;

import java.util.Scanner;

public class Bank extends Interest{

    private double balance;
    private double prevTransc;
    private String customerName;
    private int customerId; // ssn
    String value = "Please enter a number greater than 0.";

    Bank (String name, int id){
        this.customerName = name;
        this.customerId = id;
    }
    Bank (String name, int id, double bal){
        this.customerName = name;
        this.customerId = id;
        this.balance = bal;
    }
    void deposit(double amount){
        if (amount > 0){
            balance += amount;
            prevTransc = amount;
        }
        else throw new IllegalArgumentException(value);
    }
    public String getName(){
        return customerName;
    }
    public int getID(){
        return customerId;
    }
    public double getBalance(){
        return balance;
    }
    void withdraw(double amount){
        if (amount > 0){
            if (balance-amount >= 0){
                balance -= amount;
                prevTransc = -amount;
            }
            else throw new IllegalArgumentException("Your account has insufficient balance. Please deposit a payment more than the amount you wish to withdraw.");
        }
        else throw new IllegalArgumentException(value);
    }

    void getPrevTransc(){
        String result = "";
        if (balance < 0) result = "Withdrawn $" + Math.abs(prevTransc);
        else if (balance > 0) result = "Deposited $" + Math.abs(prevTransc);
        else result = "No Transcation";
        System.out.println("You have "+result);
    }

    void menu(){
        
        Scanner scan = new Scanner(System.in);
        char val = '\0';
            
        while(val!= 'F'){
            options();
            val = scan.next().charAt(0); // to avoid infinite loop
              
            if (val == 'A') System.out.println("Your balance is $"+balance);
            else if (val == 'D') getPrevTransc();
            else if (val == 'E') {
                
                System.out.print("\nHow much is your annual interest in %?   ");
                double loanRate = scan.nextInt();
                System.out.println();
                System.out.print("How much do you want to borrow?  ");
                double principal = scan.nextInt();
                System.out.println();
                System.out.print("How much will you be paying on a monthly basis?  ");
                double payment = scan.nextInt();
                System.out.println();

                if (loanRate <= 0 || principal <= 0 || payment <=0) {
                    throw new IllegalArgumentException(value);
                }
                else getInterest(loanRate, principal, payment);

            }
            else if (val == 'F') break;
            else {

                System.out.print("Please enter an amount:  ");    
                int amount = scan.nextInt();
                if (val == 'B') deposit(amount);
                else if (val == 'C') withdraw(amount);

            }
        }
           
        System.out.println("Thank you for banking with Bank XYZ! Have a wonderful day & stay safe! :)\n");
        scan.close();
        
    }

     void options(){
        System.out.println("\nWeclome " + getName()+ " to Bank XYZ! Please choose from the following options!\n" );
        System.out.println("'A' - Check balance.\n'B' - Deposit\n'C' - Withdraw" );
        System.out.println("'D' - Get Previous Transcation.\n'E' - Loan Interest Calculator \n'F' - Logout.\n");
    }
}
