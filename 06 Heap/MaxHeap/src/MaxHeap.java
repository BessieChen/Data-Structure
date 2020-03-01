public class MaxHeap<Element extends Comparable<Element>>
{
    private Array<Element> data;

    public MaxHeap(int capcacity)
    {
        data = new Array<>(capcacity);
    }

    public MaxHeap()
    {
        data = new Array<>();
    }

    //如何实现Heapify：
    public MaxHeap(Element[] arr){
        data = new Array<>(arr);
        for(int i = parent(arr.length - 1) ; i >= 0 ; i --)
            siftDown_teacher(i);
    }

    public int size()
    {
        return data.getSize();
    }

    public boolean isEmpty()
    {
        return data.isEmpty();
    }

    //寻找父亲节点
    private int parent(int index)
    {
        if(index == 0)
        {
            throw new IllegalArgumentException("Root node doesn't have parent node.");
        }
        return (index-1)/2;
    }

    private int leftChild(int index)
    {
        return index*2+1;
    }

    private int rightChild(int index)
    {
        return index*2+2;
    }

    public void add(Element e)
    {
        data.addLast(e);
        Element child = data.getLast();
        int chile_index = data.getSize()-1;
        int parent_index = parent(chile_index);
        Element parent = data.get(parent_index);
        while(parent.compareTo(child) < 0 && parent_index == 0)
        {
            data.set(parent_index, child);
            data.set(chile_index, parent);
            child = parent;
            parent_index = parent(parent_index);

            parent = data.get(parent_index);
        }
    }

    public Element findMax()//找到最大堆的最大值。
    {
        if(data.getSize() == 0)
        {
            throw new IllegalArgumentException("Find max failed, heap is empty.");
        }
        return data.getFirst();
    }

    public Element extractMax()
    {
        Element ret = findMax();
        data.swap(0, data.getSize()-1);
        data.removeLast();
        siftDown(0);
        return ret;
    }

    //Wrong
//    private void siftDown_wrong(int index)//错在了没有考虑child的index合不合法。
//    {
//        int max_index;
//        while(data.get(index).compareTo(data.get(leftChild(index))) < 0
//        || data.get(index).compareTo(data.get(rightChild(index))) < 0)
//        {
//            if(data.get(leftChild(index)).compareTo(data.get(rightChild(index))) >= 0)
//            {
//                max_index  = leftChild(index);
//            }
//            else
//            {
//                max_index = rightChild(index);
//            }
//            data.swap(max_index, index);
//            index = max_index;
//        }
//    }

    private void siftDown(int index)
    {
        int left = leftChild(index);
        int right = left+1;
        int max_index;
        while(left <= data.getSize()-1)//不管有没有right child，只要有left child就进while loop
        {
            if(right <= data.getSize()-1)
            {
                if(data.get(index).compareTo(data.get(left)) < 0
                || data.get(index).compareTo(data.get(right)) < 0)
                {
                    if(data.get(left).compareTo(data.get(right)) > 0)
                    {
                        max_index = left;
                    }
                    else
                    {
                        max_index = right;
                    }

                    data.swap(max_index, index);
                    index = max_index;
                    left = leftChild(index);
                    right = left+1;
                }
            }
            else
            {
                if(data.get(index).compareTo(data.get(left)) < 0)
                {
                    data.swap(left, index);
                    index = left;
                    left = leftChild(index);
                    right = left+1;
                }
            }
        }
    }

    //------------------------------------siftUp/siftDown from teacher starts----------------------------------
    public void add_teacher(Element e)
    {
        data.addLast(e);
        siftUp_teacher(data.getSize()-1);
    }

    private void siftUp_teacher(int k)
    {
        while(k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0)
        {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    // 看堆中的最大元素
    public Element findMax_teacher(){
        if(data.getSize() == 0)
            throw new IllegalArgumentException("Can not findMax when heap is empty.");
        return data.get(0);
    }

    // 取出堆中最大元素
    public Element extractMax_teacher(){

        Element ret = findMax_teacher();

        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown_teacher(0);

        return ret;
    }

    private void siftDown_teacher(int k){

        while(leftChild(k) < data.getSize()){
            int j = leftChild(k); // 在此轮循环中,data[k]和data[j]交换位置
            if( j + 1 < data.getSize() &&
                    data.get(j + 1).compareTo(data.get(j)) > 0 )
                j ++;
            // data[j] 是 leftChild 和 rightChild 中的最大值

            if(data.get(k).compareTo(data.get(j)) >= 0 )
                break;

            data.swap(k, j);
            k = j;
        }
    }
    //------------------------------------siftUp/siftDown from teacher ends----------------------------------

    public Element replace(Element e)//将堆顶的元素替换成新元素e，然后整理堆
    {
        Element ret = findMax();
        data.set(0,e);
        siftDown(0);
        return ret;
    }


}


//        Test MaxHeap completed.
//        Test MaxHeap completed.
//        Without heapify: 14.496170872 s
//        Test MaxHeap completed.
//        With heapify: 15.305769861 s
//
//        Process finished with exit code 0
