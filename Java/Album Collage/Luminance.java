import java.awt.Color;

public class Luminance {
  
    public static double lum(Color color) {
        return intensity(color);
    }

    public static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

  
    public static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

  
    public static boolean areCompatible(Color a, Color b) {
        return Math.abs(intensity(a) - intensity(b)) >= 128.0;
    }

    public static void main(String[] args) {
        int[] a = new int[6];
        for (int i = 0; i < 6; i++) {
            a[i] = Integer.parseInt(args[i]);
        }
        Color c1 = new Color(a[0], a[1], a[2]);
        Color c2 = new Color(a[3], a[4], a[5]);
        Color c3 = Luminance.toGray(c1);
        StdOut.println("c1 = " + c1);
        StdOut.println("c2 = " + c2);
        StdOut.println("c3 = " + c3);
        StdOut.println("intensity(c1) =  " + intensity(c1));
        StdOut.println("intensity(c2) =  " + intensity(c2));
        StdOut.println("intensity(c3) =  " + intensity(c3));
        StdOut.println(areCompatible(c1, c2));
    }
}
