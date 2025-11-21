package functions.meta;

import functions.Function;

public class Scale implements Function{
    private Function f;
    private double scaleX;
    private double scaleY;
    
    public Scale(Function f, double scaleX, double scaleY){
        this.f = f;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
    
    public double getLeftDomainBorder() {
        if (scaleX > 0) {
            return f.getLeftDomainBorder() * scaleX;
        } else if (scaleX < 0) {
            return f.getRightDomainBorder() * scaleX;
        } else {
            return Double.NaN; // Масштаб 0 - функция не определена
        }
    }
    public double getRightDomainBorder() {
        if (scaleX > 0) {
            return f.getRightDomainBorder() * scaleX;
        } else if (scaleX < 0) {
            return f.getLeftDomainBorder() * scaleX;
        } else {
            return Double.NaN;
        }
    }
    
    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }
        // Масштабируем x для исходной функции
        double scaledX = x / scaleX;
        double originalValue = f.getFunctionValue(scaledX);
        if (Double.isNaN(originalValue)) {
            return Double.NaN;
        }
        // Масштабируем результат по Y (умножение корректно)
        return originalValue * scaleY;
    }
}
