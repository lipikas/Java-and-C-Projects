public class StringEvaluator {
    public static String encode (String unchanged)  {
       int sum =1;
       String b="";
       int d = unchanged.length();
       for (int i=0; i < d - 1; i++) { // runs based on amount of length
            if (unchanged.charAt(i) == unchanged.charAt(i+1) sum +=1; // finds character
            else if (sum>1) b = b+sum+unchanged.charAt(i);
            else b = b+unchanged.charAt(i);
            sum = 1;
        }
        if (d ==1 || d ==0) b=unchanged;
        else if (unchanged.charAt(d-1)!=unchanged.charAt(d-2)) b += unchanged.charAt(d-1);
        else b = b+sum+unchanged.charAt(d-1);
        return b;
     }

    public static String decode (String unchanged)  {
        if ((unchanged.length() ==1)||(unchanged.length() ==0)) return unchanged;
        if (Character.isDigit(unchanged.charAt(0))){
            char c = unchanged.charAt(0);
                if (c=='0') return decode(unchanged.substring(2));
                else { c--; return unchanged.charAt(1) + decode(c +unchanged.substring(1));}
        }    
        else return unchanged.charAt(0)+decode(unchanged.substring(1));
     }
    public static void main(String[] args) {
    System.out.println(encode("56789vbnmmmmm775"));
    }
}
