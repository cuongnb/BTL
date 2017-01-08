package util;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.sqrt;

/**
 * Created by cuongnb on 08/01/2017.
 */
public class UtilMath {
    public static double NormalDistribution(double x, double mu, double sigma_2) {
        return (double) 1 / (sqrt(2 * sigma_2 * PI)) * exp(-(x - mu) * (x - mu) / (2 * sigma_2));
    }



    public static void main(String[] args) {
        System.out.println(NormalDistribution(1.0, 0.5, 0.25));
    }
}
