//
//  LinkedListStack.h
//  Stack_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef LinkedListStack_h
#define LinkedListStak ck_h

#include "LinkedList_dummy.h"

using namespace std;

template<typename E>
class LinkedListStack : public Stack<E>
{
private:
    LinkedList_dummy<E> *linkedlist;//别忘了这个是指针！
    
public:
    LinkedListStack()
    {
        linkedlist = new LinkedList_dummy<E>();
    }
    
    int getSize()
    {
        return linkedlist->getSize();
    }
    
    bool isEmpty()
    {
        return linkedlist->isEmpty();
    }
    
    void push(E e)
    {
        linkedlist->addFirst(e);
    }
    
    E pop()
    {
        return linkedlist->removeFirst();
    }
    
    E peek()
    {
        return linkedlist->get(0);
    }
    
    void print()
    {
        cout<<"LinkedListStack size: "<<linkedlist->getSize()<<endl;
        cout<<"top [";
        for(int i = 0; i < linkedlist->getSize()-1; i++)
        {
            cout<<linkedlist->get(i)<<", ";
        }
        cout<<linkedlist->get(linkedlist->getSize()-1)<<"] bottom"<<endl;
    }
    
};

#endif /* LinkedListStack_h */
