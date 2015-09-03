package calculation_report;

import static calculation_report.Rational.ZERO;
import static calculation_report.Rational.r;

/**
 * Created by noko on 2015/09/03.
 */

public class NewtonMethod {

    private static Rational epsilon = r(1, (int)Math.pow(2, 52));
    private static double eps = epsilon.toDouble();
    private static int maxIteration = 7;
    private static Rational initialX;

    public static Rational newtonMethod(Clojure f, Clojure fPrime, double initialX) {
        Rational x = r(initialX);
        for (int i = 0; isOkCondition(x, i); i++) {
            System.out.println(x);
            x = x.minus(f.apply(x).div(fPrime.apply(x)));
        }
        return x;
    }
    public static double newtonMethodWithDouble
        (DoubleClojure g, DoubleClojure gPrime, double initialX) {

        double x = initialX;
        for (int i = 0; isOkCondition(ZERO, i); i++) {
            System.out.println(x);
            x = x - g.apply(x) / gPrime.apply(x);
        }
        return x;
    }
    private static boolean isOkCondition(Rational x, int i) {
        return i < maxIteration;
    }

    interface Clojure {
        Rational apply(Rational x);
    }
    interface DoubleClojure {
        double apply(double x);
    }

}
