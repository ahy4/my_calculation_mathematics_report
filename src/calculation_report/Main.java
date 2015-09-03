package calculation_report;

import groovy.lang.Closure;

import static calculation_report.Rational.ONE;
import static java.lang.Math.*;

import java.util.Arrays;
import java.util.stream.IntStream;

import static calculation_report.Rational.r;

/**
 * Created by noko on 2015/09/03.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(
            new NewtonMethod(
                // sin(x) (taylor)
                x -> sin(x).plus(r(1)),
                // cos(x) (taylor)
                x -> cos(x)
            ).newtonMethod(1)
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
        for (int i = 1; i <= 8; i++) {
            sum = sum.plus(nth.apply(i));
        }
        return sum;
    }

    // return log(1 + x)
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