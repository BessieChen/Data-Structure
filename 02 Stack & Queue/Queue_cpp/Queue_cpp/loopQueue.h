//
//  loopQueue.h
//  Queue_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef loopQueue_h
#define loopQueue_h

#include "Queue.h"

using namespace std;

template<class E>
class loopQueue : public Queue<E>
{
private:
    E *array;
    int front;
    int tail;
    int capacity;
    
    int length()
    {
        return capacity + 1;
    }
    
    void resize(int newCap)
    {
        E *newArr = new E[newCap + 1];
        for(int i = 0; i < getSize(); i ++)
        {
            newArr[i] = array[(front + i) % length()];
        }
        
        tail = getSize();
        front = 0;
        capacity = newCap;
        array = newArr;
    }
    
public:
    loopQueue(int cap)
    {
        array = new E[cap+1];
        front = 0;
        tail = 0;
        capacity = cap;
    }
    
    loopQueue()
    {
        capacity = 10;
        array = new E[capacity + 1];
        front = 0;
        tail = 0;
        
    }
    
    ~loopQueue()
    {
        delete[] array;
        array  = nullptr;
    }
    
    
    //----------- 辅助函数 -----------
    int getSize()
    {
        return (tail - front + length()) % length();//todo 这个简化我很喜欢。
    }
    
    int getCapacity()
    {
        return capacity;
    }
    
    //----------- 基本函数 -----------
    bool isEmpty()
    {
        return front == tail;
    }
    
    void print()
    {
        int size = getSize();
        cout<<"Loop Queue capacity: "<<capacity<<", size: "<<size<<endl;
        cout<<" front [";
        for(int i = 0; i < getSize()-1; i++)
        {
            cout<<array[(front + i) % length()]<<", ";
        }
        cout<<array[(front + size - 1) % length()]<<"] tail"<<endl;
    }
    
    //----------- 增删改查 -----------
    
    void enqueue(E e)
    {
        int size = getSize();
        if(size + 1 == capacity)
        {
            resize(capacity * 2);
        }
        
        array[tail] = e;
        tail = (tail + 1) % length();
    }
    
    E dequeue()
    {
        E ret = array[front];
        front = (front + 1) % length();
        
        if(getSize() <= capacity / 4 && capacity/2 >= 1)
        {
            resize(capacity / 2);
        }
        return ret;
    }
    
    E getFront()
    {
        return array[front];
    }
    
};

#endif /* loopQueue_h */
