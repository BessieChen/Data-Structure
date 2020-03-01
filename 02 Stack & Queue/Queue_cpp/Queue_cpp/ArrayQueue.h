//
//  ArrayQueue.h
//  Queue_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef ArrayQueue_h
#define ArrayQueue_h

#include "Array.h"
#include "Queue.h"

template<typename E>
class ArrayQueue: public Queue<E>
{
public:
    ArrayQueue(int cap)
    {
        array = new Array<E>(cap);
    }

    ArrayQueue()
    {
        array = new Array<E>(10);
    }

    ~ArrayQueue()
    {
        //delete[] array;
        array = nullptr;
    }

    void print()
    {
        cout<<"Queue capacity: "<<array->getCapacity()<<", size: "<<getSize()<<endl;
        cout<<" front [";
        for(int i = 0; i < getSize()-1; i++)
        {
            cout<<array->get(i)<<", ";
        }
        cout<<array->get(getSize()-1)<<"] tail"<<endl;
    }
    
    int getSize()
    {
        return array->getSize();
    }

    bool isEmpty()
    {
        return array->isEmpty();
    }

    void enqueue(E e)
    {
        array->addLast(e);
    }

    E dequeue()
    {
        return array->removeFirst();
    }

    E getFront()
    {
        return array->getFirst();
    }


private:
    Array<E> *array;
};

#endif /* ArrayQueue_h */



//
////
//// Created by hpf on 18-5-3.
////
//
//#ifndef DATASTRUCTURE_ARRAYQUEUE_H
//#define DATASTRUCTURE_ARRAYQUEUE_H
////
//#include "Queue.h"
//#include "Array.h"
//
//template<class T>
//class ArrayQueue : public Queue<T> {
//public:
//    ArrayQueue() {
//        array = new Array<T>(10);
//    }
//
//    ArrayQueue(int capacity) {
//        array = new Array<T>(capacity);
//    }
//
//    ~ArrayQueue() {
//        delete[] array;
//        array = nullptr;
//    }
//
//    int getSize() {
//        return array->getSize();
//    }
//
//    T dequeue() {
//        return array->removeFirst();
//    }
//
//    T getFront() {
//        return array->getFirst();
//    }
//
//    void enqueue(T e) {
//        array->addLast(e);
//    }
//
//    bool isEmpty() {
//        return array->isEmpty();
//    }
//
//    /**
//     * 打印数组的所有元素
//     */
//    void print() {
//        std::cout << "Queue: size = " << array->getSize() << ", capacity = " << array->getCapacity() << std::endl;
//        std::cout << "front ";
//        array->print();
//        std::cout << " tail" << std::endl;
//    }
//
//private:
//    Array<T> *array;
//};
//
//#endif //DATASTRUCTURE_ARRAYQUEUE_H
