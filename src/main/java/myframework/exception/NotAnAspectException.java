package myframework.exception;

/**
 * @author Robin
 * @date 2019/12/7 -22:33
 */
public class NotAnAspectException extends RuntimeException
{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotAnAspectException(Class<?>cl)
    {
        super(cl.getName()+" is not an @AspectJ aspect");
        this.printStackTrace();
    }
}
