//import java.util.Map; 注意不是用Map，是用TreeMap！
//这个Trie代码的func()都已经检查，代码正确。
import java.util.Stack;
import java.util.TreeMap;
public class Trie<Element>
{
    private class Node
    {
        public boolean isWord;
        public TreeMap<Character, Node> next;

        public Node(boolean isWord)
        {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node()
        {
            this(false);
        }
    }

    private Node root;
    private int size;//因为不像数组，我们可以直接用data.length作为size，这里的节点Node是灵活分散的，所以应该额外设置一个size变量

    public Trie()
    {
        root = new Node();
        size = 0;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int getSize()
    {
        return size;
    }

    //错误示范add()：忽略了存在char c，但是char c之前不是末尾单词。
/*    public void add(String word)//不是Character word！因为Character是字符，不是字符串！
    {
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
            {
                if(i == word.length()-1)
                {
                    cur.next.put(c, new Node(true));//错在这里：忽略了存在char c，但是char c之前不是末尾单词。
                }
                else
                {
                    cur.next.put(c, new Node());
                }
                size++;
            }
            cur = cur.next.get(c);
        }
    }*/

    public void add(String word)//不是Character word！因为Character是字符，不是字符串！
    {
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
            {
                cur.next.put(c, new Node());
                //错误：size++; 我一定是傻了，怎么是加一个char就size++呢？应该是加了一个word才size++!!
            }
            cur = cur.next.get(c);
            if(i == word.length()-1)
            {
                //错误：也不是每次到了word末尾，就在trie上size++，因为可能这个word已经在trie里面了！
                /*cur.isWord = true;
                size++;*/

                if(!cur.isWord)
                {
                    cur.isWord = true;
                    size++;
                }
            }
        }
    }

    //更好的方式add：那就是不需要判断 if(i == word.length()-1)
    public void add_2(String word)//不是Character word！因为Character是字符，不是字符串！
    {
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
            {
                cur.next.put(c, new Node());
                //错误：size++; 我一定是傻了，怎么是加一个char就size++呢？应该是加了一个word才size++!!
            }
            cur = cur.next.get(c);
        }
        //此时出来for loop的话cur已经是代表最后一个字母的节点了。
        if(!cur.isWord)
        {
            cur.isWord = true;
            size++;
        }
    }



    public boolean contains(String word)//已check，代码正确
    {
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
            {
                return false;
            }
            cur = cur.next.get(c);
        }
        //最后出来for loop时候，cur就是word的最后一个字母所在的节点。
        return cur.isWord;
    }

    public boolean isPerfix(String word)//已check，代码正确
    {
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
            {
                return false;
            }
            cur = cur.next.get(c);
        }
        //最后出来for loop时候，cur就是word的最后一个字母所在的节点。
        return true;
    }

    //略过，之前错误的代码：
    /*public void remove_old(String word)//正确性未检查
    {
        //1. 这个字符是 待删除字符word 的中间字母 && 这个字符不是其他单词的字母： 目标：删除这个字符。
        //2. 这个字符是 待删除字符word 的中间字母 && 这个字符是其他单词的字母：   目标：保留这个字符
        //3. 这个字符是 待删除字符word 的最后的字母 && 这个字符不是其他单词的字母：   目标：删除这个字符
        //4. 这个字符是 待删除字符word 的最后的字母 && 这个字符是其他单词的字母：   目标：保留这个字符，isWord == false

        //我想到的方法：那就是设计一个函数，叫private int countIsWord(Node node)，作用是计算以node节点为根节点的树上所有节点的isWord == true的个数，
        //也就是遍历这个node为根节点的树，看看这个树里面到底有多少个单词

        //所以对于上面的4种情况：正确性未检查。
//
//        * 1情况。 countIsWord() == 1 && index < word.length()-1; 处理：删除
//        * 2情况。 countIsWord() >= 2 && index < word.length()-1; 处理：保留
//        * 3情况。 countIsWord() == 1 && index < word.length()-1; 处理：删除
//        * 4情况。 countIsWord() >= 2 && index == word.length()-1; 处理：isWord == false
//

        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) != null)
            {
                int countWord = countIsWord(cur.next.get(c));
                if(countWord == 1)
                {
                    cur.next.remove(c);
                }
                else//countWord >= 2
                {
                    if(i == word.length()-1)//说明char c是最后一个字母，cur.next.get(c)是代表char c的节点。
                    {
                        cur.next.get(c).isWord = false;
                        size--;
                    }
                }
            }
            else
            {
                System.out.println("Remove failed, word doesn't exist.");
                return;//说明这个word单词都不存在。
            }
        }
    }

    private int countIsWord(Node node)
    {
        int sum = 0;
        if(node.isWord)
        {
            sum += 1;
        }

        for(Character key : node.next.keySet())
        {
            sum += countIsWord(node.next.get(key));
        }

        return sum;
    }*/

    public boolean remove(String word)
    {

        //我最开始的设想，有错误，你可以略过。
        /*
        //1. 这个字符是 待删除字符word 的中间字母 && 这个字符不是其他单词的字母： 目标：删除这个字符。
        //2. 这个字符是 待删除字符word 的中间字母 && 这个字符是其他单词的字母：   目标：保留这个字符
        //3. 这个字符是 待删除字符word 的最后的字母 && 这个字符不是其他单词的字母：   目标：删除这个字符
        //4. 这个字符是 待删除字符word 的最后的字母 && 这个字符是其他单词的字母：   目标：保留这个字符，isWord == false

        //我想到的方法：那就是设计一个函数，叫private int countIsWord(Node node)，作用是计算以node节点为根节点的树上所有节点的isWord == true的个数，
        //也就是遍历这个node为根节点的树，看看这个树里面到底有多少个单词

        //所以对于上面的4种情况：正确性未检查。

        * 1情况。 countIsWord() == 1 && index < word.length()-1; 处理：删除
        * 2情况。 countIsWord() >= 2 && index < word.length()-1; 处理：保留
        * 3情况。 countIsWord() == 1 && index < word.length()-1; 处理：删除
        * 4情况。 countIsWord() >= 2 && index == word.length()-1; 处理：isWord == false
        * */

        //以下正确性可保证。
        //后来补充：其实不需要想我上面说的那样，通过一个countIsWord实现，而是可以像以下，递归实现：
        /*
         * 1. 我们首先来到word的最后一个字符 -- 先看这个字符是否是isWord，如果是isWord，说明word是存在在trie里面的，否则，word根本就不存在也没有必要删除了
         * 2. 检查这个字符是不是其他单词的字母，方法，直接看这个字符 的next.size() == 0 吗
         *    --2.1如果等于0的话，说明这个字符后面什么都没有，说明这个字符，仅仅是word这个单词用到了，其他单词没有用到。所以连这个字符的.isWord都不需要设置为false，而是直接递归到这个字符的前一个node，将这个字符删除。
         *    --2.2如果不等于0，说明这个字符后面还有其他字符，这些字符属于其他单词，所以，我们直接将这个字符的isWord = false，
         *        然后退出remove函数，因为word前面的字符也不能删掉，因为这些字符是其他单词的字母，注意是以true返回，说明已经成功删除了。
         * 3. 现在是从2.1过来，我们到了word的倒数第二个字母，因为最后一个已经被删了。现在，同样，判断这个倒数第二个（现在是最后一个字符了）的的next.size() == 0 && 这个字符.isWord == false 吗
         *    --3.1如果 ( 这个字符next.size() == 0 && 这个字符.isWord == false ) 的话，即1/4种情况。说明这个字符后面什么都没有，并且这个字符也不是别的单词的结尾。说明这个字符，仅仅是word这个单词用到了，其他单词没有用到。
         *         所以我们直接递归到这个字符的前一个node，将这个字符删除。并且继续判断前一个字符。
         *    --3.2如果 ( 这个字符next.size() != 0 || 这个字符.isWord == true ) 的话，即3/4种情况。说明这个字符后面还有其他字符，或者这个字符是别的单词的结尾, 这些字符属于其他单词
         *         所以什么都不用做，不用去删除这个字符，也不用更改isWord，而是直接退出remove函数，注意是以true返回，说明已经成功删除了。
         * 4. 如此循环，一直到word.charAt(i)中的i已经== -1了。就说明已经删除完了。
         *
         *
         * 注意的是：我之前总是会弄错是返回false还是返回true:
         *         我错误的地方是：认为到了2.2和3.2的情况，那就会返回false，意思是就 word之前的字母都不用删除了。false不是不用删除剩余的字母！！而是说有没有成功删除word！
         *                       之所以犯这种低级错误，是因为递归的时候，也要返回上一个函数的信息，就是本轮函数还需不需要继续做删除，我把这个信息和该传给用户的false或true信息弄混了
         * 所以，正确的返回逻辑是：
         * 1.如果我们发现word的确是存在在trie的，那最终我们的remove函数都是会返回true，即remove函数一定能删除。用户才不管是怎么删的。
         * 2.如果我们发现word不存在在trie的，那最终我们的remove函数都是会返回false，即根本没有这个word。
         *
         *  */

        //非递归，用stack：
        /*
        * 1. 首先一个一个char c = word.charAt(i)判断，node.next.get(c)存不存在：
        *   --如果存在的话，就将 node.next.get(c) push进入一个stack
        *   --如果不存在的话，那就直接也不用remove了，返回false说明remove失败。
        * 2. 一直到最后一个char c, 如果最后一个字符对应的isWord == false，不用remove了，返回false说明remove失败。
        *    如果isWord == true，一方面说明存在这个word，第二也确保word全部push进去了，即我们有原料进行下一步加工处理了，这个原料就是已经推入word的stack
        * 3. 这里要注意的是，之所以用stack来实现存入node，是因为
        *    3.1 这样子，我们就记录下了word从最后一个字母到第一个字母对应的node的顺序，这就相当于实现了 递归的性能，即能很方便回到上一个节点：通过return，轻易回到的上一个节点node。
        *    3.2 另外，stack里面存的node，都是object，也就是说明stack里面存的东西，都是地址！而不是存的仅仅是值，这就说明了我们的stack是可以对node进行修改的。
        * */

        if(word.equals(""))
            return false;

        Stack<Node> stack = new Stack<>();
        stack.push(root);//首先要第一个push进去一个root，因为如果走到后面的while loop的要删除最后一个元素的话，那就从root.next.remove()删除。
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if(cur.next.get(c) == null || (i == word.length()-1 && !cur.next.get(c).isWord))
                //或者符号左边的判断：是否存在char c， 或者符号右边的判断，最后一个字符c的isWord是不是false。
                //注意，我先判断cur.next.get(c) == null，由于熔断机制，这样就可以保证!cur.next.get(c).isWord中的cur.next.get(c)是!=null的。
            {
                System.out.println("Word " + word + " doesn't exist, remove failed.");
                return false;
            }
            stack.push(cur.next.get(c));
            cur = cur.next.get(c);
        }
        //下面的if判断，由于上面的if(cur.next.get(c) == null || (i == word.length()-1 && !cur.next.get(c).isWord))而省略：
        /*if(!cur.isWord)//此时出来for loop的cur已经是最后一个字符
        {
            System.out.println("Word " + word + " doesn't exist, remove failed.");
            return false;
        }*/

        //先上来第一步就是将最后一个字符的isWord改成false。删不删后面再看
        cur.isWord = false;
        size--;

        //之前讨论了，只有1/4种情况： ( 这个字符next.size() == 0 && 这个字符.isWord == false ) 需要麻烦点，需要到前一个节点来删除本节点
        //而更多的3/4种情况：( 这个字符next.size() != 0 || 这个字符.isWord == true )，是可以不删除本节点，直接返回用户true，即删除word成功了。
        int index = word.length()-1;
        while(stack.size() >= 2)//即至少存在一个root，外加一个字符供pop
        {
            Node top = stack.pop();
            if(top.next.size() != 0 || top.isWord)
                break;
            //剩下的情况 （top.next.size() == 0 && !top.isWord）才是需要复杂处理的，即删除
            Node former = stack.peek();
            char topChar = word.charAt(index--);//因为我们Node类设计的时候，Node本身是不知道自己是什么字符的，所以无法通过成员函数获知自己的字符，所以我设计了index来索引top节点的字符。
            former.next.remove(topChar);
        }
        return true;
    }
}
