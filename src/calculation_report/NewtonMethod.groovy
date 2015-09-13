package calculation_report;

/**
 * Created by noko on 2015/09/03.
 */

public class NewtonMethod {

    /**
     * it is max iteration count for newton method
     */
    private static int maxIteration = 4

    /**
     * setter for maxIteration
     * @param {int} n  iteration max count
     */
    public static void maxIteration(int n) {
        maxIteration = n
    }

    /**
     * show newton method's results come by `f(x) = 0`.
     *
     * sample: (java8)
     *     NewtonMethod.newtonMethod(
     *         x -> Math.sin(x) -   Math.sin(2*x),
     *         x -> Math.cos(x) - 2*Math.cos(2*x),
     *         Math.PI / 2
     *     );
     *
     * @param {Clojure<T>} f         f(x). please use lambda expression(^java8) or closure(groovy) etc
     * @param {Clojure<T>} fPrime    f'(x). please use lambda expression(^java8) or closure(groovy) etc
     * @param {T}          initialX  start x point. please use proper type that you want to use
     */
    public static <T> void newtonMethod(Clojure<T> f, Clojure<T> fPrime, T initialX) {
        T x = initialX

        println ""
        println "| 試行回数 | 相対誤差の桁数 | 使用データ数 | 近似値 | 相対誤差 |"
        println "|:-|:-|:-|"
        (1..maxIteration).forEach {
            x = x - f.apply(x) / fPrime.apply(x)
//            println "NewtonMethod |  times: " + it + ", " + "answer: " + x + ", RelativeError: " + AccurateNumber.relativeErrorWithSqrt3(x)
            println(
                "| " + it + " | " +
                ((int)(Math.abs(Math.log10(AccurateNumber.relativeErrorWithSqrt3(x).toDouble())))) + " | " +
                (x.toString().replaceAll("[ ][/][ ]", "").replaceAll("[.]", "").length()) + " | " +
                x + " | " +
                AccurateNumber.relativeErrorWithSqrt3(x) + " |"
            )

        }
        println ""
    }

    interface Clojure<T> {
        T apply(T x);
    }

}
