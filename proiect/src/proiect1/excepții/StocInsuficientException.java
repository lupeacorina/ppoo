package proiect1.excepții;

public class StocInsuficientException extends Exception{
    public StocInsuficientException() {
        super();
    }

    public StocInsuficientException(String message) {
        super(message);
    }
}
