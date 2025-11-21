package functions;

import functions.meta.*;

public class Functions {
    // Приватный конструктор, чтобы нельзя было создать объект класса
    private Functions(){}
    
    // Сдвиг функции
    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }
    // Масштабирование функции
    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }
    // Возведение в степень функции
    public static Function power(Function f, double power){
        return new Power(f, power);
    }
    // Сумма функций
    public static Function sum(Function f1, Function f2){
        return new Sum(f1, f2);
    } 
    // Произведение функций
    public static Function mult(Function f1, Function f2){
        return new Mult(f1, f2);
    } 
    public static Function composition(Function f1, Function f2){
        return new Composition(f1, f2);
    }

    // --------------- Лабораторная работа №6 ---------------

    public static double integrate(Function f, double leftX, double rightX, double step){
        // Проверка границ интегрирования
        if (leftX < f.getLeftDomainBorder() || rightX > f.getRightDomainBorder()){
            throw new IllegalArgumentException(
                "Интервал интегрирования [" + leftX + ", " + rightX + "] " +
                "выходит за область определения функции [" + 
                f.getLeftDomainBorder() + ", " + f.getRightDomainBorder() + "]");
        }
        
        // Проверка на область определения
        if (leftX >= rightX){
            throw new IllegalArgumentException("Левая граница (" + leftX + ") должна быть меньше правой (" + rightX + ")");   
        }

        double totalValue = 0;
        double currentX = leftX;

        // Проходим по всем шагам
        while (currentX + step <= rightX){
            double f1 = f.getFunctionValue(currentX);
            double f2 = f.getFunctionValue(currentX + step);

            // Площадь трапеции: (f1 + f2) * step / 2
            totalValue += (f1 + f2) * step / 2;
            currentX += step;
        }

        // Обрабатываем последний неполный шаг (если есть)
        if (currentX < rightX){
            double remainingStep = rightX - currentX;
            double f1 = f.getFunctionValue(currentX);
            double f2 = f.getFunctionValue(rightX);
            totalValue += (f1 + f2) * remainingStep / 2;
        }
        return  totalValue;
    }
}