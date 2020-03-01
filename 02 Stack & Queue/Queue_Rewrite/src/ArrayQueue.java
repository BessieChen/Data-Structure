public class ArrayQueue<Element> implements Queue<Element>
{
    private Array<Element> array;

    public ArrayQueue()
    {
        array = new Array<>();
    }

    @Override
    public void enqueue(Element e)
    {
        array.addLast(e);
    }

    @Override
    public Element dequeue()
    {
        return array.removeFirst();
    }

    @Override
    public Element getFront()
    {
        return array.getFirst();
    }

    @Override
    public boolean isEmpty()
    {
        return array.isEmpty();
    }

    @Override
    public int getSize()
    {
        return array.getSize();
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("Array Queue, size: "+array.getSize()+" front [");
        for(int i = 0; i < array.getSize(); i++)
        {
            res.append(array.get(i));
            if(i != array.getSize()-1)
            {
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args)
    {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        for(int i = 0; i < 10; i++)
        {
            queue.enqueue(i);
            System.out.println(queue);
            if(i%3==(3-1))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有2,5,8...对应的就是第3,6,9个元素、
            {
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }

}



/*
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=65210:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/Queue_Rewrite/out/production/Queue_Rewrite ArrayQueue
Array Queue, size: 1 front [0] tail
Array Queue, size: 2 front [0, 1] tail
Array Queue, size: 3 front [0, 1, 2] tail
Array Queue, size: 2 front [1, 2] tail
Array Queue, size: 3 front [1, 2, 3] tail
Array Queue, size: 4 front [1, 2, 3, 4] tail
Array Queue, size: 5 front [1, 2, 3, 4, 5] tail
Array Queue, size: 4 front [2, 3, 4, 5] tail
Array Queue, size: 5 front [2, 3, 4, 5, 6] tail
Array Queue, size: 6 front [2, 3, 4, 5, 6, 7] tail
Array Queue, size: 7 front [2, 3, 4, 5, 6, 7, 8] tail
Array Queue, size: 6 front [3, 4, 5, 6, 7, 8] tail
Array Queue, size: 7 front [3, 4, 5, 6, 7, 8, 9] tail

Process finished with exit code 0
*/
