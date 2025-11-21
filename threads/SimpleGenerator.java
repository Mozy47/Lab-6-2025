package threads;

import functions.basic.*;
import java.util.Random;

public class SimpleGenerator implements Runnable {
    private Task task;

    public SimpleGenerator(Task task){
        this.task = task;
    }

    @Override
    public void run(){
        Random random = new Random();

        for (int i = 0; i < task.getTasksCount(); i++) {
            try {
                // Генерируем параметры задания
                double base = 1 + random.nextDouble() * 9;
                Log logFunc = new Log(base);
                
                double leftBorder = random.nextDouble() * 100;
                double rightBorder = 100 + random.nextDouble() * 100;
                double step = random.nextDouble();
                
                // Синхронизация на объекте task
                synchronized (task) {
                    // Устанавливаем параметры в объект задания
                    task.setFunction(logFunc);
                    task.setLeftBorder(leftBorder);
                    task.setRightBorder(rightBorder);
                    task.setStep(step);
                    
                    // Вывод информации о сгенерированном задании
                    System.out.printf("Generator: Source %.6f %.6f %.6f%n", 
                                     task.getLeftBorder(), 
                                     task.getRightBorder(), 
                                     task.getStep());
                }
                
                // Небольшая пауза для наглядности
                Thread.sleep(10);
                
            } catch (InterruptedException e) {
                System.out.println("Generator был прерван");
                return;
            } catch (Exception e) {
                System.out.println("Generator ошибка: " + e.getMessage());
            }
        }
        System.out.println("Generator завершил работу");
    }
}
