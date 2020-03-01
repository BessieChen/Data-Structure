import java.util.EventListener;

public class SegmentTree<Element> {

    private Element[] data;
    private Element[] tree;
    private Merger<Element> merger;

    public SegmentTree(Element[] arr, Merger<Element> merger)//注意Merger<Element> merger的<Element>这一个单词不能少，否则main函数里面line16的lambda表达式就会报错。
    {
        if(arr.length == 0)
        {
            throw new IllegalArgumentException("Input array is empty.");
        }
        this.merger = merger;
        data = (Element[]) new Object[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            data[i] = arr[i];
        }

        tree = (Element[]) new Object[arr.length*4];
        buildSegmentTree(0,0,data.length-1);
    }

    private void buildSegmentTree(int treeIndex, int left, int right)
    {
        //treeIndex这个节点里面装的东西，就是从left到right的这一段的内容data[left]到data[right]，这个内容可以是data[left]到data[right]的和，或者是data[left]到data[right]里面的最大值。
        //注意，left和right指的是data里面的索引，真正的内容是data[left]到data[right], 而treeIndex是tree的索引，每个treeIndex对应着不同的left和right值，所以每个treeIndex都装着内容可以是data[left]到data[right]的和，或者是data[left]到data[right]里面的最大值。

        //首先看终止条件：
        if(left == right)
        {
            tree[treeIndex] = data[left];//这个treeIndex装的就只是data里面单独的一个值，像是原料，没有merge，也就是没有合并什么。
            return;
        }

        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);

        int mid = left + (right - left)/2;

        buildSegmentTree(leftChild, left, mid);//给leftChild赔上从data[left]到data[mid]的内容。
        buildSegmentTree(rightChild, mid+1, right);//给rightChild赔上从data[mid+1]到data[right]的内容。

        tree[treeIndex] = merger.merge(tree[leftChild], tree[rightChild]);

    }

    private void buildSegmentTree_teacher( int treeIndex, int left, int right)
    {
        if(left == right)//说明到了最底层。
        {
            tree[treeIndex] = data[left];//也就相当于等于data[right]
            return;//别忘了return，否则就继续走了。
        }
        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);

        int mid = left + (right - left)/2;

        buildSegmentTree(leftChild, left, mid);
        buildSegmentTree(rightChild, mid+1, right);

        tree[treeIndex] = merger.merge(tree[leftChild], tree[rightChild]);

    }

    public Element get(int index)
    {
        if(index < 0 || index >= data.length)
        {
            throw new IllegalArgumentException("Index is illegal.");
        }
        return data[index];
    }

    public int getSize()
    {
        return data.length;
    }

    private int leftChild(int index)
    {
        return index*2+1;
    }

    private int rightChild(int index)
    {
        return index*2+2;
    }

    private int parent(int index)
    {
        return (index-1)/2;
    }

    public Element query(int leftQ, int rightQ)
    {
        if(leftQ < 0 || leftQ >= data.length ||
                rightQ < 0 || rightQ >= data.length)
            throw new IllegalArgumentException("Index is illegal.");

        if(leftQ > rightQ)
        {
            int temp = leftQ;
            leftQ = rightQ;
            rightQ = temp;
        }
        return query(0, 0, data.length-1, leftQ, rightQ);
    }

    private Element query(int treeIndex, int leftT, int rightT, int leftQ, int rightQ )
    {
        if(leftQ == leftT && rightQ == rightT)
        {
            return tree[treeIndex];
        }
        int mid = leftT + (rightT - leftT)/2;
        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);
        if(mid >= leftQ && mid + 1 <= rightQ) //所以才有后面的 leftT <= leftQ <= mid 以及 mid+1 <= rightQ <= rightT.
        {
            Element leftResult = query(leftChild, leftT, mid, leftQ, mid);
            Element rightResult = query(rightChild, mid+1, rightT, mid+1, rightQ);
            return merger.merge(leftResult, rightResult);
        }
        if(mid < leftQ)// 所以才有后面的 mid+1 <= leftQ <= rightT.
        {
            return query(rightChild, mid+1, rightT, leftQ, rightQ);
        }
        //再剩下就是 mid > rightQ
        return query(leftChild, leftT, mid, leftQ, rightQ);

    }


    // 在以treeIndex为根的线段树中[leftT...rightT]的范围里，搜索区间[leftQ...rightQ]的值
    //注意：这个leftQ和rightQ在进入函数的时候就已经保证了，leftT < leftQ < rightQ < rightT.
    private Element query_teqcher(int treeIndex, int leftT, int rightT, int leftQ, int rightQ )//int treeIndex, int leftT, int rightT这三个就是tree上某个节点的信息。
    {
        if(leftT == leftQ && rightT == rightQ)
            return tree[treeIndex];

        int mid = leftT + (rightT - leftT) / 2;
        // treeIndex的节点分为[leftT...mid]和[mid+1...rightT]两部分

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        if(leftQ >= mid + 1)
            return query(rightTreeIndex, mid + 1, rightT, leftQ, rightQ);
        else if(rightQ <= mid)
            return query(leftTreeIndex, leftT, mid, leftQ, rightQ);

        Element leftResult = query(leftTreeIndex, leftT, mid, leftQ, mid);
        Element rightResult = query(rightTreeIndex, mid + 1, rightT, mid + 1, rightQ);
        return merger.merge(leftResult, rightResult);


    }

    public void set(int index, Element e)
    {
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal");
        data[index] = e;
        set(0, 0, data.length-1, index, e);

    }

    private void set(int treeIndex, int lT, int rT, int index, Element e)
    {
        if(lT == rT && lT == index)
        {
            tree[treeIndex] = e;
            return;
        }
        int mid = lT + (rT - lT)/2;
        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);

        if(index <= mid)
        {
            set(leftChild, lT, mid, index, e);
        }
        else // mid+1 <= index
        {
            set(rightChild, mid+1, rT, index, e);
        }
        tree[treeIndex] = merger.merge(tree[leftChild], tree[rightChild]);
    }

    // 将index位置的值，更新为e
    public void set_teacher(int index, Element e){

        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal");

        data[index] = e;
        set_teacher(0, 0, data.length - 1, index, e);
    }

    // 在以treeIndex为根的线段树中更新index的值为e
    private void set_teacher(int treeIndex, int l, int r, int index, Element e){

        if(l == r){
            tree[treeIndex] = e;
            return;
        }

        int mid = l + (r - l) / 2;
        // treeIndex的节点分为[l...mid]和[mid+1...r]两部分

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        if(index >= mid + 1)
            set_teacher(rightTreeIndex, mid + 1, r, index, e);
        else // index <= mid
            set_teacher(leftTreeIndex, l, mid, index, e);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for(int i = 0 ; i < tree.length ; i ++){
            if(tree[i] != null)
                res.append(tree[i]);
            else
                res.append("null");

            if(i != tree.length - 1)
                res.append(", ");
        }
        res.append(']');
        return res.toString();
    }
}

