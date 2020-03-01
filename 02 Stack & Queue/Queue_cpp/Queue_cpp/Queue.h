//
//  Queue.h
//  Queue_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef Queue_h
#define Queue_h

template<class E>
class Queue{
public:
    int getSize();
    bool isEmpty();
    void enqueue(E e);
    E dequeue();
    E getFront();
};

#endif /* Queue_h */
