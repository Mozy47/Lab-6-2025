package functions;

public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException {
    // Конструктор по умолчанию
    public FunctionPointIndexOutOfBoundsException(){
        super(); // super() - вызов конструктора родителя
    }
    // Конструктор с сообщением
    public FunctionPointIndexOutOfBoundsException(String message){
        super(message);
    }
    // Конструктор с индексом
    public FunctionPointIndexOutOfBoundsException(int index){
        super("Индекс: [" + index + "] выходит за границы");
    }
    // Конструктор с индексом и размером массива
    public FunctionPointIndexOutOfBoundsException(int index, int size){
        super("Индекс: [" + index + "] выходит за границы массива размером " + size);
    }
}
