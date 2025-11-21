package functions.basic;

import functions.Function;

public class Log implements Function {
    private double basis;
    // Проверка основания
    public Log(double basis){
        if (basis <= 0 || basis == 1){
            throw new IllegalArgumentException("Основание логарифма должно быть положительным и не равным 1");
        }

        this.basis = basis;
    }
    public double getLeftDomainBorder(){
        return 0 + 1e-10; // Логарифм определен для x > 0
    }
    public double getRightDomainBorder(){
        return Double.POSITIVE_INFINITY;
    }
    public double getFunctionValue(double x) {
        if (x <= 0){
            return Double.NaN; // Логарифм не определен для неположительных X
        }
        return Math.log(x)/Math.log(basis); // log - это ln. По формуле: ln(x) / ln(a) = logₐ(x)
    }
}
