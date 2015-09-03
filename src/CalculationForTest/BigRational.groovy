package calculation_report;

/**
 * Created by noko on 2015/09/03.
 */
public class BigRational implements Comparable<BigRational> {

    private int num // numerator
    private int den // denominator

    public static BigRational ONE  = r 1
    public static BigRational ZERO = r 0
    public static BigRational NaN  = r 1, 0

    public BigRational(int numerator, int denominator) {
        int g = gcd(numerator, denominator);
        this.num = numerator / g;
        this.den = denominator / g;
        if (den < 0) { num *= -1; den *= -1; }
    }

    private static int gcd(int x, int y) {
        return y == 0 ? Math.abs(x) : gcd(y, x % y);
    }

    public BigRational negative() {
        return r(-this.num, this.den);
    }
    public BigRational inverse() {
        return r(this.den, this.num);
    }

    public BigRational plus(BigRational other) {
        return r(
                this.num * other.den + other.num * this.den,
                this.den * other.den
        );
    }
    public BigRational minus(BigRational other) {
        return this.plus(other.negative());
    }
    public BigRational multiply(BigRational other) {
        return r(
                this.num * other.num,
                this.den * other.den
        );
    }
    public BigRational multiply(int times) {
        return this.multiply(new BigRational(times));
    }
    public BigRational div(BigRational other) {
        return multiply(other.inverse());
    }
    public BigRational power(int times) {
        return times == 1 ? this : this.multiply(power(times-1));
    }

    // return -1, 0, +1
    public int compareTo(BigRational b) {
        BigRational a = this;
        int l = a.den * b.num,
            r = a.num * b.den;
        return l < r ? +1 : l > r ? -1 : 0;
    }
    public boolean lessThan(BigRational other) {
        return compareTo(other) == +1;
    }
    public boolean greaterThan(BigRational other) {
        return compareTo(other) == -1;
    }
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        BigRational b = (BigRational) y;
        return compareTo(b) == 0;
    }
    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toString() {
        return den == 1 ? this.num.toString() : this.den == 0 ? "NaN" : this.num + " / " + this.den;
    }
    public double toDouble() {
        return (double) this.num / this.den;
    }

    public int numerator() { return this.num; }
    public int denominator() { return this.den; }

    public static BigRational r(int n) {
        return r(n, 1);
    }
    public static BigRational r(int n, int d) {
        return new BigRational(n, d);
    }

}