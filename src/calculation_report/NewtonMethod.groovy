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

//    public static Rational newtonMethod(Clojure<Rational> f, Clojure<Rational> fPrime, double initialX) {
//        Rational x = r(initialX);
//        for (int i = 0; isOkCondition(i); i++) {
//            System.out.println(x);
//            x = x.minus(f.apply(x).div(fPrime.apply(x)));
//        }
//        return x;
//    }
//    public static double newtonMethodWithDouble(Clojure<Double> g, Clojure<Double> gPrime, double initialX) {
//        double x = new Double(initialX);
//        for (int i = 0; isOkCondition(i); i++) {
//            System.out.println(x);
//            x = x - g.apply(x) / gPrime.apply(x);
//        }
//        return x;
//    }
    public static <T> void newtonMethod(Clojure<T> f, Clojure<T> fPrime, T initialX) {
        T x = initialX;
        for (int i = 0; isOkCondition(i); i++) {
            println x
            x = x - f.apply(x) / fPrime.apply(x);
        }
    }

    private static boolean isOkCondition(int i) {
        return i < maxIteration;
    }

    interface Clojure<T> {
        T apply(T x);
    }

}
