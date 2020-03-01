//
//  Stack.h
//  Stack_cpp
//
//  Created by 陈贝茜 on 2019/7/20.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef Stack_h
#define Stack_h

using namespace std;

template<typename E>
class Stack
{
public:
    int getSize();
    bool isEmpty();
    void push(E e);
    E pop();
    E peek();
};

#endif /* Stack_h */
