package functions.meta;

import functions.Function;

public class Shift implements Function {
    private Function f;
    private double shiftX;
    private double shiftY;

    public Shift(Function f, double shiftX, double shiftY){
        this.f = f;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }
    
    public double getLeftDomainBorder() {
        // Сдвигаем область определения по X
        return f.getLeftDomainBorder() - shiftX;
    }
    public double getRightDomainBorder() {
        // Сдвигаем область определения по X
        return f.getRightDomainBorder() - shiftX;
    }

    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }
        // Масштабируем x для исходной функции
        double shiftedX = x - shiftX;
        double originalValue = f.getFunctionValue(shiftedX);
        if (Double.isNaN(originalValue)) {
            return Double.NaN;
        }
        // Масштабируем результат по Y (умножение корректно)
        return originalValue + shiftY;
    }
}
