package threads;

import functions.Function;

public class Task {
    private Function f;
    private double leftBorder;
    private double rightBorder;
    private double step;
    private int tasksCount;

    
    // Конструкторы
    public Task(){
        this.tasksCount = 0;
    }

    public Task(int tasksCount){
        this.tasksCount = tasksCount;
    }

    // Геттеры
    public Function getFunction() {
        return f;
    }
    
    public double getLeftBorder() {
        return leftBorder;
    }
    
    public double getRightBorder() {
        return rightBorder;
    }
    
    public double getStep() {
        return step;
    }
    
    public int getTasksCount() {
        return tasksCount;
    }
    
    // Сеттеры
    public void setFunction(Function f) {
        this.f = f;
    }
    
    public void setLeftBorder(double leftBorder) {
        this.leftBorder = leftBorder;
    }
    
    public void setRightBorder(double rightBorder) {
        this.rightBorder = rightBorder;
    }
    
    public void setStep(double step) {
        this.step = step;
    }
    
    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }
}
