package calculation_report;

import java.math.BigDecimal;

import static java.lang.Math.*;

import static calculation_report.Rational.r;
import static calculation_report.BigRational.br;

/**
 * Created by noko on 2015/09/03.
 */
public class Main {

    public static void main(String[] args) {
        testClient04();
    }


    public static void testClient01() {
        NewtonMethod.newtonMethod(
            // f(x) = log(1+x) - 1
            x -> myLog(x).minus(br(1)),
            // f'(x) = 1 / (1+x)
            x -> myLogPrime(x),
            br(0)
        );
    }
    public static void testClient02() {
        NewtonMethod.newtonMethod(
            // f(x) = log(1+x) - 1
            x -> br(myLog(x).minus(br(1)).toDouble()),
            // f'(x) = 1 / (1+x)
            x -> br((myLogPrime(x)).toDouble()),
            br(0)
        );
    }

    public static void testClient03() {
        System.out.println("［Rationalクラスによる結果］");
        NewtonMethod.newtonMethod(
            x -> x.power(3).minus(x.multiply(3)),
            x -> x.power(2).multiply(3).minus(r(3)),
            r(1.7)
        );

        System.out.println("［double型による結果］");
        NewtonMethod.newtonMethod(
            // f(x) = x^2 + 3x + 2
            x -> x * x * x - 3 * x,
            // f'(x) = 2x
            x -> 3 * x * x - 3,
            1.7
        );

        System.out.println("［BigRationalクラスによる結果］");
        NewtonMethod.newtonMethod(
            x -> x.power(3).minus(x.multiply(3)),
            x -> x.power(2).multiply(3).minus(br(3)),
            br(1.7)
        );

        System.out.println("［BigIntegerクラスによる結果］");
        NewtonMethod.newtonMethod(
            x -> x.pow(3).subtract(x.multiply(new BigDecimal(3))),
            x -> x.pow(2).multiply(new BigDecimal(3)).subtract(new BigDecimal(3)),
            new BigDecimal(1.7)
        );
    }

    public static void testClient05() {
        BisectionMethod.execute(
            BigRational.class,
            // f(x) = log(1+x) - 1
            x -> myLog(x).minus(br(1)),
            1, 2
        );
    }

    public static void testClient04() {
        System.out.println("［BigRationalクラスによる結果］");
        BisectionMethod.execute(
            BigRational.class,
            x -> x.power(3).minus(x.multiply(3)),
            1, 2
        );

        System.out.println("［BigDecimalクラスによる結果］");
        BisectionMethod.execute(
            BigDecimal.class,
            x -> x.pow(3).subtract(x.multiply(new BigDecimal(3))),
            1, 2
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

    public static BigRational sum(NumericalSequence nth) {
        BigRational sum = BigRational.ZERO;
        for (int i = 1; i <= 8; i++) {
            sum = sum.plus(nth.apply(i));
        }
        return sum;
    }

    // return log(1 + x) (= sum((-1)^(n+1) * x^n / n) )
    public static BigRational myLog(BigRational x) {
        return sum ( n ->
                x.power(n)
                    .multiply(br(
                        n % 2 == 0 ? -1 : +1, // (-1)^(n+1), (int) pow(-1, n + 1)
                        n
                    ))
        );
    }

    // return 1 / (1 + x)
    public static BigRational myLogPrime(BigRational x) {
        return x.plus(BigRational.ONE).inverse();
    }

    interface NumericalSequence {
        BigRational apply(int n);
    }

}