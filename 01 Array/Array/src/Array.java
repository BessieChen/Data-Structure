public class Array<Element> {

    private Element[] array;
    private int size;

    public Array()
    {
        this(10);
    }

    public Array(int capacity)
    {
        array = (Element[]) new Object[capacity];//Byb 强制类型转换：这一句区别于后面的line 140的Array<Integer> index = new Array<>();
    }

    public void add(int index, Element e)
    {
        if(index < 0 || index > size)
        {
            throw new IllegalArgumentException("Index is illegal");
        }

        if(size == array.length)
        {
            resize(array.length*2);
        }
        for(int i = size ; i >= index + 1; i--)
        {
            array[i] = array[i-1];
        }

        array[index] = e;
        size++;
    }

    public void addFirst(Element e)
    {
        add(0, e);
    }

    public void addLast(Element e)
    {
        add(size, e);
    }

    private void resize(int newCapacity)
    {
        Element[] newArray = (Element[]) new Object[newCapacity];
        for(int i = 0; i < size; i++)
        {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public Element remove(int index)
    {
        if(size == 0)
        {
            throw new IllegalArgumentException("Remove failed. Array is empty");
        }
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException(String.format("Index %d is illegal", index));
        }
        Element ret = array[index];
        // Byb 这里要注意的，一定是先remove，后缩容。不然的话，一上来就缩容的话， 导致我们后面的空间都没了。
        for(int i = index; i <= size - 2; i++)
        {
            array[i] = array[i+1];
        }
        size--;
        if(size == array.length/4 && array.length/2 != 0)//Byb 这里我错写成了||，应该是&&，意思是，我们resize之后的长度不能==0
        {
            resize(array.length/2);
        }

        return ret;
    }

    public Element removeFirst()
    {
        Element first = remove(0);
        return first;
    }

    public Element removeLast()
    {
        Element last = remove(size-1);
        return last;
    }

    public void removeElement(Element e)
    {
        int index = find(e);
        if(index!=-1)
        {
            remove(index);
        }
    }

    public void removeAllElement(Element e)
    {
        Array<Integer> index = findAll(e);
        if(index.get(0)!=-1)
        {
            for(int i = 0, count = 0; i < index.getSize(); i++)
            {
                remove(index.get(i)-count);
                count++;
            }
        }
    }

    public void set(int index, Element e)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException(String.format("Index %d is illegal",index));
        }
        array[index] = e;
    }

    public boolean contains(Element e)
    {
        boolean b = false;
        for( int i = 0; i < size; i++)
        {
            if(array[i].equals(e))//应该使用值比较，而非if(data[i]==e)
            {
                b = true;
                break;
            }

        }
        return b;
    }

    public int find(Element e)
    {
        int index = -1;
        for(int i = 0; i < size; i++)
        {
            if(array[i].equals(e))//Byb 把所有的==都改成.equals(e)
            {
                index = i;
                break;
            }
        }
        return index;
    }

    public Array<Integer> findAll(Element e)
    {
        Array<Integer> index = new Array<>();
        for(int i = 0, index_i = 0; i < this.size; i++)
        {
            if(array[i].equals(e))//Byb 这个是Element[],所以可以直接用index调用，即array[i]. 但是line 145就是index_array.add(index,e)了，因为那个是Array<Integer>
            {
                index.add(index_i,i);
                index_i++;
            }
        }
        if(index.getSize()==0)
        {
            index.add(0,-1);
        }
        return index;
    }

    public Element get(int index)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException(String.format("Index %d is illegal",index));
        }
        return array[index];
    }

    public Element getLast()
    {
        return get(size-1);//当size==0时，size-1 == -1，get()会报错. 但写成array[size-1]就不会检查这一点
    }

    public Element getFirst()
    {
        return get(0);
    }

    public int getCapacity()
    {
        return array.length;
    }

    public int getSize()
    {
        return size;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();//Notes: 注意不是String s = new String;
        s.append(String.format("Size = %d, Capacity = %d\n", size, array.length));
        s.append("[");
        for(int i = 0; i < size; i++)
        {
            s.append(array[i]);
            if( i != size-1)
            {
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();//Notes: s是StringBuilder类，所以也需要转换成String类
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


        Array<Integer> i =  my_array.findAll(100);
        for(Integer j = 0; j <i.getSize(); j++)
        {
            System.out.println(i.get(j));//0 3
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
