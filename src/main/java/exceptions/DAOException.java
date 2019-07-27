package exceptions;


import java.util.Arrays;

public class DAOException extends Exception {

    public DAOException(){
        super();
    }

    public DAOException(String message){
        super(message);
    }

    public DAOException(Throwable throwable){
        super(throwable);
    }

    public DAOException(String message, Throwable throwable){
        super(message, throwable);
    }

    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
