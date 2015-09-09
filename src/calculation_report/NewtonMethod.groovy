package calculation_report;

/**
 * Created by noko on 2015/09/03.
 */

public class NewtonMethod {

    private static int maxIteration = 10;

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
