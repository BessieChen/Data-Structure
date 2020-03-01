//
//  Array.h
//  Array_cpp
//
//  Created by 陈贝茜 on 2019/7/20.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef Array_h
#define Array_h

#include "cassert"


using namespace std;
template<typename E>
class Array
{
private:
    E *array;
    int size;
    int capacity;
    
    
    void resize(int newCap)
    {
        E *newArr = new E[newCap];
        for(int i = 0; i < size; i++)
        {
            newArr[i] = array[i];
        }
        array = newArr;
        capacity = newCap;
    }
    
public:
    
    //------------ 构造函数 ---------------
    Array(int cap)
    {
        array = new E[cap];
        size = 0;
        capacity = cap;
    }
    
    Array()
    {
        int cap = 10;
        array = new E[cap];
        size = 0;
        capacity = cap;
    }
    
    ~Array()
    {
        delete []array;
    }
    
    //------------ 基本函数 ---------------
    int getSize()
    {
        return size;
    }
    
    bool isEmpty()
    {
        return size == 0;
    }
    
    int getCapacity()
    {
        return capacity;
    }
    
    void print()
    {
        cout<<"Array capacity: "<<capacity<<", size: "<<size<<endl;
        cout<<" [";
        for(int i = 0; i <= size-2; i++)
        {
            cout<<array[i]<<", ";
        }
        cout<<array[size-1]<<"] "<<endl;
    }
    
    //------------ 增删改查 ---------------
    void add(int index, E e)
    {
        assert(index >= 0 && index <= size);
        
        if(size + 1 > capacity)
        {
            resize(capacity * 2);
        }
        
        for(int i = size; i >= index+1; i--)
        {
            array[i] = array[i-1];
        }

        array[index] = e;
        size++;
    }
    
    void addFirst(E e)
    {
        add(0, e);
    }
    
    void addLast(E e)
    {
        add(size, e);
    }
    
    E remove(int index)
    {
        assert(index >= 0 && index <= size-1);
        E ret = array[index];
        for(int i = index; i <= size-1; i++)
        {
            array[i] = array[i+1];
        }
        size--;
        if(size <= capacity/4 && size/2 >= 1)
        {
            resize(capacity/2);
        }
        return ret;
    }
    
    E removeFirst()
    {
        return remove(0);
    }
    
    E removeLast()
    {
        return remove(size-1);
    }
    
    int getIndex(E e)//给定E e，找到其所在index
    {
        for(int i = 0; i < size; i++)
        {
            if(array[i] == e)
                return i;
        }
        return -1;
    }
    
    void removeElement(E e)
    {
        int index = getIndex(e);
        if(index != -1)
        {
            remove(index);
        }
    }
    
    bool contains(E e)
    {
        int i = 0;
        while(i < size)
        {
            if(array[i] == e)
                return true;
            i++;
        }
        return false;
    }
    
    E get(int index)
    {
        assert(index >= 0 && index <= size-1);
        return array[index];
    }
    
    E getFirst()
    {
        return get(0);
    }
    
    E getLast()
    {
        return get(size-1);
    }
    
    void set(int index, E newE)
    {
        assert(index >= 0 && index <= size-1);
        array[index] = newE;
    }
    
};

#endif /* Array_h */
