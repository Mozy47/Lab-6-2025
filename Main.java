import functions.*;
import functions.basic.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import threads.*;

public class Main {
    
    public static void main(String[] args){
        complicatedThreads();
        //simpleThreads();
        //testTask1();
        //nonThread();
    } 

    public static void complicatedThreads(){
        
        Task task = new Task(100); // Общий объект задания на 100 вычислений
        ReadWriteSemaphore semaphore = new ReadWriteSemaphore(); // Семафор для управления доступом к объекту task
    
        System.out.println("Начало многопоточного выполнения с семафором " + task.getTasksCount() + " заданий:");
        System.out.println("==================================================================================");
  
        Generator generator = new Generator(task, semaphore); // Поток-генератор заданий (операции ЗАПИСИ)
        Integrator integrator = new Integrator(task, semaphore); // Поток-вычислитель интегралов (операции ЧТЕНИЯ)  

        // Вариант 1: Одинаковые приоритеты (баланс)
        // generator.setPriority(Thread.NORM_PRIORITY);
        // integrator.setPriority(Thread.NORM_PRIORITY);
        
        // Вариант 2: Приоритет генератору (быстрее генерирует задания)
        // generator.setPriority(Thread.MAX_PRIORITY);
        // integrator.setPriority(Thread.MIN_PRIORITY);
        
        // Вариант 3: Приоритет интегратору (быстрее вычисляет)
        generator.setPriority(Thread.MIN_PRIORITY);
        integrator.setPriority(Thread.MAX_PRIORITY);

        System.out.println("Запуск потоков Generator и Integrator...");
        generator.start();    // Запускает поток генерации заданий
        integrator.start();   // Запускает поток вычисления интегралов

        try {
            System.out.println("Главный поток ждет 50ms...");
            Thread.sleep(50);  // Главный поток "засыпает" на 50 миллисекунд
        } catch (InterruptedException e) {
            System.out.println("Главный поток был прерван во время ожидания");
        }

        // ПРЕРЫВАЕМ РАБОТУ ПОТОКОВ
        System.out.println("Прерывание потоков Generator и Integrator...");
        generator.interrupt();   // Устанавливает флаг прерывания в потоке Generator
        integrator.interrupt();  // Устанавливает флаг прерывания в потоке Integrator
        
        // ИНФОРМАЦИОННОЕ СООБЩЕНИЕ
        System.out.println("Потоки прерваны главным потоком через 50ms работы");
        
        // ОЖИДАЕМ КОРРЕКТНОГО ЗАВЕРШЕНИЯ ПОТОКОВ
        try {
            System.out.println("Ожидание завершения потоков (таймаут 1 секунда)...");
            
            // join(1000) - ждем завершения потока до 1 секунды
            generator.join(1000);   // Ждем завершения Generator
            integrator.join(1000);  // Ждем завершения Integrator
            
        } catch (InterruptedException e) {
            System.out.println("Главный поток был прерван во время ожидания завершения потоков");
        }

        // ПРОВЕРЯЕМ СТАТУС ПОТОКОВ ПОСЛЕ ПРЕРЫВАНИЯ
    
        if (generator.isAlive()) {
            System.out.println("Generator все еще выполняется после прерывания и таймаута");
        } else {
            System.out.println("Generator корректно завершился");
        }
        
        if (integrator.isAlive()) {
            System.out.println("Integrator все еще выполняется после прерывания и таймаута");
        } else {
            System.out.println("Integrator корректно завершился");
        }
        
        System.out.println("==================================================================================");
        System.out.println("Многопоточное выполнение с семафором завершено");

    }




    public static void simpleThreads() {
        // Создаем объект задания
        Task task = new Task(100);

        System.out.println("Начало многопоточного выполнения " + task.getTasksCount() + " заданий:");
        System.out.println("================================================================");
        
        // Создаем потоки
        Thread generatorThread = new Thread(new SimpleGenerator(task));
        Thread integratorThread = new Thread(new SimpleIntegrator(task));

        // Устанавливаем приоритеты
        generatorThread.setPriority(Thread.MIN_PRIORITY);
        integratorThread.setPriority(Thread.MIN_PRIORITY);

        // Запускаем потоки
        generatorThread.start();
        integratorThread.start();
        
        // Ожидаем завершения потоков
        try {
            generatorThread.join();
            integratorThread.join();
        } catch (InterruptedException e) {
            System.out.println("Главный поток был прерван");
        }
        
        System.out.println("================================================================");
        System.out.println("Многопоточное выполнение завершено");
    }
      
    public static void nonThread() {
        // Создаем объект задания
        Task task = new Task();
        task.setTasksCount(100); // минимум 100 заданий

        Random random = new Random();

        System.out.println("Начало последовательного выполнения " + task.getTasksCount() + " заданий:");
        System.out.println("================================================================");

        for (int i = 0; i < task.getTasksCount(); ++i){
            try {
                // Создаем логарифмическую функцию со случайным основанием от 1 до 10
                double base = 1 + random.nextDouble() * 9; 
                Log logFunc = new Log(base);
                task.setFunction(logFunc);

                // Левая граница от 0 до 100
                double leftBorder = random.nextDouble() * 100;
                task.setLeftBorder(leftBorder);

                // Правая граница от 100 до 200
                double rightBorder = 100 + random.nextDouble() * 100;
                task.setRightBorder(rightBorder);

                // Шаг дискретизации от 0 до 1
                double step = random.nextDouble();
                task.setStep(step);

                // Вывод информации о заданиии
                System.out.printf("Source %.6f %.6f %.6f%n", 
                                 task.getLeftBorder(), 
                                 task.getRightBorder(), 
                                 task.getStep()
                );

                // Вычисление интеграла
                double result = Functions.integrate(task.getFunction(), 
                task.getLeftBorder(), task.getRightBorder(), task.getStep());
                
                // Вывод результата
                System.out.printf("Result %.6f %.6f %.6f %.6f%n", 
                                 task.getLeftBorder(), 
                                 task.getRightBorder(), 
                                 task.getStep(), 
                                 result
                );
                
                System.out.println("---");


            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка в задании " + (i + 1) + ": " + e.getMessage());
                System.out.println("---");
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка в задании " + (i + 1) + ": " + e.getMessage());
                System.out.println("---");
            }
        }
        
        System.out.println("================================================================");
        System.out.println("Последовательное выполнение завершено");
    }
    
    public static void testTask1() {
        Exp expFunction = new Exp();
        
        // Теоретическое значение интеграла e^x от 0 до 1: e^1 - e^0 = e - 1 ≈ 1.718281828459045
        double theoreticalValue = Math.E - 1;
        
        System.out.println("Теоретическое значение интеграла: " + theoreticalValue);
        System.out.println();
        
        // Подбираем шаг дискретизации для точности до 7 знака
        double[] steps = {1.0, 0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
        
        for (double step : steps) {
            try {
                double calculatedValue = Functions.integrate(expFunction, 0, 1, step);
                double error = Math.abs(calculatedValue - theoreticalValue);
                
                System.out.printf("Шаг: %8.6f, Вычисленное значение: %.10f, " +
                                "Ошибка: %.10f%n", 
                                step, calculatedValue, error);
                
                // Проверяем точность до 7 знака
                if (error < 1e-7) {
                    System.out.println("Достигнута точность до 7 знака после запятой");
                    System.out.printf("Минимальный шаг для точности 1e-7: %8.6f%n", step);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ошибка при шаге " + step + ": " + e.getMessage());
            }
        }
    }
}

    
