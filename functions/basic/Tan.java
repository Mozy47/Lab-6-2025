package functions.basic;

public class Tan extends TrigonometricFunction {
    public double getFunctionValue(double x){
        double cosX = Math.cos(x);
        if (Math.abs(cosX) < 1e-10){ // Тангенс не определен, если косинус равен 0
            return Double.NaN;
        }
        return Math.tan(x);
    }
}
