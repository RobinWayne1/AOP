package myframework.exception;

/**
 * @author Robin
 * @date 2019/12/10 -1:01
 */
public class AopInvocationException extends RuntimeException {

    /**
     * Constructor for AopInvocationException.
     * @param msg the detail message
     */
    public AopInvocationException(String msg) {
        super(msg);
        this.printStackTrace();
    }

    /**
     * Constructor for AopInvocationException.
     * @param msg the detail message
     * @param cause the root cause
     */
    public AopInvocationException(String msg, Throwable cause) {
        super(msg, cause);
        this.printStackTrace();
    }

}