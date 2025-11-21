package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable{
    // Переменные для хранения координат точкии
    private double x;
    private double y;

    // Конструктор создания объекта с заданными координатами
    public FunctionPoint(double x, double y){
        this.x = x; // this обращается к полю текущего объекта
        this.y = y;
    }

    // Конструктор копирования существующей точки
    public FunctionPoint(FunctionPoint point){
        this.x = point.x;
        this.y = point.y;
    }
    
    // Конструктор создания точки с координатами (0 , 0)
    public FunctionPoint(){
        this.x = 0;
        this.y = 0;
    }

    // Геттеры
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    // Сеттеры
    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
 
    // ---------------- Лабораторная работа №5 ------------------
    
    // Переопределение метода toString()
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Переопределение метода equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionPoint that = (FunctionPoint) o;

        // Корректное сравнение чисел с плавающей точкой через машинный эпсилон
        return Math.abs(that.x - x) < 1e-10 && Math.abs(that.y - y) < 1e-10;
    }

    // Переопределение метода hashCode()
    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(x);
        long yBits = Double.doubleToLongBits(y);
    
        // Комбинируем сразу long, потом преобразуем в int
        long combined = xBits ^ yBits;
    
        // Преобразуем long в int, комбинируя старшие и младшие биты
        return (int)(combined ^ (combined >>> 32));
    }

    // Переопределение метода clone()
    @Override
    public Object clone(){
        try {
            return new FunctionPoint(this);
        } catch (Exception e) {
            throw new AssertionError("Клонирование невозможно");
        }
    }
}
