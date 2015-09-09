package calculation_report;

/**
 * Created by noko on 2015/09/03.
 */
public class BigRational implements Comparable<BigRational> {

    private BigInteger num // numerator
    private BigInteger den // denominator

    public static BigRational ONE  = br 1
    public static BigRational ZERO = br 0
    public static BigRational NaN  = br 1, 0

    public BigRational(BigInteger numerator, BigInteger denominator) {
        BigInteger g = gcd numerator, denominator
        if (isZero(g)) g = 1
        this.num = numerator.divide(g)
        this.den = denominator.divide(g)
        if (den < 0) { num *= -1; den *= -1; }
    }
    public BigRational(BigRational bigRational) {
        this(bigRational.num, bigRational.den);
    }
    public BigRational(double d) {
        // call to this() must be first statement
        this({
            if (Double.isNaN(d)) return NaN;
            String[] numstrs = String.valueOf(d).split("[.]");
            String integer = numstrs[0].equals("-0") ? "-" : numstrs[0].equals("0") ? "" : numstrs[0];
            String decimal = numstrs[1].equals("0") ? "" : numstrs[1];
            BigInteger wholeVal;
            wholeVal = ((integer + decimal).equals("") ? "0" : integer + decimal).toBigInteger();
            BigInteger decimalDigits = new BigDecimal(Math.pow(10, decimal.length())).toBigInteger();
            return br(wholeVal, decimalDigits);
        }())
    }

    private static BigInteger gcd(BigInteger x, BigInteger y) {
        BigInteger r;
        while (new BigInteger(0) != y && BigInteger.ZERO != y) {
            r = x % y
            x = y
            y = r
        }
        return x
    }
    private static boolean isZero(BigInteger n) {
        n == 0G || n == new BigInteger(0)
    }

    public BigRational negative() {
        br(-this.num, this.den)
    }
    public BigRational inverse() {
        br this.den, this.num
    }

    public BigRational plus(BigRational other) {
        br (
            (this.num * other.den).add(other.num * this.den),
            this.den * other.den
        )
    }
    public BigRational minus(BigRational other) {
        this.plus(other.negative());
    }
    public BigRational multiply(BigRational other) {
        br (
            this.num * other.num,
            this.den * other.den
        )
    }
    public BigRational multiply(int times) {
        this.multiply(br(times))
    }
    public BigRational div(BigRational other) {
        multiply(other.inverse())
    }
    public BigRational div(int times) {
        this.div(br(times))
    }
    public BigRational power(int times) {
        times == 1 ? this : this.multiply(power(times-1))
    }

    // return -1, 0, +1
    public int compareTo(BigRational b) {
        BigRational a = this;
        BigInteger l = a.den * b.num,
                   r = a.num * b.den;
        l < r ? +1 : l > r ? -1 : 0
    }
    public boolean lessThan(BigRational other) {
        compareTo(other) == +1
    }
    public boolean greaterThan(BigRational other) {
        compareTo(other) == -1
    }
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        BigRational b = (BigRational) y;
        compareTo(b) == 0
    }
    public int hashCode() {
        this.toString().hashCode()
    }

    public String toString() {
        this.den == BigInteger.ONE ? this.num.toString() : this.den == BigInteger.ZERO ? "NaN" : this.num + " / " + this.den
    }
    public double toDouble() {
        (double) this.num.divide(this.den)
    }

    public BigInteger numerator() { this.num; }
    public BigInteger denominator() { this.den; }

    public static BigRational br(double d) {
        return new BigRational(d);
    }
    public static BigRational br(int n) {
        br n, 1
    }
    public static BigRational br(BigInteger n, BigInteger d) {
        new BigRational(n, d)
    }
    public static BigRational br(int n, int d) {
        br(new BigInteger(n), new BigInteger(d))
    }

}