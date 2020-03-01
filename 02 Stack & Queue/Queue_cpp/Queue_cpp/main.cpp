//
//  main.cpp
//  Queue_cpp
//
//  Created by 陈贝茜 on 2019/7/21.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#include <iostream>
#include "Queue.h"
//#include "ArrayQueue.h"
#include "loopQueue.h"
#include "loopQueue_full.h"
#include "LinkedListQueue.h"

using namespace std;

template<typename T>
double testQueue(T *queue, int opCount) {
    clock_t startTime = clock();
    srand(66);
    for (int i = 0; i < opCount; ++i) {
        queue->enqueue(rand());
    }
    for (int j = 0; j < opCount; ++j) {
        queue->dequeue();
    }
    clock_t endTime = clock();
    return double(endTime - startTime) / CLOCKS_PER_SEC;
}

int main() {
    //    ArrayQueue<int> *queue = new ArrayQueue<int>();
    //    for (int i = 0; i < 10; ++i) {
    //        queue->enqueue(i);
    //        queue->print();
    //        if (i % 3 == 2) {
    //            queue->dequeue();
    //            queue->print();
    //        }
    //    }
    
        loopQueue<int> *queue2 = new loopQueue<int>();
        for (int i = 0; i < 15; ++i) {
            queue2->enqueue(i);
            queue2->print();
            if (i % 3 == 2) {
                queue2->dequeue();
                queue2->print();
            }
        }
    
        cout<<endl;
    
        loopQueue_full<int> *queue3 = new loopQueue_full<int>();
        for (int i = 0; i < 15; ++i) {
            queue3->enqueue(i);
            queue3->print();
            if (i % 3 == 2) {
                queue3->dequeue();
                queue3->print();
            }
        }
    
    int opCount = 100000;
    //ArrayQueue<int> *arrayqueue = new ArrayQueue<int>();
    //cout << "ArrayQueue time: " << testQueue(arrayqueue, opCount) << " s" << endl;
    loopQueue<int> *loopqueue = new loopQueue<int>();
    cout << "LoopQueue time: " << testQueue(loopqueue, opCount) << " s" << endl; //0.006448 s
    LinkedListQueue<int> *linkedListQueue = new LinkedListQueue<int>();
    cout << "LinkedListQueue time: " << testQueue(linkedListQueue, opCount) << " s" << endl; //0.007061 s
    return 0;
}

/*
 Loop Queue capacity: 10, size: 1
 front [0] tail
 Loop Queue capacity: 10, size: 2
 front [0, 1] tail
 Loop Queue capacity: 10, size: 3
 front [0, 1, 2] tail
 Loop Queue capacity: 5, size: 2
 front [1, 2] tail
 Loop Queue capacity: 5, size: 3
 front [1, 2, 3] tail
 Loop Queue capacity: 5, size: 4
 front [1, 2, 3, 4] tail
 Loop Queue capacity: 10, size: 5
 front [1, 2, 3, 4, 5] tail
 Loop Queue capacity: 10, size: 4
 front [2, 3, 4, 5] tail
 Loop Queue capacity: 10, size: 5
 front [2, 3, 4, 5, 6] tail
 Loop Queue capacity: 10, size: 6
 front [2, 3, 4, 5, 6, 7] tail
 Loop Queue capacity: 10, size: 7
 front [2, 3, 4, 5, 6, 7, 8] tail
 Loop Queue capacity: 10, size: 6
 front [3, 4, 5, 6, 7, 8] tail
 Loop Queue capacity: 10, size: 7
 front [3, 4, 5, 6, 7, 8, 9] tail
 Loop Queue capacity: 10, size: 8
 front [3, 4, 5, 6, 7, 8, 9, 10] tail
 Loop Queue capacity: 10, size: 9
 front [3, 4, 5, 6, 7, 8, 9, 10, 11] tail
 Loop Queue capacity: 10, size: 8
 front [4, 5, 6, 7, 8, 9, 10, 11] tail
 Loop Queue capacity: 10, size: 9
 front [4, 5, 6, 7, 8, 9, 10, 11, 12] tail
 Loop Queue capacity: 20, size: 10
 front [4, 5, 6, 7, 8, 9, 10, 11, 12, 13] tail
 Loop Queue capacity: 20, size: 11
 front [4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14] tail
 Loop Queue capacity: 20, size: 10
 front [5, 6, 7, 8, 9, 10, 11, 12, 13, 14] tail
 
 Loop Queue full capacity: 10, size: 1
 front [0] tail
 Loop Queue full capacity: 10, size: 2
 front [0, 1] tail
 Loop Queue full capacity: 10, size: 3
 front [0, 1, 2] tail
 Loop Queue full capacity: 5, size: 2
 front [1, 2] tail
 Loop Queue full capacity: 5, size: 3
 front [1, 2, 3] tail
 Loop Queue full capacity: 5, size: 4
 front [1, 2, 3, 4] tail
 Loop Queue full capacity: 5, size: 5
 front [1, 2, 3, 4, 5] tail
 Loop Queue full capacity: 5, size: 4
 front [2, 3, 4, 5] tail
 Loop Queue full capacity: 5, size: 5
 front [2, 3, 4, 5, 6] tail
 Loop Queue full capacity: 10, size: 6
 front [2, 3, 4, 5, 6, 7] tail
 Loop Queue full capacity: 10, size: 7
 front [2, 3, 4, 5, 6, 7, 8] tail
 Loop Queue full capacity: 10, size: 6
 front [3, 4, 5, 6, 7, 8] tail
 Loop Queue full capacity: 10, size: 7
 front [3, 4, 5, 6, 7, 8, 9] tail
 Loop Queue full capacity: 10, size: 8
 front [3, 4, 5, 6, 7, 8, 9, 10] tail
 Loop Queue full capacity: 10, size: 9
 front [3, 4, 5, 6, 7, 8, 9, 10, 11] tail
 Loop Queue full capacity: 10, size: 8
 front [4, 5, 6, 7, 8, 9, 10, 11] tail
 Loop Queue full capacity: 10, size: 9
 front [4, 5, 6, 7, 8, 9, 10, 11, 12] tail
 Loop Queue full capacity: 10, size: 10
 front [4, 5, 6, 7, 8, 9, 10, 11, 12, 13] tail
 Loop Queue full capacity: 20, size: 11
 front [4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14] tail
 Loop Queue full capacity: 20, size: 10
 front [5, 6, 7, 8, 9, 10, 11, 12, 13, 14] tail
 
 LoopQueue time: 0.006448 s
 LinkedListQueue time: 0.007075 s
 Program ended with exit code: 0
 */
