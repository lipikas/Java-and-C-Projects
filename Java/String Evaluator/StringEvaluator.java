/*************************************************************************
 *  Compilation:  javac StringEvaluator.java
 *  Execution:    java StringEvaluator
 *
 *  @author: Lipika Sutrave;ls1175@rutgers.edu; ls1175
 *
 *************************************************************************/

public class StringEvaluator {

    /* 
     * Encode the original string by finding sequences in the string
     * where the same character repeats. Replace each such sequence
     * by a token consisting of: the number of characters in the sequence
     * followed by the repeating character.
     * Return the encoded string.
     */
    public static String encode (String original)  {
       int sum =1;
       String b="";
       int d = original.length();
       for (int i=0; i < d - 1; i++) { // runs based on amount of length
            if (original.charAt(i) == original.charAt(i+1) sum +=1; // finds character
            else if (sum>1) b = b+sum+original.charAt(i);
            else b = b+original.charAt(i);
            sum = 1;
        }
        if (d ==1 || d ==0) b=original;
        else if (original.charAt(d-1)!=original.charAt(d-2)) b += original.charAt(d-1);
        else b = b+sum+original.charAt(d-1);
        return b;
     }

    public static String decode (String original)  {
        if ((original.length() ==1)||(original.length() ==0)) return original;
        if (Character.isDigit(original.charAt(0))){
            char c = original.charAt(0);
                if (c=='0') return decode(original.substring(2));
                else { c--; return original.charAt(1) + decode(c +original.substring(1));}
        }    
        else return original.charAt(0)+decode(original.substring(1));
     }
    public static void main(String[] args) {
    //StdOut.println(encode("eeeugnkghdge"));
    //StdOut.println(decode("2aa5bc"));
    }
}
