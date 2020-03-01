public interface UF //注意，里面的装的，不是Element！
{
    int getSize();
    boolean isConnected(int a, int b);
    void unionElements(int a, int b);
}
