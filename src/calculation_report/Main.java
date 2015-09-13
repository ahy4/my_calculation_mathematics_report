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
        testClient03();
        testClient04();
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

}