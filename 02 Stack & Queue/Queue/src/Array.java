public class Array<Element>//泛型数组，其中Element是一个类
{
    private Element[] data;
    private int size;
    //private int capacity;// data.capacity.equals(data.length)

    // constructor, set the value of capacity of array. 构造函数，传入数组的容量capacity构造Array
    public Array(int capacity)
    {
        //this.data = new Element[capacity];//不支持直接new一个泛型数组, 因为是java历史遗留问题，泛型特性本身是从java 1.5才开始支持的。
        this.data = (Element[]) new Object[capacity];
        size = 0;
    }

    // constructor without parameter, with capacity default to 10. 无参数的构造函数，capacity初始值为10.
    public Array()
    {
        this(10);
    }

    //get the size of the array. 获取数组元素个数
    public int getSize()
    {
        return size;
    }

    //get the capacity of the array. 获取数组容量
    public int getCapacity()
    {
        return data.length;
    }

    //check whether the array is empty. 检查数组是否为空
    public boolean isEmpty()
    {
        return size == 0;
    }

    // add to a specific position 插入指定位置
    // 方法：先将最后的向后移动，再把次最后的向后移动，一直到指令位置。
    public void add_static(int index, Element e)//静态add
    {
        if(size == data.length)
        {
            throw new IllegalArgumentException("Add failed. Array is full.");
        }
        if(index < 0 || index > size)//Notes: 索引不能为负数，并且也不能大于size：否则就是稀疏的array了
        {
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size");
        }
        for(int i = size; i >= index+1; i--)//Notes: size表示：1.目前存放的个数 2.第一个没有元素的index； 所以size—1表示：最后一个有元素的index
        {
            //我喜欢这么写：这么写（int i = size; i >= index+1; i--）的思路是：我的主角是第一个没有元素的位置i=size，然后主角需要位置(i-1)的值，主角一直到index的右侧也就是index+1位置
            data[i] = data[i-1];
        }
//        for(int i = size - 1; i >= index; i--)
//        {
//            //这么写（int i = size - 1; i >= index; i--）的思路是：我的主角是最后一个有元素的位置i=size-1，主角把自己的值赋给自己右侧位置(i+1)，主角一直到index
//            data[i+1] = data[i];
//        }

        data[index] = e;
        size++;
    }

    public void add(int index, Element e)//动态add
    {
        if(index < 0 || index > size)//注意每次都要先思考会有什么异常！
        {
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size");
        }
        if(size == data.length)//我扩容的量，需要和我现在的size是一个数量级的。不能说只添加10个，万一我现在的size是1w呢？总之要添加相当的。
        {
            resize(2 * data.length);//扩容1.5倍，3倍也都是可以的。
        }
        for(int i = size; i >= index+1; i--)//Notes: size表示：1.目前存放的个数 2.第一个没有元素的index； 所以size—1表示：最后一个有元素的index
        {
            //我喜欢这么写：这么写（int i = size; i >= index+1; i--）的思路是：我的主角是第一个没有元素的位置i=size，然后主角需要位置(i-1)的值，主角一直到index的右侧也就是index+1位置
            data[i] = data[i-1];
        }
        data[index] = e;
        size++;
    }


    public void addFirst(Element e)
    {
        add(0,e);
    }

    // add after the last element. 加到最后
    public void addLast(Element e)
    {
//        if(size == data.length)
//        {
//            throw new IllegalArgumentException("AddLast failed. Array is full.");
//        }
//        data[size++] = e;
        add(size,e);
    }

//    public void My_add(int e, int position)
//    {
//
//        int a = size;//Notes: 注意这一句错了，应该是 int a = size - 1;
//        for(int i = a; i >= position; i--)
//        {
//            data[i+1] = data[i];
//        }
//        data[position] = e;
//        size++;
//    }//缺点，没有检查position的合法性

    // 获取某个元素
    public Element get(int index)
    {
        if(index < 0 || index >= size)//Notes: 注意这个时候index也是不能==size的
        {
            throw new IllegalArgumentException(String.format("Get failed, index %d is illegal.",index));
        }
        return data[index];
    }

    public Element getLast()
    {
        return get(size-1);//当size==0时，size-1 == -1，get()会报错
    }

    public Element getFirst()
    {
        return get(0);
    }



    // 修改某个元素
    public void set(int index, Element e)
    {
        if(index < 0 || index >= size)//Notes: 注意这个时候index也是不能等于size的，因为remove那个函数会使得index==size的地方残留着某个元素。
        {
            throw new IllegalArgumentException(String.format("Set failed, index %d is illegal.",index));
        }
        data[index] = e;
    }

    // 查找是否有某个元素e
    public boolean contains(Element e)
    {
        boolean b = false;
        for( int i = 0; i < size; i++)
        {
            if(data[i].equals(e))//应该使用值比较，而非if(data[i]==e)
            {
                b = true;
                break;
            }

        }
        return b;
    }

    // 查找某个元素e，如果有就返回e的index，没有就返回-1
    public int find(Element e)
    {
        int f = -1;
        for(int i = 0; i < size; i++)
        {
            if(data[i].equals(e))//应该使用值比较，而非if(data[i]==e)
            {
                f = i;
                break;
            }
        }
        return f;
    }

    // 查找某个元素e，如果有就返回所有元素==e的index，没有就返回-1
    public Integer[] findAll(Element e)
    {
        Array<Integer> f_all = new Array<Integer>(this.size);
        for(int i = 0,j = 0; i < this.size; i++)
        {
            if(this.data[i].equals(e))//应该使用值比较
            {
                f_all.add(j,i);
                j++;
            }
        }
        if(f_all.size==0)
        {
            f_all.add(0,-1);
            return f_all.toIntArray();
        }
        return f_all.toIntArray();
    }


    public Element remove_static(int index)//静态remove
    {
        if(size == 0)
        {
            throw new IllegalArgumentException("Array is empty");
        }
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException(String.format("Remove failed. Index %d is illegal.", index));
        }

        Element r = data[index];
        for(int i = index; i<=size-2; i++)//我喜欢这么写，思路是：主角是位置i=index, 主角需要右侧位置(i+1)的值，主角一直持续到size-2处，因为size-1是之前最后一个元素处，现在size-2是最后一个元素处。
        {
            data[i] = data[i+1];
        }
        size--;//Notes: 注意此时的index==size的地方是有元素的，而不是之前说的（index==size的地方是第一个没有元素的地方）
        // 如果不是泛型数组，遗留最后一个就没有关系，其他的函数会检查index的合法性，保证使用的人永远不能删除，修改，查找。
        // 这种残留的数据叫做loitering data, 但是它无害，因为之后还是可能被别的data的覆盖，所以loitering data != memory leak.
        // 但是如果是泛型数组，我们data里面存的可能是引用，如果不释放的话，java的垃圾回收处理机制就无法启动，所以我们最好还是释放掉引用：
        data[size] = null;//释放了引用，此时java垃圾回收机制启用。
        return r;//Notes: 如果用户不需要remove的值，用户直接调用remove(index, e)就可以，不需要int i = remove(index, e);
    }

    public Element remove(int index)
    {
        if(size == 0)
        {
            throw new IllegalArgumentException("Array is empty");
        }
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException(String.format("Remove failed. Index %d is illegal.", index));
        }

        Element r = data[index];
        for(int i = index; i<=size-2; i++)//我喜欢这么写，思路是：主角是位置i=index, 主角需要右侧位置(i+1)的值，主角一直持续到size-2处，因为size-1是之前最后一个元素处，现在size-2是最后一个元素处。
        {
            data[i] = data[i+1];
        }
        size--;
        data[size] = null;

        if(size == data.length/4 && data.length/2 != 0)//用了一个比较lazy的策略，防止复杂度震荡。另外如果size=1的时候，也不能再缩容了。
        {
            resize(data.length/2);//注意，这里的resize已经做完了遍历data赋值给newData，然后data = newData.
            // 另外也不需要担心会不会resize成为比data.length初始值(10 in this case)更小的size，因为触发if(size == data.length)的条件就已经满足了size比初始值10大
        }
        return r;
    }

    //删除第一个元素
    public Element removeFirst()
    {
        return remove(0);//Notes: 这里不需要check是否不存在第一个元素不存在。因为不存在的话，0==size，则remove函数会报错
    }

    //删除最后一个元素
    public Element removeLast()
    {
        return remove(size-1);//Notes: 这里也不需要担心是否最后一个元素不存在，即Array就是空的，因为最后一个元素不存在的话，size==0, size-1<0，remove函数会报错
    }

    //给定某个element的值，如果有的话就删除第一个等于这个值的element
    public void removeElement(Element e)
    {
        //先查找有没有这个元素，用到find()
        int index = find(e);
        if(index!=-1)
        {
            //如果有这个元素，find()返回index值，并且remove这个index上的值
            remove(find(e));
        }
    }

    //给定某个element的值，如果有的话就删除全部等于这个值的element
    public void removeAllElement(Element e)
    {
        Integer[] index_array = findAll(e);
        if(index_array[0]!=-1)
        {
            for(int i = 0, count = 0; i < index_array.length; i++)
            {
                removeElement(data[index_array[i]-count]);
                count++;
            }
        }
    }

    @Override//覆盖父类的方法：作用：检查父类是不是有toString()
    public String toString()
    {
        StringBuilder s = new StringBuilder();//Notes: 注意不是String s = new String;
        s.append(String.format("Size = %d, Capacity = %d\n", size, data.length));
        s.append("[");
        for(int i = 0; i < size; i++)
        {
            s.append(data[i]);
            if( i != size-1)
            {
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();//Notes: s是StringBuilder类，所以也需要转换成String类
    }


    private void resize(int newCapacity)//Notes: 注意这个resize是private的，也就是说用户是无法给我们的Array扩容的。同样适用于.toIntArray()
    {
        Element[] newData = (Element[]) new Object[newCapacity];//TODO 这里也要思考一下
        for(int i = 0; i < size; i++)
        {
            newData[i] = data[i];
        }
        data = newData;//至此就ok了
        //我们不需要额外更改data的capacity，因为我们的capacity的计算就是data.length已经自动更改了
        //另外我们也不需要更改size，因为目前只是增加额外空间，还没有添加或者删除元素
        //此时data指向了新的newData，而data之前指向的地方：java垃圾回收处理掉了
        //当走出resize()函数，newData的生存期终止，可是data是Array类的成员变量，所以data的的生存期是整个Array类的生存周期一致，所以data还是存在的。
    }

    private Integer[] toIntArray()//Notes: 注意这个也是私有的
    {
        Integer[] result = new Integer[size];
        for(int i = 0; i<size; i++)
        {
            result[i] = (Integer) data[i];
        }
        return result;
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


        Integer[] i =  my_array.findAll(100);
        for(Integer j = 0; j <i.length; j++)
        {
            System.out.println(i[j]);//0 3
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
