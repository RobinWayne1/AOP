package myframework.aop;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Robin
 * @date 2020/1/6 -21:55
 */
public class PartialOrder
{

    /**
     * All classes that want to be part of a partial order must implement PartialOrder.PartialComparable.
     */
    public static interface PartialComparable
    {
        /**
         * @returns <ul>
         * <li>+1 if this is greater than other</li>
         * <li>-1 if this is less than other</li>
         * <li>0 if this is not comparable to other</li>
         * </ul>
         *
         * <b> Note: returning 0 from this method doesn't mean the same thing as returning 0 from
         * java.util.Comparable.compareTo()</b>
         */
        public int compareTo(Object other);

        /**
         * This method can provide a deterministic ordering for elements that are strictly not comparable. If you have no need for
         * this, this method can just return 0 whenever called.
         */
        public int fallbackCompareTo(Object other);
    }

    /**
     * SortObject,用来封装smallerObjects和biggerObjects,前者是存放比对象内的object小的对象,后者存放比object大的对象
     * 使用SortObject的对象必须实现PartialComparable接口以定义比较规则compareTo()
     * @param <T>
     */
    private static class SortObject<T extends PartialComparable>
    {
        T object;
        List<SortObject<T>> smallerObjects = new LinkedList<SortObject<T>>();
        List<SortObject<T>> biggerObjects = new LinkedList<SortObject<T>>();

        public SortObject(T o)
        {
            object = o;
        }

        boolean hasNoSmallerObjects()
        {
            return smallerObjects.size() == 0;
        }

        boolean removeSmallerObject(SortObject<T> o)
        {
            smallerObjects.remove(o);
            return hasNoSmallerObjects();
        }

        /**
         * 根据比较规则,对this和other进行比较,构造smallerObjects和biggerObjects
         * @param other
         */
        void addDirectedLinks(SortObject<T> other)
        {
            int cmp = object.compareTo(other.object);
            if (cmp == 0)
            {
                return;
            }
            if (cmp > 0)
            {
                this.smallerObjects.add(other);
                other.biggerObjects.add(this);
            } else
            {
                this.biggerObjects.add(other);
                other.smallerObjects.add(this);
            }
        }


    }

    /**
     * 遍历graph内的元素,比较graph内的元素是比so大还是小(后面这一步在SortObject.addDirectedLinks()中进行)
     * 然后将构造好的SortObject加入进graph中
     * @param graph
     * @param o
     * @param <T>
     */
    private static <T extends PartialComparable> void addNewPartialComparable(List<SortObject<T>> graph, T o)
    {
        SortObject<T> so = new SortObject<T>(o);
        for (Iterator<SortObject<T>> i = graph.iterator(); i.hasNext(); )
        {
            SortObject<T> other = i.next();
            so.addDirectedLinks(other);
        }
        graph.add(so);
    }

    /**
     * 思路如下:由于调用该方法的方法中已经选出了一个当前最小的元素,所以要将sortList中的其他元素
     * 的 比当前元素大的smallObject 的当前元素删掉,这才能在调用方法继续迭代时找到下一个smallObject.size=0的最小元素
     * @param graph
     * @param o
     * @param <T>
     */
    private static <T extends PartialComparable> void removeFromGraph(List<SortObject<T>> graph, SortObject<T> o)
    {
        for (Iterator<SortObject<T>> i = graph.iterator(); i.hasNext(); )
        {
            SortObject<T> other = i.next();

            if (o == other)
            {
                i.remove();
            }
            // ??? could use this to build up a new queue of objects with no
            // ??? smaller ones
            other.removeSmallerObject(o);
        }
    }

    /**
     * @param objects must all implement PartialComparable
     * @returns the same members as objects, but sorted according to their partial order. returns null if the objects are cyclical
     */
    public static <T extends PartialComparable> List<T> sort(List<T> objects)
    {
        // lists of size 0 or 1 don't need any sorting
        if (objects.size() < 2)
        {
            return objects;
        }


        List<SortObject<T>> sortList = new LinkedList<SortObject<T>>();
        for (Iterator<T> i = objects.iterator(); i.hasNext(); )
        {
            addNewPartialComparable(sortList, i.next());
        }

        // System.out.println(sortList);

        // now we have built our directed graph
        // use a simple sort algorithm from here
        // can increase efficiency later
        // List ret = new ArrayList(objects.size());
        final int N = objects.size();
        //每一次遍历确定一个 最终排序好的List 的元素的位置
        for (int index = 0; index < N; index++)
        {
            SortObject<T> leastWithNoSmallers = null;
            /**
             * 此处循环找出sortList中一个最小的元素,即smallObjects.size=0的元素
             *
             * 有可能会找不到,即会形成环状结构,因为每次比较都只是与sortList(即已经比较好的list)中
             * 已有的元素进行比较,而不是和原list中的所有元素进行比较
             *
             * 这就是偏序的一个特征,有偏序关系的集合中,偏序(即传递性)只针对集合中的部分元素,由于某些元素可以不遵守偏序关系
             * ,所以排序时可能会形成环状
             */
            for (SortObject<T> so : sortList)
            {
                if (so.hasNoSmallerObjects())
                {
                    if (leastWithNoSmallers == null || so.object.fallbackCompareTo(leastWithNoSmallers.object) < 0)
                    {
                        leastWithNoSmallers = so;
                    }
                }
            }
            //形成环状,返回null
            if (leastWithNoSmallers == null)
            {
                return null;
            }

            removeFromGraph(sortList, leastWithNoSmallers);
            //将最小元素放进结果List中
            objects.set(index, leastWithNoSmallers.object);
        }

        return objects;
    }
}