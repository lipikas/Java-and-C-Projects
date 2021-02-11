package practice.src;

public class Interest{
    
    double rateI;
    double principal;
    private double time;
    private double interestAmount;

   void getInterest(double rate, double prin, double payment){
        this.rateI = rate;
        rate /= (12*100);
        this.principal = prin; 
        double amount = prin;
        this.time = 0;

        while (amount >= 0){
            if (amount*(1+rate) < payment){
                amount = 0;
                this.interestAmount += payment - amount*(1+rate);
                break;
            }
            amount *= (1+rate);
            amount -= payment;
            this.time++;
            this.interestAmount+=payment;
        }
        interestAmount -= prin;
        System.out.println(toString());
   }

   double getTime(){
        return time;
   }
   double getInterestAmount(){
    return interestAmount;
    }

  public String toString(){ // meth overrriding 
    return "\nFor your interest rate " + rateI + "% and based on info inputted, it will take " + getTime() + " months or "+getTime()/12+
    " years to pay off your debt. Also you will be paying extra $"+ getInterestAmount() + " in interest.";
    }
    
}
