package calculation_report


/**
 * Created by noko on 2015/09/09.
 */

public class BisectionMethod {

    /**
     * it is max iteration count for bisection method
     */
    private static int maxIteration = 100;

    /**
     * setter for maxIteration
     * @param  n  iteration max count
     */
    public static void maxIteration(int n) {
        maxIteration = n;
    }

    /**
     * show bisection method's results come by `f(x) = 0`.
     * search an answer between params.left and params.right
     *
     * sample: (java8)
     *     BisectionMethod.execute(
     *         Double.class,
     *         x -> Math.sin(x) - Math.sin(2*x),
     *         0, Math.PI / 2
     *     );
     *
     * @param {Class<T>}   klass it is come by java language problem. sorry. (we cannot use `new T(2)` with generics)
     *                           please input [Type.class] that you want to use
     * @param {Clojure<T>} f     f(x). please use lambda expression(^java8) or closure(groovy) etc
     * @param {double}     left  start leftX_0  point
     * @param {double}     right start rightX_0 point (leftX_0 < rightX_0)
     */
    public static <T> void execute(Class<T> klass, Clojure<T> f, double left, double right) {
        T middle
        T zero, two
        T leftBound, rightBound
        try {
            zero = klass.newInstance([0] as Object[]) // new T(0)
            two = klass.newInstance([2] as Object[]) // new T(2)
            leftBound = klass.newInstance([left] as Object[])
            rightBound = klass.newInstance([right] as Object[])
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e)
        }

        println ""
        println "| 試行回数 | 相対誤差の桁数 | 使用データ数 | 近似値 | 相対誤差 |"
        println "|:-|:-|:-|:-|"
        (1..maxIteration).forEach {
            middle = (leftBound + rightBound) / two
            if (f.apply(leftBound) * f.apply(middle) >= zero) {
                leftBound = middle
            } else {
                rightBound = middle
            }
//            println "BisectionMethod |  times: " + it + ", " + "answer: " + middle
            if (it == 1 || it % 10 == 0) println(
                "| " + it + " | " +
                ((int)(Math.abs(Math.log10(AccurateNumber.relativeErrorWithSqrt3(middle).toDouble())))) + " | " +
                (middle.toString().replaceAll("[ ][/][ ]", "").replaceAll("[.]", "").length()) + " | " +
                middle + " | " +
                AccurateNumber.relativeErrorWithSqrt3(middle) + " |"
            )

        }
        println ""
    }

    interface Clojure<T> {
        T apply(T x)
    }

}
