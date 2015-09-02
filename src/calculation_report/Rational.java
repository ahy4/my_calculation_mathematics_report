package calculation_report;

/**
 * Created by noko on 2015/09/02.
 */
public class Rational {

    private int num; // numerator
    private int den; // denominator

    public Rational(int numerator, int denominator) {
        int g = gcd(numerator, denominator);
        this.num = numerator / g;
        this.den = denominator / g;
        if (den < 0) { num *= -1; den *= -1; }
    }

    private static int gcd(int x, int y) {
        return y == 0 ? Math.abs(x) : gcd(y, x % y);
    }

    public Rational negative() {
        return new Rational(-this.num, this.den);
    }
    public Rational inverse() {
        return new Rational(this.den, this.num);
    }

    public Rational plus(Rational other) {
        return new Rational(
            this.num * other.den + other.num * this.den,
            this.den * other.den
        );
    }
    public Rational minus(Rational other) {
        return this.plus(other.negative());
    }
    public Rational multiply(Rational other) {
        return new Rational(
            this.num * other.num,
            this.den * other.den
        );
    }
    public Rational div(Rational other) {
        return multiply(other.inverse());
    }

    // return -1, 0, +1
    public int compareTo(Rational b) {
        Rational a = this;
        int l = a.den * b.num,
            r = a.num * b.den;
        return l < r ? +1 : l > r ? -1 : 0;
    }
    public boolean lessThan(Rational other) {
        return compareTo(other) == +1;
    }
    public boolean greaterThan(Rational other) {
        return compareTo(other) == -1;
    }
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Rational b = (Rational) y;
        return compareTo(b) == 0;
    }
    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toString() {
        return den == 1
            ? this.num + ""
            : this.num + "/" + this.den;
    }
    public double toDouble() {
        return (double) this.num / this.den;
    }

    public int numerator() { return this.num; }
    public int denominator() { return this.den; }

}