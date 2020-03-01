public class Student {
    private String name;
    private int score;

    public Student(String name, int score)
    {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString()
    {
        return String.format("Student(name: %s, score: %d)\n",name,score);
    }

    public static void main(String[] args)
    {
        Array<Student> student_array = new Array<Student>();//默认了Array的容量capacity=10
        student_array.addFirst(new Student("bob",100));
        student_array.addFirst(new Student("charlie",90));
        student_array.addFirst(new Student("alice",80));

        System.out.print(student_array);//TODO


    }
}

// 输出结果是Array.toString() + Student.toString()!!
//        Size = 3, Capacity = 10
//        [Student(name: alice, score: 80)
//        ,Student(name: charlie, score: 90)
//        ,Student(name: bob, score: 100)
//        ]

