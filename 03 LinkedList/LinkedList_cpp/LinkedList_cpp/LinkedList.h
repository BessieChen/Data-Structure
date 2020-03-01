//
//  LinkedList.h
//  LinkedList_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef LinkedList_h
#define LinkedList_h

using namespace std;

template<typename E>
class LinkedList
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
            next = nullptr;
        }
        
        Node(Node *node)
        {
            this->e = node->e;
            this->next = node->next;
        }
        
    };
    
private:
    Node *head;
    int size;
    
public:
    LinkedList()
    {
        head = nullptr;
        size = 0;
    }
    
    ~LinkedList()
    {
        destroy(head);
        
//        //method 2:
//        while( head != NULL){
//            Node *node = head;
//            head = head->next;
//            delete node;
//            count --;
//        }
//
//        assert( head == NULL && count == 0 );
    }
    
    //-------------  基本 ---------------
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
        cout<<"LinkedList size: "<<size<<endl;
        cout<<"head -> ";
        Node *cur = head;
        while(cur != nullptr)
        {
            cout<<cur->e<<" -> ";
            cur = cur->next;
        }
        cout<<"NULL"<<endl;
    }
    //-------------  增删改查 ---------------
    void add(int index, E e)
    {
        assert(index >= 0 && index <= size);
        if(index == 0)
        {
            head = new Node(e, head);
            size++;
            return;
        }
        Node *cur = head;
        for(int i = 0; i <= index-2; i++)
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
        if(index == 0)
        {
            E ret = head->e;
            head = head->next;
            size--;
            return ret;
        }
        Node *cur = head;
        for(int i = 0; i <= index-2 ; i++)
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
        return remove(size-1);
    }
    
    bool removeElement(E e)
    {
        Node *cur = head;
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
        Node *cur = head;
        for(int i = 0; i <= index-1; i++)
            cur = cur->next;
        cur->e = newE;
    }
    
    bool setElement(E e, E newE)
    {
        Node *cur = head;
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
    
    E get(int index)
    {
        assert(index >= 0 && index < size);
        if(index == 0) return head->e;
        Node *cur = head;
        for(int i = 0; i <= index-1; i++)
            cur = cur->next;
        return cur->e;
    }
    
    bool contains(E e)
    {
        Node *cur = head;
        while(cur != nullptr)
        {
            if(cur->e == e)
                return true;
            cur = cur->next;
        }
        return false;
    }
    
    
private:
    void destroy(Node node)
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

#endif /* LinkedList_h */
