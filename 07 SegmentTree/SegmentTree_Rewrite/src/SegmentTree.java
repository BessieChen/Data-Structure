public class SegmentTree<Element>
{
    /*
    * 目录：
    * 1.成员变量：
    *  Element[] data：装基本数据的，原料。
    *  Element[] tree：加工基本数据的，加工后值
    *  因为是数组实现，不需要树结构的元素大小比较，所以Element不用extends Comparable<Element>
    *  不需要专门设计 private int size; 因为size可以直接获取data.length
    *
    * 2.构造函数：
    *  --传入Element[] arr数据
    *  --存入基本函数data，加工成tree
    *    加工tree需要的：空间 -- 4倍的arr元素个数，
    *                  建造segment tree：用到buildSegmentTree()函数，因为成员函数 buildSegmentTree()可以直接处理 成员对象tree，所以tree不需要传入buildSegmentTree()
    *  --Merger<Element> merger;!!!这个必须有，就是用户传入它是打算怎么实现这个Merger接口！
    *
    * 3.如果将数组当做树来使用，必须要有的就是leftChild(), rightChild(), parent() 这样的辅助函数。就像是MaxHeap里面也是如此
    *
    * 4.注意，我们这里有Merger，这个Merger是一个接口，我们这里没有设计这个接口要怎么实现，所以！怎么Merge，是用户通过自定义，然后经过构造函数传入！！
    *
    * 5.基本函数：isEmpty(), getSize(), toString()
    *
    * 6.SegmentTree特有的函数：
    *   private void buildSegmentTree(int treeIndex, int lD, int rD) //将tree里面treeIndex位置加工成data[lD]到data[rD]的加工值
    *   public Element query(int lD, int rD)                         //找到data[lD]到data[rD]的加工值
    *   public void set(int index, Element e)                        //将data中的index位置的值更新为e，然后更新tree里面的加工值。
    *
    * */

    private Element[] data;
    private Element[] tree;
    private Merger<Element> merger; //todo 注意：不能写成private Merger merger; 要有泛函类型


    public SegmentTree(Element[] arr, Merger<Element> merger) //todo 注意：不能写成 public SegmentTree(Element[] arr, Merger merger)要有泛函类型
    {
        data = (Element[]) new Object[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            data[i] = arr[i];
        }

        this.merger = merger;//todo 注意，merger的初始化必须是早于buildSegmentTree(0, 0, data.length-1);否则buildSegmentTree()中的merger.merge()不知道是什么，会报错

        tree = (Element[]) new Object[arr.length * 4];
        buildSegmentTree(0, 0, data.length-1);//还是如此，#1参数：tree第0个索引，#2-3参数：tree上第0个索引对应的是data[0]到data[data.length-1]的加工值

    }

    public boolean isEmpty()
    {
        return data.length == 0;
    }

    public int getSize()
    {
        return data.length;
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

    //辅助函数：
    private int leftChild(int index)
    {
        return index * 2 + 1;
    }

    private int rightChild(int index)
    {
        return index * 2 + 2;
    }

    //treeIndex这个索引里面装的东西，就是从lD到rD的这一段的内容data[lD]到data[rD]，这个内容可以是data[lD]到data[right]的和，或者是data[lD]到data[rD]里面的最大值。
    //注意，lD和rD指的是data里面的索引，真正的内容是data[lD]到data[rD], 而treeIndex是tree的索引，每个treeIndex对应着不同的lD和rD值
    //所以每个treeIndex都装着内容可以是data[lD]到data[right]的和，或者是data[lD]到data[rD]里面的最大值。
    private void buildSegmentTree(int treeIndex, int lD, int rD)
    {
        //找到目标位置：--------------------------
        if(lD == rD)
        {
            tree[treeIndex] = data[lD];
            return;
        }

        //没找到目标位置，继续缩小目标位置：--------------------------
        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);
        int midD = lD + (rD - lD) / 2;

        buildSegmentTree(leftChild, lD, midD);
        buildSegmentTree(rightChild, midD+1, rD);


        //最后到这一步的时候，已经是找到了目标位置并且一步一步递归了--------------------------
        tree[treeIndex] = merger.merge( tree[leftChild], tree[rightChild]);
    }

    public Element query(int lD, int rD)
    {
        if(lD == rD)
        {
            return data[lD];
        }

        if(lD > rD)
        {
            //method 1:
            int temp = lD;
            lD = rD;
            rD = temp;

            //method 2:
            //throw new IllegalArgumentException("failed");
        }

        return query(0, 0, data.length-1, lD, rD);
    }

    private Element query(int treeIndex, int lT, int rT, int lD, int rD)
    {
        //先看终止条件，那就是两边都完全符合
        //我之前想过, 有没有可能终止条件是lD == midT, rD = rT; 说白了就是lD和rD不是两边都符合，而是一边符合，然后另一边只是符合midT？
        //可是这样的终止条件，还是要继续递归的，因为treeIndex这个索引的内容并不是midT->rT或者lT->midT
        if(lT == lD && rT == rD)
        {
            return tree[treeIndex];
        }

        //接下来就是看我们lD，rD到底是在treeIndex索引的左孩子，右孩子，还是左右孩子都占了。
        //这里已经保证了lD绝对小于rD，见上面的query()函数
        //同时这里已经保证了 lT <= lD < rD <= rT, 因为从递归一开始 lT == 0 和 rT == data.length-1 就已经保证了lT <= lD < rD <= rT
        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);
        int midT = lT + (rT - lT) / 2; //注意，这里和buildSegmentTree()中不同，这里是midT, 而build()是midD

        if( midT + 1 <= lD)//todo 我们想要的[lD, rD]在treeIndex的右孩子, 我之前错写成了if( midT + 1 <= lT)，注意是lD！！
        {
            return query(rightChild, midT+1, rT, lD, rD);
        }
        else if( rD <= midT)//todo 我们想要的[lD, rD]在treeIndex的左孩子, 我之前错写成了else if( rT <= midT)，注意是rD！！
        {
            return query(leftChild, lT, midT, lD, rD);
        }
        else // 剩下的只可能是： lD <= mid && mid+1 <= rD
        {
            Element a = query(leftChild, lT, midT, lD, midT); //todo 这里容易错，现在我们要找的是data[lD]到data[midT]的加工值
            Element b = query(rightChild, midT+1, rT, midT + 1, rD);//todo 这里容易错，现在我们要找的是data[midT + 1]到data[rD]的加工值
            return merger.merge(a,b);
        }
    }

    // 将data中的index位置的值更新为e，然后更新tree里面的加工值。
    //todo 这个set主要是，给了index，在data里面改正data[index]的值很简单
    //todo 但是在tree里面找到对应data[index]的位置，这个位置要么是倒数第一层，要么倒数第二层，之后就需要我们递归了，递归之后再往上merge
    public void set(int index, Element e)
    {
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal");
        data[index] = e;
        set(0, 0, data.length-1, index, e);//todo 我犯了一个小错误：将lT写成是data.length, 正确应该是data.length-1. 解释set():还是如此，#1参数：tree第0个索引，#2-3参数：tree上第0个索引对应的是data[0]到data[data.length-1]的加工值，#4参数：我们要找的是data中第index个位置在tree上对应哪里，#5:更新为e
    }

    private void set(int treeIndex, int lT, int rT, int index, Element e)
    {
        //找到目标位置：--------------------------
        if(lT == index && rT == index)
        {
            tree[treeIndex] = e;
            return;
        }

        //没找到目标位置，继续缩小目标位置：--------------------------
        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);
        int midT = lT + (rT - lT) / 2;

        if(index <= midT)//即index在treeIndex索引的左孩子那边
        {
            set(leftChild, lT, midT, index, e);
        }
        else//index >= midT + 1, 即index在treeIndex索引的右孩子那边
        {
            set(rightChild, midT + 1, rT, index, e);
        }

        //最后到这一步的时候，已经是找到了目标位置并且一步一步递归了--------------------------
        tree[treeIndex] = merger.merge(tree[leftChild], tree[rightChild]);

    }
}
