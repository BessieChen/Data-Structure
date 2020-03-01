public class test
{
    public void change(int i)
    {
        i = 9;
    }

    public void change_arr(int[] arr)
    {
        arr[0] = 9;
    }

    public static void main(String[] args)
    {
        int a = 10;
        int[] b = {1,2,3,4};
        test tt = new test();
        tt.change(a);
        tt.change_arr(b);
        System.out.println(a);
        System.out.println(b[0]);
    }
}

/*
下面的结果告诉我们：
int，double等基本类型传入函数的时候，传入的是值，所以int a没有被修改
int[]，object等数组，类对象，传入的也是值，不过这个值是地址的值，所以b[0]被成功修改！

结论：
1.传入的是object：例如root = new Node(), add(root,e)里面有一句node.right = new Node(e); root的right会被修改成e；注意，这里如果是node = new Node(e), 那么node就会变成灰色，因为你并没有用到它，只是将它赋了一个新的值
2.传入的是null：例如root = null，add(root,e)函数里面有一句node = new Node(e); 可是root依旧不会被改成这个new Node(e). 除非你在外面写了root = add(root,e);
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=56459:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/BST_Rewrite/out/production/BST_Rewrite test
10
9

Process finished with exit code 0
*/