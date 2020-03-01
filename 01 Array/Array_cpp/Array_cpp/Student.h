//
//  Student.h
//  Array_cpp
//
//  Created by 陈贝茜 on 2019/7/20.
//  Copyright © 2019 Bessie Chen. All rights reserved.
//

#ifndef Student_h
#define Student_h

using namespace std;
class Student
{
private:
    string name;
    int score;
    
public:
    Student(string name = "", int score = 0)
    {
        this->name = name;
        this->score = score;
    }
    
    void print()
    {
        cout<<"Student name: "<<name<<", score: "<<score<<endl;
    }
};

#endif /* Student_h */
