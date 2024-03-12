package proiect1.excepții;

public class CantitateNegativaException extends Exception{
    public CantitateNegativaException() {
        super();
    }

    public CantitateNegativaException(String message) {
        super(message);
    }
}
