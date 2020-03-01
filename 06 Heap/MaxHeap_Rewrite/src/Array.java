public class Array<Element> {

    /*
     *
     * 目录：
     * 1. 构造函数
     * 2. 3个必备功能：isEmpty，getSize, toString
     * 3. 4个基本操作：
     * --增：1.在任意位置加 2.在末尾加 3.在开头加
     * --删：1.除了给index，我删该index上元素 2.还可以给元素，我去找并删这个元素
     * --改：1.修改一个只有的元素
     * --查：1.查是否含有某个元素 2.查某个index上的元素的值 3.查某个元素值的index
     *
     * 4. Array自己特殊功能: getCapacity,
     * 5. 为服务其他数据结构的功能：swap（服务Heap）
     * 6. 用到Array的其他数据结构：
     * -- MaxHeap
     *
     *
     * 7.易错：
     * -- 不用==判断是否等于，而是用.equals()
     * -- add()函数里面是 从最后一个开始，被前面一个值覆盖，一直到i=index+1，而非：从index+1开始，被前面一个覆盖，一直到最后一个。因为这个错误写法会导致，所有的从index+1开始的全部值都等于了index的值。
     * -- addLast是add(size,e)而不是add(size-1,e)//removeLast才是remove(size-1)
     * -- remove()里面的缩容操作，如果是不考虑复杂度震荡（选择size == data.length/2就缩）应该是先去除掉了该删元素之后，再缩容。而不是先缩容，因为会导致，之后index溢出。
     * -- 如果考虑复杂度震荡（选择size == data.length/4才缩），则可以先缩容，缩到data.length/2, 再删除元素，因为这样也不用担心index溢出。
     * -- 所以保险起见，还是先remove，再缩容。
     * -- removeAllElement(e)里面，记得会利用之前的remove函数，但是因为indexArray记录的是最原始的应该删除的index序列，所以见line 183，应该remove(indexArray[i]-i);
     * -- findAll()函数里面，是判断data[i].equals(e)，而不要粗心写成data.equals(e)
     * -- 注意，如果在我的Array class里面，如果我想调用data的第i个值，就直接data[i]就好了，但是如果是在我的Array class里面，我想调用另外一个Array类的值，就需要aa.get(i)，见line 259
     * -- 小细节：ret.append(data[i]+"]");这里的+不能跟char，只能跟String，所以不可以写成+']'
     * */


    public Element[] data;
    public int size;
    //不需要public int capacity，因为data.length就是capacity，但是我们的size却要我们自己记录

    public Array(Element[] arr)
    {
        data = (Element[]) new Object[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            data[i] = arr[i];
        }
        size = arr.length;
    }

    public Array(int capacity)
    {
        data = (Element[]) new Object[capacity];
        size = 0;
    }

    public Array()
    {
        this(10);
    }

    //3个必备功能：isEmpty，getSize, toString---------------------------------------------------------
    public boolean isEmpty()
    {
        return size == 0;
    }

    public int getSize()
    {
        return size;
    }

    @Override
    public String toString()
    {
        StringBuilder ret = new StringBuilder();
        ret.append(String.format("Size: %d, Capacity: %d ", size, data.length));
        ret.append('[');
        for(int i = 0; i < size; i++)
        {
            ret.append(data[i]);//这里暗含的是data[i]这个Element类型中的toString()，假如说data是一个Student类数组，因为ret.append()需要一个String，那么java就会调用data[i]的toString(),也就是Student类的toString()
            if(i != size-1)
            {
                ret.append(", ");
            }
        }
        ret.append(']');
        return ret.toString();
    }

    //Array的几个特殊功能: getCapacity, swap
    public int getCapacity()
    {
        return data.length;
    }

    public void swap(int index1, int index2)
    {
        Element a = data[index1];
        data[index1] = data[index2];
        data[index2] = a;
    }

    //增-----------------------------------------------------------------------------------------------
    public void add(int index, Element e)//这里的add是指在某个index插入，而不是取代某个index。
    {
        if(index < 0 || index > size)//合法的index是index>=0 && index<=size
        {
            throw new IllegalArgumentException("Add failed, index is illegal");
        }

        if(size == data.length)//当size == capacity时候
        {
            resize(data.length * 2);
        }

        for(int i = size; i >= index+1; i--)//Wrong!:for(int i = index+1; i <= size; i++)
        {
            data[i] = data[i-1];
        }
        data[index] = e;
        size++;
    }

    private void resize(int newLen)
    {
        //Wrong：Element[] newData = new Element[newLen];
        Element[] newData = (Element[]) new Object[newLen];
        for(int i = 0; i < size; i++)
        {
            newData[i] = data[i];
        }
        data = newData;
    }

    public void addFirst(Element e)
    {
        add(0, e);
    }

    public void addLast(Element e)
    {
        add(size, e);
    }

    //删-----------------------------------------------------------------------------------------------
    public Element remove(int index)//这里是给一个index，然后我要删除该index的值，并且返回这个值
    {
        if(index < 0 || index >= size)//合法的index是index>=0 && index<=size-1
        {
            throw new IllegalArgumentException("Remove failed, index is illegal");
        }

        Element ret = data[index];
        /*Wrong!
        以下是错的，即先缩容，后remove，例如，size当前==11，remove一个元素之后应该是10，当前capacity==20，若先缩容，容量设定在capacity == 10，就会导致复制数据的时候，缩容后放不下原先的size==11的数据
        * if(size-1 <= data.length/2 && data.length/2 >= 1)
        {
            resize(data.length/2);
        }

        for(int i = index; i < size-1; i++)
        {
            data[i] = data[i+1];//所以最后虽然我们最后一个元素还是遗留着没有被其他东西覆盖，但是没有关系，因为不影响其他函数使用。
        }

        size--;
        * */

        //正确部分：
        for(int i = index; i <= size-2; i++)
        {
            data[i] = data[i+1];
        }
        size--;
        if(size <= data.length/2 && data.length/2 >= 1) //为了防止复杂度震荡，也可以写成：if(size <= data.length/4 && data.length/2 >= 1)
        {
            resize(data.length/2);
        }
        return ret;
    }

    public Element removeFirst()
    {
        return remove(0);
    }

    public Element removeLast()
    {
        return remove(size-1);
    }

    public void removeElement(Element e)//注意，这里只能保证删除第一个等于Element e的元素
    {
        int index = this.Find(e);
        if(index != -1)
        {
            remove(index);
        }
    }

    public void removeAllElement(Element e)//删除所有等于Element e的元素
    {
        int[] indexArray = this.FindAll(e);
        if(indexArray[0] != -1)
        {
            for(int i = 0; i < indexArray.length; i++)
            {
                remove(indexArray[i]-i);
            }
        }
    }

    //改-----------------------------------------------------------------------------------------------
    public void set(int index, Element e)
    {
        if(index < 0 || index >= size)//合法的index是index>=0 && index<=size-1, 注意这里也不能等于size，也就是set不能当做addLast使用
        {
            throw new IllegalArgumentException("Get failed, index is illegal");
        }
        data[index] = e;
    }

    //查------------------------------------------------------------------------------------------------
    public boolean contains(Element e)
    {
        for(int i = 0; i < size; i++)
        {
            if(data[i].equals(e))
                return true;
        }
        return false;
    }

    public Element get(int index)
    {
        if(index < 0 || index >= size)//合法的index是index>=0 && index<=size-1
        {
            throw new IllegalArgumentException("Get failed, index is illegal");
        }
        return data[index];
    }

    public Element getFirst()
    {
        return get(0);
    }

    public Element getLast()
    {
        return get(size-1);
    }

    public int Find(Element e)
    {
        for(int i = 0; i < size; i++)
        {
            if(data[i].equals(e))
            {
                return i;
            }
        }
        return -1;
    }

    public int[] FindAll(Element e)//因为java没有指针，所以我这里设置一个int[]
    {
        Array<Integer> ret = new Array(this.size);
        for(int i = 0; i < this.size; i++)
        {
            if(data[i].equals(e))//Wrong: data.equals(e),这么写肯定是返回false
            {
                ret.addLast(i);
            }
        }
        if(ret.size == 0)
        {
            return new int[]{-1};
        }
        else
        {
            int[] ret2 = new int[ret.size];
            for(int i = 0; i < ret2.length; i++)
            {
                ret2[i] = ret.get(i);//Wrong：ret[i];不能这么写，因为ret是一个Array类的成员，我们需要用get()函数提取值
            }
            return ret2;
        }
    }


    public static void main(String[] args)
    {
        Array<Integer> my_array = new Array<Integer>();
        for(Integer i = 0; i < 10; i++)
        {
            my_array.add(i,i);
        }
        System.out.println(my_array);//Size = 10, Capacity = 10. [0,1,2,3,4,5,6,7,8,9]


        my_array.add(1,100);//已经动态扩容了
        my_array.addFirst(10);
        my_array.addLast(20);
        my_array.add(0,100);
        System.out.println(my_array);//Size = 14, Capacity = 20. [100,10,0,100,1,2,3,4,5,6,7,8,9,20]


        int[] array =  my_array.FindAll(100);
        for(int j = 0; j < array.length; j++)
        {
            System.out.println(array[j]);//0 3
        }


        my_array.removeAllElement(100);
        System.out.println(my_array); //Size = 12, Capacity = 20. [10,0,1,2,3,4,5,6,7,8,9,20]
        my_array.removeAllElement(23);//没有改变，因为my_array里面没有23
        System.out.println(my_array); //Size = 12, Capacity = 20. [10,0,1,2,3,4,5,6,7,8,9,20]
        // 我们输入时Integer类型，输出时是int，这种自动转换叫做auto-boxing.

        my_array.removeElement(1);
        my_array.removeElement(2);//已经动态缩容了

        System.out.println(my_array);//Size = 10, Capacity = 10. [10,0,3,4,5,6,7,8,9,20]


    }



}


/*
Size: 10, Capacity: 10 [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
Size: 14, Capacity: 20 [100, 10, 0, 100, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20]
0
3
Size: 12, Capacity: 20 [10, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20]
Size: 12, Capacity: 20 [10, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20]
Size: 10, Capacity: 10 [10, 0, 3, 4, 5, 6, 7, 8, 9, 20]
*/