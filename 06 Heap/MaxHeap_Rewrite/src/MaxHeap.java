public class MaxHeap<Element extends Comparable<Element>> { //最大堆是有序的，所以元素需要extends Comparable

    /*
    * 目录：
    * 1.MaxHeap要能够容纳泛型Element，并且因为MaxHeap是需要比较大小的有序数组，所以Element需要extends Comparable这个类，注意，是extends，不是implements。extends+类， implements+接口
    * 2.类成员变量：因为我们是用数组来构建MaxHeap，所以我们要创建一个数组对象，但是java的int[]的数组是不行的，因为它是静态的不能随意扩容缩容，所以我们采取自定义的Array数组
    *   另外，因为siftDown和siftUp操作中，我们需要知道需要siftUp和siftDown元素的位置，所以我们需要一个int data.getSize()变量，以此知道我们add之后最后一个元素的位置，用于siftUp。
    * 3.构造函数：
    *   一个是刚开始是一个空数组，之后慢慢在data.getSize()位置，也就是第一个没有元素的位置添加元素，再对该元素siftUp
    *   另一个是一开始就传入一个非空数组，然后直接heapify。Heapify的方法见第6点。
    * 4.辅助函数
    *   我们需要知道leftChild的index，rightChild的index：用于siftDown时候的比较
    *   还需要知道parent的index：用于siftUp时候的比较
    *   我们在这个case中使用index==0为根节点
    *   注意：这个辅助函数，需要是private的，用户不需要知道我们的MaxHeap的底层是Array，自然不需要给用户父亲儿子的index
    *   另外leftChild(), rightChild()不需要判断某个index是不是没有leftChild或者rightChild，因为如果在leftChild()里面判断一般只能throw new Illegal...，可是我们不需要报错。
    *   如果真的不存在某个index的leftChild或者rightChild，我们可以做的是对于siftDown来说，不需要进入while loop，而不是报错截断运行。
    * 5.Element有compareTo这个功能，因为Element extends Comparable<Element>, 故Element类是Comparable的子类
    *   而compareTo是Comparable里面的函数，所以Element，也就是我们MaxHeap的元素，都可以继承compareTo的函数。
    *   compareTo用于siftDown和siftUp时候的大小比较。
    * 6.非常易错：Heapify的时候的两种方法：
    *           方法一：
    *           用siftDown：从第parent(data.getSize()-1)个元素开始，siftDown，一直到第一个元素。
    *           记忆的方法很简单：就像是梳头发，先把最底下的梳顺了，再移上一点，再从上往下梳顺。
    *           方法二：
    *           用siftUp：从第一个元素开始，即index==0开始，一直siftUp到最后一个元素。
    *           而不是反过来，因为：
    *           假如说siftUp(index),假设index是倒数第二层的非叶子节点，那么向上浮之后，这个index底下的元素就会一团糟。
    *           比喻：siftUp就像是往上能梳顺的东西，所以就想梳狗狗背上的毛，先把最上层的往上梳顺。再往下一层，往上梳顺。
    *   方法二比方法一慢，因为方法一可以省去all叶子节点。
    *   后来我发现的问题：
    *   siftDown(index)，只能保证index以下的元素是符合MaxHeap结构的。
    *
    *   siftUp(index)，只能保证index以上的元素是符合MaxHeap结构的
    *   这就意味着，如果我们是siftUp最底层的节点，那么，最底层的节点以上，都能满足最大堆结构。（不过前提是最低层以上本来都是符合最大堆的）
    *   但是，如果siftUp倒数第二层，那只能保证倒数第二层以上是符合结构条件的，但是倒数第二层以下，也就是最后一层，就不满足了。
    *   所以我们用siftUp只有在add的时候才会用到，因为
    *   1.未add的时候数据结构本身就符合MaxHeap的条件，add的时候只是在结尾添加了一个不符合结构条件的元素
    *   2.add(index)的index本身也是最后一层
    *
    *   例1：一个最后一层开始siftUp，但是上面本身满足MaxHeap
    *      10
    *    9   8
    *  5 4  11
    *   如果从最后一个元素11开始siftUp，再siftUp4，一直到元素值为5的节点，结果是：
    *       11
    *    9    10
    *   5 4  8
    *   依旧符合MaxHeap条件。
    *
    *   例2：一个最后一层开始siftUp，但是上面不满足MaxHeap
    *       1
    *    2    3
    *   5 4  6
    *   如果从最后一个元素6开始siftUp，再siftUp4，一直到元素值为5的节点，结果是：
    *       6
    *    5    1
    *   4 2  3
    *   所以不符合MaxHeap
    *
    *   例3：从中间某一个index开始siftUp，但是之前不满足MaxHeap，结果就是：index上层没问题，但是下层会一团糟。
    *       0
    *    2    3
    *   5 4  1
    *   假如说对值==3的元素siftUp，那就是：
    *       3
    *    2     0
    *   5 4  1
    *   可以看出0和1是错的。因为3往上浮，是不会管下面发生了什么。
    *
    *   总之：
    *   1.siftUp之所以应用在1.add函数的siftUp(size-1)最后一个元素，是因为，保证了最后一个元素的UP都是符合条件的。别忘了前提：add之前本身数据结构就是符合MaxHeap的，因为之前也是一个个add进去的，步步都是符合MaxHeap的
    *   2.siftDown之所以可以应用在1.extractMax里面的siftDown(0)第一个元素，2.siftDown任何index，是因为保证了第一个元素index==0的Down都是符合条件的。别忘了前提：siftDown之前的本身数据结构也是符合MaxHeap的
    *
    * 7.所以小总结：
    *   siftUp：用于在数组末尾add一个新元素
    *   siftDown：用于extractMax，当最后一个元素替代了优先级最大，也就是第一个的元素之后，siftDown(0)
    *             用于Heapify时候，从第parent(data.getSize()-1)个元素开始，siftDown，一直到第一个元素。
    * 8.小细节：关于MaxHeap的data.getSize()如何设定：我之前设定的是,初始时：data.getSize() = data.getSize(), 因为数组Array类的成员：data，它的data.getSize()不会自动给
    *   我们MaxHeap的data.getSize()，所以我是自己手动维护data.getSize()++或者data.getSize()--。如此一来很不方便。其实更加聪明的做法是：不需要为MaxHeap类设置专门的成员变量data.getSize()
    *   而是在我们需要使用data.getSize()的时候，直接调用data.getSize()
    *   同样，MaxHeap类设置的getSize()成员函数，里面return data.getSize();
    * 9.关于权限：
    *   成员变量：private的，例如data，否则会通过data[1]等危险的行为改动我的内部数据。
    *   构造函数：一般都是public
    *   成员函数：大多数都是public的
    *   如果含有内部类，例如LinkedList和BST里面的Node，类是私有的：private class Node{}
    *   但是内部类的：成员函数，构造函数和成员函数需要是public的，例如public Element e; public Node left; public Node next;
    * 10.我的siftDown()写了两遍可都没有老师的简洁。
    * */

    private Array<Element> data;

    public MaxHeap()
    {
        data = new Array<>();//在Array类里面，default构造函数默认构造capacity==10的数组
    }

    public MaxHeap(Element[] arr)//注意这里的参数不能写成 int[] arr, 因为传入new Array<>(arr)中，Array类的构造函数需要的是Element[]型的变量。而不是基础类的
    {
        /*data = new Array<>(arr.length);
        for(int i = 0; i < arr.length; i++)
        {
            data.add(i, arr[i]);
        }*/

        data = new Array<>(arr);//经过这一步，data已经包含了arr的所有元素，thanks to Array类的构造函数

        //Heapify的方法：从最后一个非叶子节点开始siftDown，直到0节点
        for(int i = parent(data.getSize()-1); i >=0; i--)//最后一个非叶子节点，因为最后一个非叶子节点 == 最后一个节点的父亲, parent(data.getSize()-1)就是最后一个非叶子节点，即(data.getSize()-1)-1/2
        {
            siftDown(i);
        }
        //另一个Heapify方法：从第一个节点开始siftUp,直到最后一个节点。
//        for(int i = 0; i <= data.getSize()-1 ; i++)
//        {
//            siftUp(i);
//        }


    }

    private int leftChild(int index)
    {
        return index * 2 + 1;
    }

    private int rightChild(int index)
    {
        return index * 2 + 2;
    }

    private int parent(int index)
    {
        if(index == 0)
        {
            throw new IllegalArgumentException("Root node doesn't have parent node.");
        }
        return (index - 1) / 2;
    }

    //最基础的两个：isEmpty()，getSize()
    public boolean isEmpty()
    {
        return data.getSize() == 0;
    }

    public int getSize()
    {
        return data.getSize();
    }

    //增：
    public void add(Element e)
    {
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    private void siftUp(int index)
    {
        //method 1:写的有点笨
        //先看index合不合法，如果不合法：即是根节点，那就不可能再siftUp了
//        if(index == 0)
//        {
//            return;
//        }
//
//        while(data.get(parent(index)).compareTo(data.get(index)) < 0)//当父亲节点小于自己
//        {
//            data.swap(index, parent(index));//这里不需要比较，自己和父亲节点的另一个孩子，因为已经保证了另一个孩子是小于父亲的，如果自己大于父亲，那自己也一定大于另一个孩子。
//            index = parent(index);
//        }

        //method 2:
        while(index > 0 && data.get(parent(index)).compareTo(data.get(index)) < 0)
        {
            data.swap(index, parent(index));//这里不需要比较，自己和父亲节点的另一个孩子，因为已经保证了另一个孩子是小于父亲的，如果自己大于父亲，那自己也一定大于另一个孩子。
            index = parent(index);
        }
    }

    //删：
    public Element extractMax()
    {
        Element ret = findMax();
        data.swap(0,data.getSize()-1);
        data.removeLast();
        siftDown(0);
        return ret;
    }

    //还是老师的最简洁！只有9行
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

    private void siftDown(int index)
    {
        //先看index还能不能siftDown。首先，叶子节点是不能再siftDown的
        //最后一个能siftDown的是最后一个非叶子节点，因为最后一个非叶子节点 == 最后一个节点的父亲
        //parent(data.getSize()-1)就是最后一个非叶子节点，即((data.getSize()-1)-1)/2), 所以大于((data.getSize()-1)-1)/2)的index都是叶子节点，不可能siftDown了
        while(data.getSize() != 1 && index <= parent(data.getSize()-1) && index >= 0)//另外注意如果parent()参数值为0，parent()会报错说0没有parent
        {
            //接下来就是和左右孩子比较大小，再跟最大的孩子swap，做法：不是先和左孩子比，再和右孩子比，而是：
            //先看有无右孩子，如果有右孩子：先取左右孩子最大，再和这个最大比。如果没有右孩子：跟左孩子比最大。
            int leftChild = leftChild(index);
            int maxChild = leftChild;//最大孩子先设成左孩子，如果存在右孩子并且右孩子最大，就赋予rightChild
            if(rightChild(index) <= data.getSize()-1)//说明有右孩子
            {
                int rightChild = rightChild(index);
                if(data.get(rightChild).compareTo(data.get(leftChild)) > 0)
                {
                    maxChild = rightChild;
                }
            }
            if(data.get(index).compareTo(data.get(maxChild)) < 0)
            {
                data.swap(index, maxChild);
                index = maxChild;
            }
            else
            {
                break;
            }
        }
    }

    //
    private void siftDown_opt(int index)
    {
        while(leftChild(index) <= data.getSize()-1)
            //这一句while保证了，index是<=第一个非叶子节点，同时也保证了，不是只有根节点，即index == 0 && data.getSize() == 1;
            //leftChild < size-1意味着index还有一个rightChild，当leftChild == size-1意味着index没有rightChild。但无论如何index都一定至少有一个Child
        {
            int leftChild = leftChild(index);
            int maxChild = leftChild;
            if(leftChild+1 <= data.getSize()-1)//说明有rightChild
            {
                if(data.get(leftChild+1).compareTo(data.get(leftChild)) > 0)//rightChild更大
                {
                    maxChild = leftChild+1;
                }
            }

            if(data.get(maxChild).compareTo(data.get(index)) > 0)
            {
                data.swap(maxChild, index);
                index = maxChild;
            }
            else
            {
                break;
            }
        }
    }


    //改：功能是取出优先级最高的，并插入一个新的，最后要保持MaxHeap的结构
    public Element replace(Element newE)
    {
        Element ret = findMax();//要学会使用已有的函数！
        data.set(0,newE);
        siftDown(0);
        return ret;
    }

    //查：MaxHeap只能查到优先级最高的
    public Element findMax()
    {
        if(data.getSize() == 0)
        {
            throw new IllegalArgumentException("extractMax failed, MaxHeap is empty.");
        }
        return data.get(0);
    }

    @Override
    public String toString()
    {
        return data.toString();//这里就会用到Array类的toString，打印的是一个框架。
    }
}
