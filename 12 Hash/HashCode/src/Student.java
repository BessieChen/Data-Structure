public class Student {

    int grade;
    int cls;
    String firstName;
    String lastName;

    Student(int grade, int cls, String firstName, String lastName){
        this.grade = grade;
        this.cls = cls;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public int hashCode()//这里我们没有担心溢出是因为，就算溢出，也是从正数到负数，我们不担心负数，只需要hash值不相同就好。所以这里对于溢出不做特殊处理。
    {
        int B = 31;//这个是随意选的。
        int hash = 0;
        hash = hash * B + ((Integer)grade).hashCode();//这里的hashCode就相当于之前的 [(xx % M) * B + yy] % M * B
        hash = hash * B + ((Integer)cls).hashCode();
        hash = hash * B + firstName.toLowerCase().hashCode();//也可以不要toLowerCase(),取决于你的业务。
        hash = hash * B + lastName.toLowerCase().hashCode();
        return hash;
    }

    //判断两个对象是否相等。
    @Override
    public boolean equals(Object o)
    {//注意，传进来的是Object类型，而不是Student类
        if(this == o)
            return true;

        if(o == null)
            return false;

        if(getClass() != o.getClass())
            return false;

        Student another = (Student)o;
        return this.grade == another.grade &&
                this.cls == another.cls &&
                this.firstName.toLowerCase().equals(another.firstName.toLowerCase()) &&
                this.lastName.toLowerCase().equals(another.lastName.toLowerCase());
    }
}
/*
* 这里的equals的作用是：
* 当两个object a和b，当hash(a) == hash(b)的时候，是不是真的a == b
* 什么是真的等于：
* 1.引用（地址）相同： this == o
* 2.如果引用（地址）不相同，看值是不是相同。
* */