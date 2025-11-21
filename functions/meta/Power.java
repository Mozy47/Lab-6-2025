package functions.meta;

import functions.Function; 

public class Power implements Function {
    private Function f;
    private double power; // степень

    public Power(Function f, double power){
        this.f = f;
        this.power = power;
    }
    public double getLeftDomainBorder() {
        return f.getLeftDomainBorder();
    }
    public double getRightDomainBorder() {
        return f.getRightDomainBorder();
    }
    public double getFunctionValue(double x){
        // Проверяем, что x принадлежит области определения
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }
        
        double baseValue = f.getFunctionValue(x);
        if (Double.isNaN(baseValue)) {
            return Double.NaN;
        }
        return Math.pow(baseValue, power);
    }
}
