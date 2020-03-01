//
//  LinkedListQueue.h
//  Queue_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef LinkedListQueue_h
#define LinkedListQueue_h

using namespace std;

template<typename E>
class LinkedListQueue : public Queue<E>
{
    struct Node
    {
        E e;
        Node *next;
        
        Node(E e, Node *next)
        {
            this->e = e;
            this->next = next;
        }
        
        Node(E e)
        {
            this->e = e;
            this->next = nullptr;
        }
        
        Node()
        {
            this->next = nullptr;
        }
        
        Node(Node *node)
        {
            this->e = node->e;
            this->next = node->next;
        }
    };
    
private:
    Node *front;//这里可以不需要设置成dummyHead，因为我们第一次add()一定是添加在最开头，所以每次都是index == 0的那种特殊情况。
    Node *tail;
    int size;
    
public:
    LinkedListQueue()
    {
        front = nullptr;
        tail = nullptr;
        size = 0;
    }
    
    int getSize()
    {
        return size;
    }
    
    bool isEmpty()
    {
        return size == 0;
    }
    
    void enqueue(E e)
    {
        if(size == 0)
        {
            front = new Node(e, front);
            tail = front;
            size++;
            return;
        }
        
        tail->next = new Node(e);
        tail = tail->next;
        size++;
    }
    
    E dequeue()
    {
        assert(size > 0);
        Node *del = front;
        front = del->next;
        del->next = nullptr;
        size--;
        E ret = del->e;
        if(size == 0)
        {
            front = tail = nullptr;
        }
        return ret;
    }
    
    E getFront()
    {
        return front->e;
    }
    
    void print()
    {
        cout<<"LinkedListQueue size: "<<size<<endl;
        cout<<" front -> ";
        Node *cur = front;
        while(cur != nullptr)
        {
            cout<<cur->e<<" -> ";
        }
        cout<<"NULL"<<endl;
    }
};


#endif /* LinkedListQueue_h */
