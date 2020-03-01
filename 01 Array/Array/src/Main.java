public class Main {

    public static void main(String[] args) {
        // write your code here
        int[] arr = new int[10];//you must declare length of the int array.
        for(int i = 0; i < arr.length; i++)
        {
            arr[i] = i;
        }

        int[] scores = new int[]{100,99,66};
        for(int i = 0; i < scores.length; i++)
        {
            System.out.println(scores[i]);
        }

        for(int i : scores)
        {
            System.out.println(i);
        }

        //索引可以有语意，也可以没有语意。一般来说我们用array是当它的index是有语意的。index没有语意时，最好是其他数据结构。
        //但是，并不是所有情况下，index有语意时都适合用array，因为index的数字可能很大。
        //例如身份证有18位数，可是我们只需要其中的几个身份证的信息，没必要开辟这么大空间，并且index也不可能做到18位数。
        //接下来要做的：将java自己的静态数组，二次封装为我们的动态数组。
        //要实现的功能：增删改查。
    }
}
