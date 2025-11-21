package functions;

public interface Function {
    // Методы получения границ определения
    public double getLeftDomainBorder();
    public double getRightDomainBorder();
    // Метод возвращения значения функции в заданной точке
    public double getFunctionValue(double x);
}
