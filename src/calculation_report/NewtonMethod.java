package calculation_report;

import static calculation_report.Rational.r;

/**
 * Created by noko on 2015/09/03.
 */

public class NewtonMethod {

    private Clojure f;
    private Clojure fPrime;
    private Rational epsilon = r(1, (int)Math.pow(2, 52));
    private int maxIteration = 4;
    private Rational initialX;
    private Rational result = Rational.NaN;

    public NewtonMethod(Clojure f, Clojure fPrime) {
        this.f = f;
        this.fPrime = fPrime;
    }

    public Rational newtonMethod(int initialX) {
        Rational x = r(initialX);
        for (int i = 0; isOkCondition(x, i); i++) {
            System.out.println(x);
            x = x.minus(f.apply(x).div(fPrime.apply(x)));
        }
        result = x;
        return x;
    }
    private boolean isOkCondition(Rational x, int i) {
        return i < maxIteration;
    }

    public String toString() {
        return result.toString();
    }
    public double toDouble() {
        return result.toDouble();
    }
    public Rational result() {
        return result;
    }

    interface Clojure {
        Rational apply(Rational x);
    }

}
