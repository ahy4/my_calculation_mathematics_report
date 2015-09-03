package calculation_report;

import static calculation_report.Rational.ONE;
import static java.lang.Math.*;

import static calculation_report.Rational.r;

/**
 * Created by noko on 2015/09/03.
 */
public class Main {
    public static void main(String[] args) {
        NewtonMethod.newtonMethodWithDouble(
            // f(x) = x^2 + 3x + 2
            x -> x*x*x - 3*x,
            // f'(x) = 2x
            x -> 3*x*x - 3,
            1.7
        );
        /**
         * 絶対誤差: -7.47754*10^-7
         * 相対誤差: -4.31716*10^-7
         */

        NewtonMethod.newtonMethod(
            x -> x.power(3).minus(x.multiply(3)),
            x -> x.power(2).multiply(3).minus(r(3)),
            1.7
        );
        /**
         * 絶対誤差: -7.47753885429800410529115329528...*10^-7
         * 相対誤差: -4.31715907040483849824607517932...*10^-7
         */
    }
    public static void testClient01() {
        NewtonMethod.newtonMethod(
            // f(x) = log(1+x) - 1
            x -> myLog(x).minus(r(1)),
            // f'(x) = 1 / (1+x)
            x -> myLogPrime(x),
            0
        );
    }
    public static void testClient02() {
        NewtonMethod.newtonMethod(
            // f(x) = log(1+x) - 1
            x -> r(myLog(x).minus(r(1)).toDouble()),
            // f'(x) = 1 / (1+x)
            x -> r((myLogPrime(x)).toDouble()),
            0
        );
    }

    public static long factorial(long n) {
        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
//        return n == 0 ? 1 : n * factorial(n-1);
    }

    public static Rational sum(NumericalSequence nth) {
        Rational sum = Rational.ZERO;
        for (int i = 1; i <= 3; i++) {
            sum = sum.plus(nth.apply(i));
        }
        return sum;
    }

    // return log(1 + x) (= sum((-1)^(n+1) * x^n / n) )
    public static Rational myLog(Rational x) {
        return sum(
            n -> x.power(n)
                .multiply(r(
                    (long) pow(-1, n + 1),
                    n
                ))
        );
    }

    // return 1 / (1 + x)
    public static Rational myLogPrime(Rational x) {
        return x.plus(ONE).inverse();
    }

    public static Rational sin(Rational x) {
        return sum(i -> x.power(2 * i - 1)
            .multiply(r((long) pow(-1, i + 1), factorial(2 * i - 1))));
    }

    public static Rational cos(Rational x) {
        return sum(i -> x.power(2 * i - 2)
            .multiply(r((long) pow(-1, i + 1), factorial(2 * i - 2))));
    }


    interface NumericalSequence {
        Rational apply(long n);
    }

}