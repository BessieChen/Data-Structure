public class PriorityQueue<Element extends Comparable<Element>> implements Queue<Element>
{
    /*
    * 1.记得PriorityQueue<Element extends Comparable<Element>> implements Queue<Element> ,不要忘记extends Comparable<Element>
    */
    MaxHeap<Element> queue;

    public PriorityQueue()
    {
        queue = new MaxHeap<>();
    }

    public PriorityQueue(Element[] arr)
    {
        queue = new MaxHeap<>(arr);
    }

    @Override
    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    @Override
    public int getSize()
    {
        return queue.getSize();
    }

    @Override
    public void enqueue(Element e)
    {
        queue.add(e);
    }

    @Override
    public Element dequeue()
    {
        return queue.extractMax();
    }

    @Override
    public Element getFront()
    {
        return queue.findMax();
    }
}
