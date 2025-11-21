package threads;

import functions.basic.*;
import java.util.Random;

public class Generator extends Thread {
    
    private Task task; // Общий объект задания, в который записываются параметры
    private ReadWriteSemaphore semaphore;  // Семафор для синхронизации доступа к task

    public Generator(Task task, ReadWriteSemaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        Random random = new Random();

        try {
            for (int i = 0; i < task.getTasksCount(); ++i){
                if (Thread.interrupted()){ // Если поток был прерван, выходим из цикла
                    throw new InterruptedException();
                }

                // Генерируем параметры задания
                double base = 1 + random.nextDouble() * 9;
                Log logFunc = new Log(base);
                
                double leftBorder = random.nextDouble() * 100;
                double rightBorder = 100 + random.nextDouble() * 100;
                double step = random.nextDouble();

                // Захват семафора для записи. Блокируем доступ для других писателей и новых читателей
                semaphore.writeLock();
                try {
                    // Запись данных в общий объект
                    // Атомарно устанавливаем все параметры задания
                    task.setFunction(logFunc);
                    task.setLeftBorder(leftBorder);
                    task.setRightBorder(rightBorder);
                    task.setStep(step);
                    
                    System.out.printf("Generator: Source %.6f %.6f %.6f%n", 
                                     task.getLeftBorder(), 
                                     task.getRightBorder(), 
                                     task.getStep());
                } finally {
                    semaphore.writeUnlock();
                }

                Thread.sleep(10);
            }
            
        System.out.println("Generator завершил работу. Сгенерировано: " + task.getTasksCount() + " заданий");
            
        } catch (InterruptedException e) {
            System.out.println("Generator был прерван");
        } catch (Exception e) {
            System.out.println("Generator ошибка: " + e.getMessage());
        }
    }
}
