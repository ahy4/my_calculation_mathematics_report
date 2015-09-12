package calculation_report;

import java.math.BigDecimal;

/**
 * Created by noko on 2015/09/02.
 */
public class Rational implements Comparable<Rational> {

    private long num; // numerator
    private long den; // denominator

    public static final Rational ONE  = r(1);
    public static final Rational ZERO = r(0);
    public static final Rational NaN  = r(1, 0);

    /**
     * create a rational object.
     * [ numerator / denominator ]
     * @param  numerator    fraction's child number
     * @param  denominator  fraction's mother number
     */
    public Rational(long numerator, long denominator) {
        long g = gcd(numerator, denominator);
        if (g == 0) g = 1;
        this.num = numerator / g;
        this.den = denominator / g;
        if (den < 0) { num *= -1; den *= -1; }
    }

    /**
     * create a rational object.
     * [ num / 1 ]
     * @param  num value of this rational object
     */
    public Rational(long num) {
        this(num, 1);
    }

    /**
     * create a rational object parsed by double value.
     * ex.
     *   d = 12.33 -> [ 1233 / 100 ]
     *   d = 12.34 -> [ 617 / 50 ]
     * @param  d value of this rational object
     */
    public Rational(double d) {
        // call to this() must be first statement
        this(((Block<Rational>) () ->
            double2Rational(d)
        ).body());
    }
    private static Rational double2Rational(double d) {
        if (Double.isNaN(d)) return NaN;
        String[] numstrs = String.valueOf(d).split("[.]");
        String integer = numstrs[0].equals("-0") ? "-" : numstrs[0].equals("0") ? "": numstrs[0];
        String decimal = numstrs[1].equals("0") ? "" : numstrs[1];
        long wholeVal;
        wholeVal = Long.parseLong((integer + decimal).equals("") ? "0" : integer + decimal);
        long decimalDigits = (long) Math.pow(10, decimal.length());
        return r(wholeVal, decimalDigits);
    }

    /**
     * clone a rational object.
     * [ num / 1 ]
     * @param  rational rational object
     */
    public Rational(Rational rational) {
        this(rational.num, rational.den);
    }

    // return gcd(|x|, |y|)
    private static long gcd(long x, long y) {
        return y == 0 ? Math.abs(x) : gcd(y, x % y);
    }
    // return lcm(|x|, |y|)
    private static long lcm(long x, long y) {
        return Math.abs(x * y) / gcd(x, y); // g * l = x * y
    }

    /**
     * @return -rational
     */
    public Rational negative() {
        return r(-this.num, this.den);
    }

    /**
     * @return 1 / rational
     */
    public Rational inverse() {
        return r(this.den, this.num);
    }

    /**
     * add both rational objects. staving off overflow
     * @param  b other rational object
     * @return a + b (a: this, b: other)
     */
    public Rational plus(Rational b) {
        Rational a = this;

        if (a.equals(ZERO)) return b;
        if (b.equals(ZERO)) return a;

        long f = gcd(a.num, b.num);
        long g = gcd(a.den, b.den);
        if (f == 0 || g == 0) return r (
            a.num * b.den + b.num * a.den,
            a.den * b.den
        );

        Rational s = r (
            (a.num / f) * (b.den / g) + (b.num / f) * (a.den / g),
            lcm(a.den, b.den)
        );

        s.num *= f;
        return s;
    }

    /**
     * subtract both rational objects
     * @param  other rational object
     * @return this - other
     */
    public Rational minus(Rational other) {
        return this.plus(other.negative());
    }

    /**
     * multiply both rational objects. staving off overflow as much as possible by cross-cancellation
     * @param  b other rational object
     * @return a * b (a: this, b: other)
     */
    public Rational multiply(Rational b) {
        Rational a = this;
        Rational c = new Rational(a.num, b.den);
        Rational d = new Rational(b.num, a.den);
        return new Rational(c.num * d.num, c.den * d.den);
    }

    /**
     * multiply with integer value
     * @param  times multiply num
     * @return n * x (n: times, x: this)
     */
    public Rational multiply(long times) {
        return this.multiply(new Rational(times));
    }

    /**
     * divide both rational objects
     * @param  other rational object
     * @return a / b (a: this, b: other)
     */
    public Rational div(Rational other) {
        if (other.equals(ZERO)) return NaN;
        return multiply(other.inverse());
    }

    /**
     * divide with integer value
     * @param  times divide num
     * @return x / n (n: times, x: this)
     */    public Rational div(long times) {
        return this.div(r(times));
    }

    /**
     * multiply n times
     * @param  times multiply times
     * @return x ^ n (n: times, x: this)
     */
    public Rational power(long times) {
        Rational pow = Rational.ONE;
        for (int i = 0; i < times; i++) {
            pow = pow.multiply(this);
        }
        return pow;
//        return times == 1 ? this : this.multiply(power(times-1));
    }

    /**
     * compare this and other value.
     * need for java
     * @param  b other val
     * @return -1, 0, 1 (LessThan, Equal, GreaterThan)
     */
    public int compareTo(Rational b) {
        Rational a = this;
        long l = a.den * b.num,
            r = a.num * b.den;
        return l < r ? +1 : l > r ? -1 : 0;
    }

    /**
     * check less or not
     * @param  other compare val
     * @return true / false (yes, no)
     */
    public boolean lessThan(Rational other) {
        return compareTo(other) == +1;
    }

    /**
     * check greater or not
     * @param  other compare val
     * @return true / false (yes, no)
     */
    public boolean greaterThan(Rational other) {
        return compareTo(other) == -1;
    }

    /**
     * compares this rational with the specified Object for equality.
     * @param  y compare val
     * @return true / false (yes, no)
     *         {@code true} if and only if the specified Object is a
     *         Rational whose value is numerically equal to this Rational.
     */
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Rational b = (Rational) y;
        return compareTo(b) == 0;
    }

    /**
     * need for java compiler
     * @return hashcode
     */
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * returns a string object representing this rational value
     * @return rational string.
     *         ex. [ 1 / 2 ] -> "1 / 2"
     */
    public String toString() {
        return den == 1
            ? this.num + ""
            : this.den == 0
            ? "NaN"
            : this.num + " / " + this.den;
    }

    /**
     * returns a double object representing this rational value
     * @return double value by rational.
     *         ex. [ 1 / 2 ] -> 0.5
     */
    public double toDouble() {
        return (double) this.num / this.den;
    }

    /**
     * returns a double object representing this rational value
     * @return double value by rational.
     *         ex. [ 1 / 2 ] -> 0.5
     */
    public BigDecimal toBigDecimal() {
        return (new BigDecimal(num)).divide(new BigDecimal(den), 200, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * getter for this.num
     * @return this.num
     */
    public long numerator() { return this.num; }

    /**
     * getter for this.den
     * @return this.den
     */
    public long denominator() { return this.den; }

    /**
     * [shortcut] create a rational object (factory method)
     * @param  d double value
     * @return new rational object by argument
     */
    public static Rational r(double d) {
        return new Rational(d);
    }

    /**
     * [shortcut] create a rational object (factory method)
     * @param  n integer value
     * @return [ n / 1 ]
     */
    public static Rational r(long n) {
        return r(n, 1);
    }

    /**
     * [shortcut] create a rational object (factory method)
     * @param  n child  value
     * @param  d mother value
     * @return [ n / d ]
     */
    public static Rational r(long n, long d) {
        return new Rational(n, d);
    }

    interface Block<T> {
        T body();
    }
}