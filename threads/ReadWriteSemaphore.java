package threads;


// Семафор нужен для управления доступом к общим ресурсам с разделением на операции чтения и записи
public class ReadWriteSemaphore {
    private int readers = 0; // Текущее кол-во активных читателей
    private int writers = 0; // Текущее количество активных писателей (0 или 1)
    private int writeRequests = 0; // Кол-во потоков, ожидающих возможность записи

    private boolean dataReady = false; // Флаг - данные готовы для чтения

    public synchronized void readLock() throws InterruptedException {
        // Пока есть писатели или запросы на запись - ждем
        while (writers > 0 || writeRequests > 0 || !dataReady){
            wait(); // Освобождаем монитор и ждем уведомления
        }
        // Получаем доступ для чтения
        readers++;
    }

    public synchronized void readUnlock() {
        readers--; 
        dataReady = false; 
        notifyAll(); // Если читателей не осталось, уведомляем ожидающих писателей
    }

    public synchronized void writeLock() throws InterruptedException {
        // Регистрируем запрос на запись
        writeRequests++;
        while (readers > 0 || writers > 0 || dataReady){ // Ждем, пока не освободятся все читатели и писатели
            wait(); // Освобождаем монитор и ждем уведомления
        }

        // Получаем экслюзивный доступ для записи
        writeRequests--; // Убираем наш запрос из очереди
        writers++; // Становимся активным писателем
    }

    public synchronized void writeUnlock(){
        writers--;
        dataReady = true;
        notifyAll(); // Уведомляем все ожидающие потоки (и читатели, и писатели)
    }

}
