package functions;

import java.io.*;
import java.util.StringTokenizer;

public class TabulatedFunctions {
    // Приватный конструктор
    private TabulatedFunctions(){}

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        // Проверка, что отрезок табулирования находится в области определения функции
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()){
            throw new IllegalArgumentException(
                "Отрезок табулирования [" + leftX + ", " + rightX + "] " +
                "выходит за область определения функции [" + 
                function.getLeftDomainBorder() + ", " + function.getRightDomainBorder() + "]");
        }
        
        // Проверка на область определения
        if (leftX >= rightX){
            throw new IllegalArgumentException("Левая граница (" + leftX + ") должна быть меньше правой (" + rightX + ")");   
        }
        // Проверка на кол-во точек
        if (pointsCount < 2){
            throw new IllegalArgumentException("Кол-во точек должно быть не меньше двух");
        }

        // Создаем массив значений функции
        double[] values = new double[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);

        // Заполняем массив значениями
        for (int i = 0; i < pointsCount; ++i){
            double x = leftX + i * step;
            values[i] = function.getFunctionValue(x);
        }

        // Возвращаем табулированную функцию (используем ArrayTabulatedFunction)
        return new ArrayTabulatedFunction(leftX, rightX, values);
    }

    // ------------------- ЗАДАНИЕ 7 --------------------

    // Вывод табулированной функции в байтовый поток
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(out);
        
        // Записываем кол-во точек
        dataOut.writeInt(function.getPointsCount());

        // Записываем координаты точек (x,y)
        for (int i = 0; i < function.getPointsCount(); ++i){
            dataOut.writeDouble(function.getPointX(i));
            dataOut.writeDouble(function.getPointY(i));
        }

        // Гарантируем, что все данные записаны в поток, оставляя поток открытым
        dataOut.flush();
    }

    // Ввод табулированной функции из байтового потока
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);
        // Читаем кол-во точек
        int pointsCount = dataIn.readInt();

        // Читаем координты точек
        double[] xValues = new double[pointsCount];
        double[] yValues = new double[pointsCount];

        for (int i = 0; i < pointsCount; ++i){
            xValues[i] = dataIn.readDouble();
            yValues[i] = dataIn.readDouble();
        }

        // Создаем табулированную функцию
        return new ArrayTabulatedFunction(xValues, yValues);
    }

    // Запись табулированной функции в символьный поток
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException{
        PrintWriter writer = new PrintWriter(out);

        // Записываем кол-во точек
        writer.print(function.getPointsCount());
        writer.print(" ");

        // Записываем координаты точек через пробел
        for (int i = 0; i < function.getPointsCount(); ++i){
            writer.print(function.getPointX(i));
            writer.print(" ");
            writer.print(function.getPointY(i));
            if (i < function.getPointsCount() - 1) { // Чтобы исключить лишний пробел
                writer.print(" ");
            }
        }
        
        // Flush, но не закрываем поток
        writer.flush();
    }

    // Чтение табулированной функции из символьного потока
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);

        // Читаем кол-во точек
        tokenizer.nextToken();
        int pointsCount = (int) tokenizer.nval;

        // Читаем координаты точек
        double[] xValues = new double[pointsCount];
        double[] yValues = new double[pointsCount];
        
        for (int i = 0; i < pointsCount; i++) {
            tokenizer.nextToken(); // x координата
            xValues[i] = tokenizer.nval;
            
            tokenizer.nextToken(); // y координата  
            yValues[i] = tokenizer.nval;
        }
        return new ArrayTabulatedFunction(xValues, yValues);
    }
}
