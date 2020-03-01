//
//  main.cpp
//  Stack_cpp
//
//  Created by 陈贝茜 on 2019/7/20.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#include <iostream>
#include "ArrayStack.h"
#include "LinkedListStack.h"

int main() {
    ArrayStack<int> *stack = new ArrayStack<int>();
    for(int i = 0; i < 5; ++i) {
        stack->push(i);
        stack->print();
    }
    cout << "stack->peek(): " << stack->peek() << endl;
    cout << "stack->pop(): " << stack->pop() << endl;
    stack->print();
    cout<<endl;
    
    LinkedListStack<int> *stack2 = new LinkedListStack<int>();
    for (int i = 0; i < 5; ++i) {
        stack2->push(i);
        stack2->print();
    }
    cout << "stack2->peek(): " << stack2->peek() << endl;
    cout << "stack2->pop(): " << stack2->pop() << endl;
    stack2->print();
    return 0;
}


/*
 Stack: size = 1, capacity = 10
 bottom [0] top
 Stack: size = 2, capacity = 10
 bottom [0, 1] top
 Stack: size = 3, capacity = 10
 bottom [0, 1, 2] top
 Stack: size = 4, capacity = 10
 bottom [0, 1, 2, 3] top
 Stack: size = 5, capacity = 10
 bottom [0, 1, 2, 3, 4] top
 stack->peek(): 4
 stack->pop(): 4
 Stack: size = 4, capacity = 10
 bottom [0, 1, 2, 3] top
 
 LinkedListStack size: 1
 top [0] bottom
 LinkedListStack size: 2
 top [1, 0] bottom
 LinkedListStack size: 3
 top [2, 1, 0] bottom
 LinkedListStack size: 4
 top [3, 2, 1, 0] bottom
 LinkedListStack size: 5
 top [4, 3, 2, 1, 0] bottom
 stack2->peek(): 4
 stack2->pop(): 4
 LinkedListStack size: 4
 top [3, 2, 1, 0] bottom
 Program ended with exit code: 0
 */
