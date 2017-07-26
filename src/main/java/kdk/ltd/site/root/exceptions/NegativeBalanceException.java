package kdk.ltd.site.root.exceptions;

public class NegativeBalanceException extends RuntimeException {

    public NegativeBalanceException(String message) {
        super(message);
    }
}
