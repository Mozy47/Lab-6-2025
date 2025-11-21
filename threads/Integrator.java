package threads;

import functions.Functions;
import functions.Function;

public class Integrator extends Thread {
    private Task task; // Общий объект задания, из которого читаются параметры
    private ReadWriteSemaphore semaphore; // Семафор для синхронизации доступа к task

    public Integrator(Task task, ReadWriteSemaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run(){
        int processedCount = 0; // Счетчик успешно обработанных заданий

        try {
            // Цикл по всем заданиям, которые нужно обработать
            for (int i = 0; i < task.getTasksCount(); i++) {
                
                // Проверка прерывания потока
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                
                // Переменные для хранения параметров задания
                double leftBorder, rightBorder, step, result;
                Function function;

                // Захват семафора для чтения. Разрешает параллельное чтение, но блокирует запись
                semaphore.readLock();
                try {
                    // Чтение параметров из общего объекта
                    // Атомарно читаем все параметры задания
                    leftBorder = task.getLeftBorder();
                    rightBorder = task.getRightBorder();
                    step = task.getStep(); 
                    function = task.getFunction(); 
                } finally { // Код выполнится ВСЕГДА, назависимо от того, что произошло в try
                    semaphore.readUnlock(); // Освобождаем сразу после чтения, чтобы не блокировать генератор
                }

                // Проверка наличия данных.
                // Если функция еще не установлена генератором
                if (task.getFunction() == null) {
                    // Ждем пока генератор подготовит данные
                    Thread.sleep(50);
                    continue; // Переходим к следующей итерации
                }
                
                // Вычисление интеграла 
                // Это "тяжелая" операция, выполняем без блокировки семафора
                result = Functions.integrate(function, leftBorder, rightBorder, step);
                
                System.out.printf("Integrator: Result %.6f %.6f %.6f %.6f%n", 
                    leftBorder, rightBorder, step, result);
                
                processedCount++; // Увеличиваем счетчик успешных вычислений
                
                Thread.sleep(10);
            }
            
            System.out.println("Integrator завершил работу. Обработано: " + processedCount + " заданий");
            
        } catch (InterruptedException e) {
            System.out.println("Integrator был прерван. Обработано: " + processedCount + " заданий");
        } catch (Exception e) {
            System.out.println("Integrator ошибка: " + e.getMessage() + ". Обработано: " + processedCount + " заданий");
        }
    }
}















