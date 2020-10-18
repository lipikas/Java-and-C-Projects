package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	 public static Node add(Node poly1, Node poly2)  {
	        /** COMPLETE THIS METHOD **/
	        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
	        // CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
	        
	        Node ptr1 = poly1;
	        Node ptr2 = poly2;
	        Node ptr = new Node (0.0f, 0, null);
	        Node front = ptr;
	        
	        if (ptr1 == null && ptr2 == null) {
	            
	        	return null;
	        }
	        else if (ptr1 == null)
	        {
	            while(ptr2!=null) {
	                if(ptr2.term.coeff ==0) {
	                    ptr2 = ptr2.next;
	                    break;
	                }
	                
	                ptr.next=new Node(ptr2.term.coeff, ptr2.term.degree, null);
	                ptr=ptr.next;
	                ptr2 = ptr2.next;
	                
	            }
	            return front.next;
	        }
	        else if (ptr2 == null)
	        {
	            while(ptr1!=null) {
	                if(ptr1.term.coeff ==0) {
	                    ptr1 = ptr1.next;
	                    break;
	                }
	                ptr.next=new Node(ptr1.term.coeff, ptr1.term.degree, null);
	                ptr=ptr.next;
	                ptr1 = ptr1.next;
	            }
	            return front.next;
	        }
	        
	            
	        
	        while (ptr1!=null && ptr2 !=null)
	        {
	            if (ptr1.term.coeff == 0) {
	                ptr1 = ptr1.next;
	                continue;
	            }
	            else if (ptr2.term.coeff == 0 ) {
	                ptr2 = ptr2.next;
	                continue;
	            }
	            
	            
	            if (ptr1.term.degree > ptr2.term.degree) {
	                ptr.next = new Node (ptr2.term.coeff, ptr2.term.degree, null);
	                ptr = ptr.next;
	                ptr2 = ptr2.next;
	                
	                if (ptr2 == null) {
	                    ptr.next = new Node (ptr1.term.coeff, ptr1.term.degree, null);
	                    ptr = ptr.next;
	                    ptr1 = ptr1.next;
	                }
	            }
	            else if(ptr1.term.degree < ptr2.term.degree) {
	                
	            
	                ptr.next = new Node (ptr1.term.coeff, ptr1.term.degree, null);
	                ptr = ptr.next;
	                ptr1 = ptr1.next;
	                
	                if (ptr1 == null) {
	                    ptr.next = new Node (ptr2.term.coeff, ptr2.term.degree, null);
	                    ptr = ptr.next;
	                    ptr2 = ptr2.next;
	                }
	            }
	            else
	            {    
	                float a = ptr1.term.coeff + ptr2.term.coeff;
	                if(front.next == null && a == 0.0 && ptr1.next == null && ptr2.next == null) return null;
	                    
	                if ( a==0.0) {
	                    ptr1 = ptr1.next;
	                    ptr2 = ptr2.next;
	                    continue;
	                }
	                
	                ptr.next = new Node (a, ptr1.term.degree, null);
	                ptr = ptr.next;
	                ptr1 = ptr1.next;
	                ptr2 = ptr2.next;
	            }
	        }
	        while (ptr1!= null) {
	            
	            if (ptr1.term.coeff == 0) {
	                ptr1 = ptr1.next;
	                continue;
	            }
	            ptr.next = new Node (ptr1.term.coeff, ptr1.term.degree, null);
	            ptr = ptr.next;
	            ptr1=ptr1.next;
	        }
	        while(ptr2!=null) {
	            
	            if (ptr2.term.coeff == 0) {
	                ptr2 = ptr2.next;
	                continue;
	            }
	            ptr.next = new Node (ptr2.term.coeff, ptr2.term.degree, null);
	            ptr = ptr.next;
	            ptr2=ptr2.next;
	            
	        }
	        
	        return front.next;
	    }
	    
	    /**
	     * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	     * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	     * of the input polynomials can be in the result.
	     * 
	     * @param poly1 First input polynomial (front of polynomial linked list)
	     * @param poly2 Second input polynomial (front of polynomial linked list)
	     * @return A new polynomial which is the product of the input polynomials - the returned node
	     *         is the front of the result polynomial
	     */
	    public static Node multiply(Node poly1, Node poly2) {
	        /** COMPLETE THIS METHOD **/
	        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
	        // CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
	        
	        Node ptr1 = poly1;
	        Node ptr2 = poly2;
	        Node ptr = new Node (0.0f, 0, null);
	        
	        float pro;
	        int sum;
	        if (poly1 == null || poly2 == null) return null;
	        int max = 0;
	        while(ptr1 != null)
	        {
	            while(ptr2 != null) {
	                pro = ptr1.term.coeff*ptr2.term.coeff;
	                sum = ptr1.term.degree + ptr2.term.degree;
	                ptr = new Node (pro, sum, ptr);
	                
	                if ( sum > max) 
	                    max = sum;
	                ptr2=ptr2.next;
	                }
	            
	                ptr1=ptr1.next;
	                ptr2=poly2;
	        }
	        
	        Node pop = new Node (0.0f, 0, null);
	        
	        for ( int i = 0; i <= max; i++) {
	            Node temp = ptr;
	            float Nsum =0;
	            
	            while (temp != null) {
	            if (temp.term.degree == i) 
	                Nsum += temp.term.coeff;
	            temp = temp.next;
	            }
	            if(Nsum!=0) 
	                pop = new Node(Nsum, i, pop);
	            
	        }
	        
	        Node ptr3 = pop;
	        Node prev = null;
	        Node curr = null;
	        
	        while(ptr3 != null) {
	            curr = ptr3;
	            ptr3=ptr3.next;
	            curr.next = prev;
	            prev = curr;
	            pop = curr;
	        }
	        
	        return pop.next; 
	    }
	        
	    /**
	     * Evaluates a polynomial at a given value.
	     * 
	     * @param poly Polynomial (front of linked list) to be evaluated
	     * @param x Value at which evaluation is to be done
	     * @return Value of polynomial p at x
	     */
	    public static float evaluate(Node poly, float x) {
	        /** COMPLETE THIS METHOD **/
	        // FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
	        // CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
	        Node ptr=poly;
	        
	        if (ptr == null) return 0.0f;
	        
	        float sum = 0;
	        while (ptr!=null)
	        {
	            sum += ((float)(Math.pow(x, ptr.term.degree))*ptr.term.coeff);
	            ptr = ptr.next;
	        }
	    
	            return sum;
	    }
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
	
	
	
}
