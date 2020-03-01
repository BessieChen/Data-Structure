//
//  main.cpp
//  LinkedList_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#include <iostream>
#include "LinkedList.h"
#include "LinkedList_dummy.h"
#include "LinkedList_recursive.h"
#include "LinkedList_recursive_dummy.h"

using namespace std;
int main(int argc, const char * argv[]) {
    LinkedList<int> *linkedList1 = new LinkedList<int>();
    for (int i = 0; i < 5; ++i) {
        linkedList1->addFirst(i);
        linkedList1->print();
    }
    linkedList1->add(2, 30);
    linkedList1->print();
    linkedList1->remove(2);
    linkedList1->print();
    linkedList1->removeFirst();
    linkedList1->print();
    linkedList1->removeLast();
    linkedList1->print();
    cout<<endl;
    
    LinkedList_dummy<int> *linkedList2 = new LinkedList_dummy<int>();
    for (int i = 0; i < 5; ++i) {
        linkedList2->addFirst(i);
        linkedList2->print();
    }
    linkedList2->add(2, 30);
    linkedList2->print();
    linkedList2->remove(2);
    linkedList2->print();
    linkedList2->removeFirst();
    linkedList2->print();
    linkedList2->removeLast();
    linkedList2->print();
    cout<<endl;
    
    LinkedList_recursive<int> *linkedList3 = new LinkedList_recursive<int>();
    for (int i = 0; i < 5; ++i) {
        linkedList3->addFirst(i);
        linkedList3->print();
    }
    linkedList3->add(2, 30);
    linkedList3->print();
    linkedList3->remove(2);
    linkedList3->print();
    linkedList3->removeFirst();
    linkedList3->print();
    linkedList3->removeLast();
    linkedList3->print();
    cout<<endl;
    
    LinkedList_recursive_dummy<int> *linkedList4 = new LinkedList_recursive_dummy<int>();
    for (int i = 0; i < 5; ++i) {
        linkedList4->addFirst(i);
        linkedList4->print();
    }
    linkedList4->add(2, 30);
    linkedList4->print();
    linkedList4->remove(2);
    linkedList4->print();
    linkedList4->removeFirst();
    linkedList4->print();
    linkedList4->removeLast();
    linkedList4->print();
    cout<<endl;
    return 0;
}


/*
 LinkedList size: 1
 head -> 0 -> NULL
 LinkedList size: 2
 head -> 1 -> 0 -> NULL
 LinkedList size: 3
 head -> 2 -> 1 -> 0 -> NULL
 LinkedList size: 4
 head -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList size: 5
 head -> 4 -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList size: 6
 head -> 4 -> 3 -> 30 -> 2 -> 1 -> 0 -> NULL
 LinkedList size: 5
 head -> 4 -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList size: 4
 head -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList size: 3
 head -> 3 -> 2 -> 1 -> NULL
 
 LinkedList dummy size: 1
 head -> 0 -> NULL
 LinkedList dummy size: 2
 head -> 1 -> 0 -> NULL
 LinkedList dummy size: 3
 head -> 2 -> 1 -> 0 -> NULL
 LinkedList dummy size: 4
 head -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList dummy size: 5
 head -> 4 -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList dummy size: 6
 head -> 4 -> 3 -> 30 -> 2 -> 1 -> 0 -> NULL
 LinkedList dummy size: 5
 head -> 4 -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList dummy size: 4
 head -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList dummy size: 3
 head -> 3 -> 2 -> 1 -> NULL
 
 LinkedList recursive size: 1
 head -> 0 -> NULL
 LinkedList recursive size: 2
 head -> 1 -> 0 -> NULL
 LinkedList recursive size: 3
 head -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive size: 4
 head -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive size: 5
 head -> 4 -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive size: 6
 head -> 4 -> 3 -> 30 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive size: 5
 head -> 4 -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive size: 4
 head -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive size: 3
 head -> 3 -> 2 -> 1 -> NULL
 
 LinkedList recursive dummy size: 1
 head -> 0 -> NULL
 LinkedList recursive dummy size: 2
 head -> 1 -> 0 -> NULL
 LinkedList recursive dummy size: 3
 head -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive dummy size: 4
 head -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive dummy size: 5
 head -> 4 -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive dummy size: 6
 head -> 4 -> 3 -> 30 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive dummy size: 5
 head -> 4 -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive dummy size: 4
 head -> 3 -> 2 -> 1 -> 0 -> NULL
 LinkedList recursive dummy size: 3
 head -> 3 -> 2 -> 1 -> NULL
 
 Program ended with exit code: 0
 */
