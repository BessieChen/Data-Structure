//
//  LinkedList_recursive_dummy.h
//  LinkedList_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef LinkedList_recursive_dummy_h
#define LinkedList_recursive_dummy_h

using namespace std;

template<typename E>
class LinkedList_recursive_dummy
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
    LinkedList_recursive_dummy()
    {
        dH = new Node();
        size = 0;
    }
    
    ~LinkedList_recursive_dummy()
    {
        destroy(dH->next);
        delete dH;
    }
    
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
        cout<<"LinkedList recursive dummy size: "<<size<<endl;
        cout<<"head -> ";
        Node *cur = dH->next;
        while(cur != nullptr)
        {
            cout<<cur->e<<" -> ";
            cur = cur->next;
        }
        cout<<" NULL"<<endl;
    }
    
    void add(int index, E e)
    {
        assert(index >= 0 && index <= size);
        add(dH, index, e);
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
        return remove(dH, index);
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
        return removeElement(dH, e);
    }
    
    void set(int index, E newE)
    {
        assert(index >= 0 && index < size);
        set(dH->next, index, newE);
    }
    
    bool setElement(E e, E newE)
    {
        return setElement(dH->next, e, newE);
    }
    
    bool contains(E e)
    {
        return contains(dH->next, e);
    }
    
    E get(int index)
    {
        assert(index >= 0 && index < size);
        return get(dH->next, index);
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
        //不需要下面这一句，因为不存在第一次传进来的 就是nullptr，因为第一次传进来的是dH，也只可能是dH->next == nullptr;
//        if(node == nullptr)
//            return false;
        
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
        {
            return false;
        }
        
        if(node->e == e)
        {
            node->e = newE;
            return true;
        }
        
        return setElement(node->next, e, newE);
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
        if(node == nullptr)//假如说第一次进destroy的node就是空
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
#endif /* LinkedList_recursive_dummy_h */
