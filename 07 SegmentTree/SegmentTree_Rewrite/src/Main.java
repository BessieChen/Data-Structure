public class Main {

    public static void main(String[] args) {

        Integer[] nums = {-2,0,3,-5,2,-1};//基本数据

        //method 1:匿名类：
        SegmentTree<Integer> segTree = new SegmentTree<>(nums, new Merger<Integer>() { //todo 这里要记得声明new Merger <Integer>
            @Override
            public Integer merge(Integer a, Integer b) {
                return a+b;//自定义的。
            }
        });
        System.out.println(segTree);//基本数据是[-2,0,3,-5,2,-1], 线段树是：[-3, 1, -4, -2, 3, -3, -1, -2, 0, null, null, -5, 2, null, null, null, null, null, null, null, null, null, null, null]
        for(int i = 0; i < segTree.getSize(); i++)
        {
            segTree.set(i, i+1);
        }

        System.out.println(segTree);//基本数据是[1,2,3,4,5,6], 线段树是：[21, 6, 15, 3, 3, 9, 6, 1, 2, null, null, 4, 5, null, null, null, null, null, null, null, null, null, null, null]
        System.out.println(segTree.query(0, 2));//答案：6
        System.out.println(segTree.query(2, 5));//答案：18
        System.out.println(segTree.query(0, 5));//答案：21
        System.out.println(segTree.query(0, 0));//答案：1
        System.out.println(segTree.query(5, 0));//答案：21


        //method 2: lambda表达式
        SegmentTree<Integer> segTree2 = new SegmentTree<>(nums, (a,b) -> a+b );
        System.out.println(segTree2);//基本数据是[-2,0,3,-5,2,-1], 线段树是：[-3, 1, -4, -2, 3, -3, -1, -2, 0, null, null, -5, 2, null, null, null, null, null, null, null, null, null, null, null]

        System.out.println(segTree2.query(0, 2));//答案：1
        System.out.println(segTree2.query(2, 5));//答案：-1
        System.out.println(segTree2.query(0, 5));//答案：-3
        System.out.println(segTree2.query(0, 0));//答案：-2
        System.out.println(segTree2.query(5, 0));//答案：-3

    }
}


/*
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=60953:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/SegmentTree_Rewrite/out/production/SegmentTree_Rewrite Main
[-3, 1, -4, -2, 3, -3, -1, -2, 0, null, null, -5, 2, null, null, null, null, null, null, null, null, null, null, null]
[21, 6, 15, 3, 3, 9, 6, 1, 2, null, null, 4, 5, null, null, null, null, null, null, null, null, null, null, null]
6
18
21
1
21
[-3, 1, -4, -2, 3, -3, -1, -2, 0, null, null, -5, 2, null, null, null, null, null, null, null, null, null, null, null]
1
-1
-3
-2
-3

Process finished with exit code 0
*/