//
//  main.cpp
//  Array_cpp
//
//  Created by 陈贝茜 on 2019/7/20.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#include <iostream>
#include "Array.h"
#include "Student.h"

using namespace std;
int main(int argc, const char * argv[]) {
    
    //method 1: 创造数组
    int arr1[10];
    int len = sizeof(arr1)/sizeof(int);
    for(int i = 0; i < len; i++)
    {
        arr1[i] = i;
    }
    
    for(int i = 0; i < len; i++)
    {
        cout<<arr1[i]<<" ";
    }
    cout<<endl;
    
    //method 2:
    int *arr2 = new int[13];
    int len2 = sizeof(arr2)/sizeof(int);
    cout<<sizeof(arr2)<<" ";//输出8？？？
    for(int i = 0; i < len2; i++)
    {
        arr2[i] = i+1;
    }
    
    for(int i = 0; i < len2; i++)
    {
        cout<<arr2[i]<<" ";
    }
    cout<<endl;
    delete []arr2;
    
    //method 3:
    int arr3[] = {5,6,7};
    int len3 = sizeof(arr3)/sizeof(int);
    for(int i = 0; i < len3; i++)
    {
        cout<<arr3[i]<<" ";
    }
    cout<<endl;
    
    arr3[0] = 100;
    for(int i = 0; i < len3; i++)
    {
        cout<<arr3[i]<<" ";
    }
    cout<<endl;
    
    Array<int> *array = new Array<int>(8);
    for (int i = 0; i < 10; ++i) {
        array->addLast(i);
        array->print();
    }
    
    cout<<endl;
    array->add(1,100);
    array->print();
    array->addFirst(-1);
    array->print();
    array->remove(2);
    array->print();
    array->removeElement(4);
    array->print();
    array->removeLast();
    array->print();
    array->removeFirst();
    array->print();
    for(int i = 0; i < 7; ++i) {
        array->removeFirst();
        array->print();
    }

    return 0;
}


//0 1 2 3 4 5 6 7 8 9
//8 1 2
//5 6 7
//100 6 7
//Array capacity: 8, size: 1
//[0]
//Array capacity: 8, size: 2
//[0, 1]
//Array capacity: 8, size: 3
//[0, 1, 2]
//Array capacity: 8, size: 4
//[0, 1, 2, 3]
//Array capacity: 8, size: 5
//[0, 1, 2, 3, 4]
//Array capacity: 8, size: 6
//[0, 1, 2, 3, 4, 5]
//Array capacity: 8, size: 7
//[0, 1, 2, 3, 4, 5, 6]
//Array capacity: 8, size: 8
//[0, 1, 2, 3, 4, 5, 6, 7]
//Array capacity: 16, size: 9
//[0, 1, 2, 3, 4, 5, 6, 7, 8]
//Array capacity: 16, size: 10
//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//
//Array capacity: 16, size: 11
//[0, 100, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//Array capacity: 16, size: 12
//[-1, 0, 100, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//Array capacity: 16, size: 11
//[-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//Array capacity: 16, size: 10
//[-1, 0, 1, 2, 3, 5, 6, 7, 8, 9]
//Array capacity: 16, size: 9
//[-1, 0, 1, 2, 3, 5, 6, 7, 8]
//Array capacity: 16, size: 8
//[0, 1, 2, 3, 5, 6, 7, 8]
//Array capacity: 16, size: 7
//[1, 2, 3, 5, 6, 7, 8]
//Array capacity: 16, size: 6
//[2, 3, 5, 6, 7, 8]
//Array capacity: 16, size: 5
//[3, 5, 6, 7, 8]
//Array capacity: 8, size: 4
//[5, 6, 7, 8]
//Array capacity: 8, size: 3
//[6, 7, 8]
//Array capacity: 4, size: 2
//[7, 8]
//Array capacity: 4, size: 1
//[8]
//Program ended with exit code: 0
