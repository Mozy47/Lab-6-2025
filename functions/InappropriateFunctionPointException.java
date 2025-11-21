package functions;

public class InappropriateFunctionPointException extends Exception{
    // Конструктор по умолчанию
    public InappropriateFunctionPointException(){
        super();
    }
    // Конструктор с сообщением
    public InappropriateFunctionPointException(String message){
        super(message);
    }
    // Конструктор с сообщением и причиной
    public InappropriateFunctionPointException(String message, Throwable cause){
        super(message, cause); // Throwable - суперкласс всех ошибок и исключениий в Java
    }
}

