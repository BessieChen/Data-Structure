//
//  loopQueue_full.h
//  Queue_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef loopQueue_full_h
#define loopQueue_full_h

#include "Queue.h"

using namespace std;
//如果需要数组的全部元素都能使用上，则需要手动添加一个size, 多维护一个变量
template<typename E>
class loopQueue_full: public Queue<E>
{
private:
    E *array;
    int size;
    int front;
    int tail;
    int capacity;
    
public:
    loopQueue_full(int cap)
    {
        capacity = cap;
        array = new E[capacity];
        size = 0;
        front = 0;
        tail = 0;
    }
    
    loopQueue_full()
    {
        capacity = 10;
        array = new E[capacity];
        size = 0;
        front = 0;
        tail = 0;
    }
    
    int getSize()
    {
        return size;
    }
    
    int getCapacity()
    {
        return capacity;
    }
    
    bool isEmpty()
    {
        return size == 0;
    }
    
    void print()
    {
        cout<<"Loop Queue full capacity: "<<capacity<<", size: "<<size<<endl;
        cout<<" front [";
        for(int i = 0; i < size-1; i++)
        {
            cout<<array[(front + i) % length()]<<", ";
        }
        cout<<array[ (front + size - 1) % length() ]<<"] tail"<<endl;
    }
    
    void enqueue(E e)
    {
        if(size >= capacity)
        {
            resize(capacity * 2);
        }
        
        array[tail] = e;
        tail = (tail + 1) % length();
        size++;
    }
    
    E dequeue()
    {
        E ret = array[front];
        front = (front + 1) % length();
        size--;
        
        if(size <= capacity/4 && capacity/2 >= 1)
        {
            resize(capacity / 2);
        }
        
        return ret;
    }
    
    E getFront()
    {
        return array[front];
    }
    
private:
    int length()
    {
        return capacity;
    }
    
    void resize(int newCap)
    {
        E *newArr = new E[newCap];
        for(int i = 0; i < size; i++)
        {
            newArr[i] = array[(front + i) % length()];
        }
        front = 0;
        tail = size;
        capacity = newCap;
        array = newArr;
    }
};

#endif /* loopQueue_full_h */
