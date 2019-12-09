package myframework.exception;

/**
 * @author Robin
 * @date 2019/12/9 -21:54
 */
public class AopConfigException extends RuntimeException
{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AopConfigException(String message)
    {
        super(message);
        this.printStackTrace();
    }
}
