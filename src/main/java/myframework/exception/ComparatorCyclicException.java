package myframework.exception;

/**
 * @author Robin
 * @date 2020/1/7 -22:26
 */
public class ComparatorCyclicException extends RuntimeException
{
    public ComparatorCyclicException(String message)
    {
        super(message);
        super.printStackTrace();
    }
}
