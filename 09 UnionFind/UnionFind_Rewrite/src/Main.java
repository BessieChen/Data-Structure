import java.util.Random;
//整个UF代码正确性已验证！通过547题。
/// Leetcode 547. Friend Circles
/// https://leetcode.com/problems/friend-circles/description/
public class Main {

    public static void testUF(UF uf, int m)
    {
        int size = uf.getSize();
        Random random = new Random();

        long startTime = System.nanoTime();

        for(int i = 0; i < m; i++)
        {
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.unionElements(a,b);
        }

        for(int i = 0; i < m; i++)
        {
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.isConnected(a,b);//不需要返回true还是false，我们只是测试时间
        }

        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1e9+" s");

    }
    public static void main(String[] args) {

        int size = 100000;
        int m = 100000;

        UF uf1 = new UnionFind1(size);
        UF uf2 = new UnionFind2(size);
        UF uf3 = new UnionFind3(size);
        UF uf4 = new UnionFind4(size);
        UF uf5 = new UnionFind5(size);
        UF uf6 = new UnionFind6(size);

        testUF(uf1, m);
        testUF(uf2, m);
        testUF(uf3, m);
        testUF(uf4, m);
        testUF(uf5, m);
        testUF(uf6, m);
    }
}

/*

/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=55738:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/UnionFind_Rewrite/out/production/UnionFind_Rewrite Main
2.877158127 s
10.284255921 s
0.01647196 s
0.012324506 s
0.00991502 s
0.011974797 s

Process finished with exit code 0

*/

/*
有时候uf1竟然会快于uf2
两个可能的原因：
1.
uf1的union函数中有一段：
for(int i = 0 ; i < id.length; i++)
        {
            if(find(id[i]) == qID)
            {
                id[i] = pID;
            }
        }
这里说的是，我们要把所有A元素所在集合的所有其他元素的id都改成B元素的id
因为这个是改变一串连续的空间
java的JDK有很好的优化对于这种连续数组的操作，所以很快

相比之下，uf2的find_parent操作中：

while(parent[i] != i)
{
   i = parent[i];
}
每次需要搜索不连续的：i = parent[i]，所以就慢了。

2.我们的测试数量int m等于uf的size：
这就导致了，我们很有可能union了几乎每一个元素
所以就会导致我们的树很高，每次while( p != parent[p]) 的判断也是需要判断很多次的。
最差的情况，树高到了成为了一个链表
也就导致了我们的O(h)的复杂度其实也是很高的

所以，一个优化的方法，也就是uf3，是去思考如何让树不那么高。
*/

