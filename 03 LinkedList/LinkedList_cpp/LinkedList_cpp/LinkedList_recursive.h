//
//  LinkedList_recursive.h
//  LinkedList_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef LinkedList_recursive_h
#define LinkedList_recursive_h

using namespace std;

template<typename E>
class LinkedList_recursive
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
        
        Node(): e(nullptr), next(nullptr){}
        
        Node(Node *node):e(node->e), next(node->next){}
    };
    
private:
    Node *head;
    int size;
    
public:
    LinkedList_recursive()
    {
        head = nullptr;
        size = 0;
    }
    
    ~LinkedList_recursive()
    {
        destroy(head);
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
        cout<<"LinkedList recursive size: "<<size<<endl;
        cout<<"head -> ";
        Node *cur = head;
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
        if(index == 0)
        {
            head = new Node(e, head);
            size++;
            return;
        }
        add(head, index-1, e);
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
            Node *del = head;
            head = head->next;
            del->next = nullptr;
            size--;
            return del->e;
        }
        return remove(head, index-1);
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
        if(head == nullptr)
        {
            cout<<"Remove element " <<e<<" failed, LinkedList is empty.";
            return false;
        }
        
        if(head->e == e)
        {
            head = head->next;
            size--;
            return true;
        }
        return removeElement(head, e);
    }
    
    void set(int index, E newE)
    {
        assert(index >= 0 && index < size);
        set(head, index, newE);
    }
    
    bool setElement(E e, E newE)
    {
        Node *cur = head;
        return set(head, e, newE);
    }
    
    bool contains(E e)
    {
        Node *cur = head;
        return contains(head, e);
    }
    
    E get(int index)
    {
        assert(index >=0 && index < size);
        return get(head, index);
    }
    
private:
    void add(Node *node, int index, E e)
    {
        if(index == 0)
        {
            node->next = new Node(e, node->next);
            size++;
            return;
        }
        add(node->next, index-1, e);
    }
    
    E remove(Node *node, int index)
    {
        if(index == 0)
        {
            Node *del = node->next;
            node->next = del->next;
            del->next = nullptr;
            size--;
            return del->e;
        }
        return remove(node->next, index-1);
    }
    
    bool removeElement(Node *node, E e)
    {
        if(node->next == nullptr)
            return false;
        
        if(node->next->e == e)
        {
            Node *del = node->next;
            node->next = del->next;
            del->next = nullptr;
            size--;
            return true;
        }
        return removeElement(node->next, e);
    }
    
    void set(Node *node, int index, E newE)
    {
        if(index == 0)
        {
            node->e = newE;
            return;
        }
        
        set(node->next, index-1, newE);
    }
    
    bool setElement(Node *node, E e, E newE)
    {
        if(node == nullptr)
            return false;
        
        if(node->e == e)
        {
            node->e = newE;
            return true;
        }
        
        return set(node->next, e, newE);
    }
    
    bool contains(Node *node, E e)
    {
        if(node == nullptr)
            return false;
        if(node->e == e)
            return true;
        return contains(node->next, e);
    }
    
    E get(Node *node, int index)
    {
        if(index == 0)
            return node->e;
        
        return get(node->next, index-1);
    }
    
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

#endif /* LinkedList_recursive_h */
