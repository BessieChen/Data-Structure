public class Student
{
    /*
    * 目录：
    * 1.之前我们学过每个类都可以Override最祖先类的toString()
    *   其实还有其他函数也可以override，例如接下来要介绍的：
    *   -- hashCode()
    *   -- equals()
    *
    * 2.hashCode()需要注意的事情：
    *   2.1调用：
    *      --如果是基础类型，int，double，一定要记得转换成相应的 对象类型 Integer, Double，并且记得有两对括号 ( (Integer) score ).hashCode()
    *      --如果是对象类型，String, Integer, Double直接用就好了： xxx.hashCode()
    *      --如果是自定义的对象，例如Student，也是直接用：object.hashCode()
    *   2.2默认hashCode(), 使用地址，所以如果即便两个内容都一样的object也会因为地址不同，从而它们两个的hashCode()不同。
    *      这就为什么，我们有时候会override hashCode()
    *   2.3自定义hashCode():
    *      --如果是基础类型，java自带的对象类型，eg. String：[(xx % M) * B + yy] % M * B， 例如可以有 B = 26 代表26个字母， M一般是素数
    *        不过注意，一般基础类型 and java自带的对象类型 也没必要自己自定义hashCode()
    *      --如果是自定义的类，例如Student，我们能做的：
    *        将Student类里面的每个成员变量，score，cls，等等。它们的hashCode()给汇总
    *        怎么汇总？通过 乘 B 再加上变量的hashCode()
    *        所以你看到了，这里计算自定义的hashCode()就没有出现那个素数 M
    *
    * 3.equals()需要注意的：
    *   -- 参数是Object而不是Student
    *   -- 判断顺序： 1. 是否能够简单判断？如果简单判断是==的，则return true。
    *                2.剩下的是：不能简单判断的 or 简单判断!=的
    *                 -- 是否参数对象obj为空？ and 是否this为空？如果不为空，继续受测验
    *                 -- 是否类型相同，用祖先类的getClass() 如果相等，继续受测验
    *                 -- 走到这一步，说明类型相同，将参数对象obj强制转换成 此class的对象
    *                    成员变量以及比较，int/double 用 == 以及 String 用 .equals()，取逻辑与&&
    *                 -- 如果都满足，则return true
    *
    *
    * */

    private int score;
    private int cls;
    private String firstName;
    private String lastName;

    public Student(int score, int cls, String firstName, String lastName)
    {
        this.score = score;
        this.cls = cls;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public int hashCode()
    {
        int B = 31;//随意选的
        int hash = 0;//记得这个初始的hash == 0

        hash = (hash * B) + ((Integer)score).hashCode();
        hash = (hash * B) + ((Integer)cls).hashCode();
        hash = (hash * B) + firstName.toLowerCase().hashCode();//也可以不要toLowerCase(),取决于你的业务。
        hash = (hash * B) + lastName.toLowerCase().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj)//注意这里的参数是Object而不是Student
    {
        if(this == obj) return true;

        if(obj == null) return false;

        if(getClass() != obj.getClass()) return false;

        Student another = (Student) obj;
        return (another.score == this.score) &&
                (another.cls == this.cls) &&
                (another.firstName.equals(this.firstName)) &&
                (another.lastName.equals(this.lastName));
    }

}
