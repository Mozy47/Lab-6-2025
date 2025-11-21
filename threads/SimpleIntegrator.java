package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private Task task;

    public SimpleIntegrator(Task task){
        this.task = task;
    }

    @Override
    public void run(){
        for (int i = 0; i < task.getTasksCount(); ++i){
            
        try {
            double leftBorder, rightBorder, step, result;
             
            // Ждем данные от генератора
            int attempts = 0;
            while (task.getFunction() == null && attempts < 10) {
                Thread.sleep(100);  // Ждем 100ms
                attempts++;
            }
            
            if (task.getFunction() == null) {
                System.out.println("Integrator: нет данных для задания " + i);
                continue;
            }
            
            
            // Атомарное чтениие все данных
            synchronized (task) {
                leftBorder = task.getLeftBorder();
                rightBorder = task.getRightBorder();
                step = task.getStep();
            } 
            
            // Вычисляем интеграл (вне блока синхронизации)
            result = Functions.integrate(task.getFunction(), leftBorder, rightBorder, step);
                
            // Вывод результата (в синхронизации для красивого вывода)
            synchronized (task) {
                System.out.printf("Integrator: Result %.6f %.6f %.6f %.6f%n", 
                leftBorder, rightBorder, step, result);
                }
                
                // Небольшая пауза
                Thread.sleep(10);
                
            } catch (InterruptedException e) {
                System.out.println("Integrator был прерван");
                return;
            } catch (Exception e) {
                System.out.println("Integrator ошибка: " + e.getMessage());
            }
        }
        
        System.out.println("Integrator завершил работу");
    }
}
