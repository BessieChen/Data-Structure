//
//  LinkedList_dummy.h
//  LinkedList_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef LinkedList_dummy_h
#define LinkedList_dummy_h

using namespace std;

template<typename E>
class LinkedList_dummy
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
        
        Node(E e):e(e), next(nullptr){}

        Node(): next(nullptr){}
        
        Node(Node *node):e(node->e), next(node->next){}
    };
    
private:
    Node *dH;
    int size;
    
public:
    LinkedList_dummy()
    {
        dH = new Node();
        size = 0;
    }
    
    ~LinkedList_dummy()
    {
        destroy(dH->next);
        delete dH;
    }
    
    //------------ 基本 ------------
    int getSize()
    {
        return size;
    }
    
    bool isEmpty()
    {
        return size == 0;
    }
    
    void print()
    {
        cout<<"LinkedList dummy size: "<<size<<endl;
        cout<<"head -> ";
        Node *cur = dH->next;
        while(cur != nullptr)
        {
            cout<<cur->e<<" -> ";
            cur = cur->next;
        }
        cout<<"NULL"<<endl;
    }
    //------------ 增删改查 ------------
    void add(int index, E e)
    {
        assert(index >= 0 && index <= size);
        Node *cur = dH;
        for(int i = 0; i <= index - 1; i++)
            cur = cur->next;
        
    
        cur->next = new Node(e, cur->next);
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
        assert(index >= 0 && index < size);
        Node *cur = dH;
        for(int i = 0; i <= index-1; i++)
            cur = cur->next;
        Node *del = cur->next;
        cur->next = del->next;
        del->next = nullptr;
        size--;
        return del->e;
    }
    
    E removeFirst()
    {
        return remove(0);
    }
    
    E removeLast()
    {
        return remove(size - 1);
    }
    
    bool removeElement(E e)
    {
        Node *cur = dH;
        while(cur->next != nullptr)
        {
            if(cur->next->e == e)
            {
                Node *del = cur->next;
                cur->next = del->next;
                del->next = nullptr;
                size--;
                return true;
            }
            cur = cur->next;
        }
        return false;
    }
    
    void set(int index, E newE)
    {
        assert(index >= 0 && index < size);
        Node *cur = dH->next;
        for(int i = 0; i <= index-1; i++)
            cur = cur->next;
        cur->e = newE;
    }
    
    bool setElement(E e, E newE)
    {
        Node *cur = dH->next;
        while(cur != nullptr)
        {
            if(cur->e == e)
            {
                cur->e = newE;
                return true;
            }
            cur = cur->next;
        }
        return false;
    }
    
    bool contains(E e)
    {
        Node *cur = dH->next;
        while(cur != nullptr)
        {
            if(cur->e == e)
                return true;
            cur = cur->next;
        }
        return false;
    }
    
    E get(int index)
    {
        assert(index >= 0 && index < size);
        Node *cur = dH->next;
        for(int i = 0; i <= index-1; i++)
            cur = cur->next;
        return cur->e;
    }
    
private:
    void destroy(Node *node)
    {
        if(node == nullptr)
            return;
        
        if(node->next == nullptr)
        {
            delete node;
            size--;
            return;
        }
        
        destroy(node->next);
    }
};

#endif /* LinkedList_dummy_h */
