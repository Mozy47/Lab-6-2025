package functions.meta;

import functions.Function;

public class Composition implements Function{
    private Function outFunc;
    private Function inFunc;

    public Composition(Function outFunc, Function inFunc){
        this.outFunc = outFunc;
        this.inFunc = inFunc;
    }

    // Область определения можно считать областью определения внутренней функции
    public double getLeftDomainBorder(){
        return inFunc.getLeftDomainBorder();
    }
    public double getRightDomainBorder(){
        return inFunc.getRightDomainBorder();
    }

    public double getFunctionValue(double x){
        // Проверям, что x принадлежит пересечению областей определения
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()){
            return Double.NaN;
        }
        double inFuncValue = inFunc.getFunctionValue(x);
        if (Double.isNaN(inFuncValue)) {
            return Double.NaN;
        }

        // Проверяем, что результат внутренней функции принадлежит области определения внешней
        if (inFuncValue < outFunc.getLeftDomainBorder() || inFuncValue > outFunc.getRightDomainBorder()) {
            return Double.NaN;
        }
        // Затем передаем результат во внешнюю функцию
        return outFunc.getFunctionValue(inFuncValue);
    } 
}
