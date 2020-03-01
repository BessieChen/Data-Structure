import java.util.HashMap;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        int a = 42;
        System.out.println(((Integer)a).hashCode());

        int b = -42;
        System.out.println(((Integer)b).hashCode());

        double c = 3.1415926;
        System.out.println(((Double)c).hashCode());

        String d = "imooc";
        System.out.println(d.hashCode());
        System.out.println();

        System.out.println(Integer.MAX_VALUE);//2147483647
        System.out.println(Integer.MAX_VALUE + 1);//这一句 想说的是 Max_Value + 1就溢出了，就是会循环然后变成负数：-2147483648，即最小的负数
        System.out.println();

        Student student = new Student(3, 2, "Bobo", "Liu");
        System.out.println(student.hashCode());

        Student student2 = new Student(3, 2, "Bobo", "Liu");
        System.out.println(student2.hashCode());

        HashSet<Student> set = new HashSet<>();//set里面装的都是Student
        set.add(student);//HashSet是用add()

        HashMap<Student, Integer> scores = new HashMap<>();//每一个Student对应一个score
        scores.put(student, 100);//HashSet是用put()
    }
}

//上面的student1和student2想表达的东西是：
//1. 首先你可以发现，stu1和stu2的内容是一样的，但是它们指向的物理地址是不同的。
//2. 然后其实object对象，也就是用自定义class的对象，java也能有hashCode()，这个hashCode是基于地址创建的。
//3. 所以如果我们不创建自定义的hashCode()来override java的hashCode()，那么stu1和stu2的hashCode是不相等的，因为它们的地址不同
//4. 但是如果我们有自定义的hashCode()，而我们写的hashCode()又只是单单关注值，所以stu1和stu2的自定义的hashCode是相等的

/*
* 需要额外补充的：
* 1. HashMap和HashSet，其实你就把它当成 Map 和 Set 看待就好了，正如TreeMap和TreeSet
* 2. 只不过HashMap是TreeMap数组，意思是：将一个一个TreeMap通过hashCode()存储在了数组的不同索引上。
* 3. java8之后
*     HashMap和HashSet，
*     如果数组的某个索引，存储的节点不多，那么存储方式是链表。
*     当哈希冲突达到一定程度，每一个索引从链表 转换成 红黑树。
* */