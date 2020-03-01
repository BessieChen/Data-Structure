//
//  ArrayStack.h
//  Stack_cpp
//
//  Created by 陈贝茜 on 2019/7/20.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef ArrayStack_h
#define ArrayStack_h

#include "Array.h"
#include "Stack.h"

using namespace std;

template<typename E>
class ArrayStack: public Stack<E>
{
private:
    Array<E> *array;
    
public:
    
    ArrayStack(int cap)
    {
        array = new Array<E>(cap);//这个<E>不能少！
    }
    
    ArrayStack()
    {
        array = new Array<E>();
    }
    
    ~ArrayStack()
    {
        delete[] array;
    }
    
    int getSize()
    {
        return array->getSize();
    }
    
    bool isEmpty()
    {
        return array->isEmpty();
    }
    
    void push(E e)
    {
        array->addLast(e);
    }
    
    E pop()
    {
        return array->removeLast();
    }
    
    E peek()
    {
        return array->getLast();
    }
    
    void print() {
    
        cout << "Stack: size = " << array->getSize() << ", capacity = " << array->getCapacity() << std::endl;
        cout<<"bottom [";
        for(int i = 0; i <= array->getSize()-2; i++)
        {
            cout<<array->get(i)<<", ";
        }
        cout<<array->get(array->getSize()-1)<<"] top"<<endl;
        
    }
};

#endif /* ArrayStack_h */
