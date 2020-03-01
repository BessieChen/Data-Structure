import java.util.TreeSet;

//第一版Union-Find：记录id
public class UnionFind1 implements UF //注意，里面的装的，不是Element！
{
    private int[] id;//索引值 <-> 第几个元素， 数组内容 <-> 该元素的id //注意不是private Element[] id;

    public UnionFind1(int size)
    {
        id = new int[size];

        for(int i = 0; i < size; i++)
        {
            id[i] = i;
        }
    }

    public UnionFind1()
    {
        this(10);
    }

    @Override
    public int getSize()
    {
        return id.length;
    }

    @Override
    public boolean isConnected(int a, int b)
    {
        if(id[a] == id[b])
            return true;
        else
            return false;
    }

    @Override
    public void unionElements(int a, int b)
    {
        if(!isConnected(a,b))
        {
            for(int i = 0; i < getSize(); i++)
            {
                if(id[i] == id[a])//任何和a的id相同的元素的id改成b的id
                {
                    id[i] = id[b];
                }
            }
        }
    }

    public int countUnion()//计算有多少个集合。
    {
        TreeSet<Integer> set = new TreeSet<>();
        for(int i = 0; i < id.length; i++)
        {
            set.add(id[i]);
        }
        return set.size();
    }

}
