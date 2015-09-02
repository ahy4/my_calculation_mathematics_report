package calculation_report;

/**
 * Created by noko on 2015/09/03.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(
            new NewtonMethod(
                x -> (x.power(3)).minus(x.multiply(3)),
                x -> (x.power(2).multiply(3)).minus(r(3))
            ).newtonMethod(10)
        );
    }

    static Rational r(int n) {
        return r(n, 1);
    }
    static Rational r(int n, int d) {
        return new Rational(n, d);
    }
}
