package calculation_report

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by noko on 2015/09/09.
 */

public class BisectionMethod {

    private static int maxIteration = 40;

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
            throw new RuntimeException(e);
        }

        for (int i = 0; isOkCondition(i); i++) {
            middle = (leftBound + rightBound) / two;
            println middle
            if (f.apply(leftBound) * f.apply(middle) >= zero) {
                leftBound = middle;
            } else {
                rightBound = middle;
            }
        }
    }

    private static boolean isOkCondition(int i) {
        return i < maxIteration;
    }

    interface Clojure<T> {
        T apply(T x);
    }

}
