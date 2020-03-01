import java.util.TreeMap;
//这个Trie代码的func()都已经通过leetcode208检查，代码正确
public class Trie_recursive
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
    private int size;

    public Trie_recursive()
    {
        root = new Node();
        size = 0;
    }

    public void add(String word)
    {
        add(root, word, 0);
    }

    private void add(Node node, String word, int index)
    {
        if(index == word.length())//说明从上一轮过来的是时候，那个char c已经是最后一个字母了
            return;
        char c = word.charAt(index);
        if(node.next.get(c) == null)
        {
            node.next.put(c, new Node());
        }
        Node nextLetter = node.next.get(c);
        if(index == word.length() - 1 && !nextLetter.isWord )
        {
            nextLetter.isWord = true;
            size++;
        }
        add(nextLetter, word, index+1);
    }

    public boolean contains(String word)
    {
        return contains(root, word, 0);
    }

    private boolean contains(Node node, String word, int index)
    {
        if(index == word.length())
            return node.isWord; //说明已经遍历完了，此时参数给的node就是最后一个字母对应的节点。

        char c = word.charAt(index);
        if(node.next.get(c) == null)
        {
            return false;
        }
        return contains(node.next.get(c), word, index+1);
    }

    public boolean isPrefix(String prefix)
    {
        return isPrefix(root, prefix, 0);
    }

    private boolean isPrefix(Node node, String prefix, int index)
    {
        if(index == prefix.length())
            return true;

        char c = prefix.charAt(index);
        if(node.next.get(c) == null)
            return false;

        return isPrefix(node.next.get(c), prefix, index+1);
    }

    //我最开始的remove设想，有错误，你可以略过。
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
     * 之前的想法，看看就好。最后没有使用到：
     * boolean ret;//返回给用户看的.
     * boolean continueRemove;//我自己用来判断还需不需要继续删除剩余字符。
     * 然后逻辑是：如果在判断word是否存在的过程中，有任何不存在的信号，则用户收到false，continueRemove也只可能是false
     * 如果最终发现word存在，则用户收到true，continueRemove有可能是false，也有可能一直是true。
     *
     *  */

    public boolean remove(String word)
    {
        if(word.equals(""))
            return false;
        return remove(root, word, 0);
        //return remove2(root, word, 0);
    }

    //method 1: 在终止条件处直接node.isWord = false; size--;
    private boolean remove(Node node, String word, int index)
    {
        if(index == word.length())
        {
            if(node.isWord)
            {
                node.isWord = false;
                size--;
                return true;
            }
            return false;//这是最终判断，word是否存在在trie中，如果这里是true，最终直接返回到用户的也必须是true。如果这里是false，用户得到的也必须是false。
        }

        char c = word.charAt(index);
        /*if(node.next.get(c) == null)
            return false;//说明word不存在在trie中，用户收到false
        else
        {
            ret = remove(node.next.get(c), word, index + 1);
        }

        if(ret)
        {
            if(node.next.get(c).next.size() == 0)//如果c字符没有下一个字母，说明c单独属于word，所以直接删掉c
            {
                node.next.remove(c);
                size--;
            }
            else if(node.next.get(c).next.size() != 0 && index == word.length()-1)
            {
                node.next.get(c).isWord = false;//如果c字符有下一个字母，说明c不是单独属于word，所以将c的isWord = false;
                size--;
                ret = false;//一旦ret == false，就会一口气返回，就说明剩下word之前的字母都不用删除了。
            }
        }
        return ret;*/
        if(node.next.get(c) == null)
            return false;

        if(!remove(node.next.get(c), word, index+1))
            return false;

        //能走到这里，说明已经是保证了，word这个单词是存在在trie里面的。并且最后一个字符的isWord已经==false
        //之前讨论了，只有1/4种情况： ( 这个字符next.size() == 0 && 这个字符.isWord == false ) 需要麻烦点，需要到前一个节点来删除本节点
        //而更多的3/4种情况：( 这个字符next.size() != 0 || 这个字符.isWord == true )，是可以不删除本节点，直接返回用户true，即删除word成功了。

        Node nextNode = node.next.get(c);
        if(nextNode.next.size() == 0 && !nextNode.isWord)//需要复杂处理的，即删除
            node.next.remove(c);

        //剩下的情况 （nextNode.next.size() != 0 || nextNode.isWord)，和上一步删除完后，直接返回true
        return true;
    }

    //method 2: 在后面才node.isWord = false; size--;
    private boolean remove2(Node node, String word, int index)
    {
        if(index == word.length())
        {
            return node.isWord;//这是最终判断，word是否存在在trie中，如果这里是true，最终直接返回到用户的也必须是true。如果这里是false，用户得到的也必须是false。
        }

        char c = word.charAt(index);
        /*if(node.next.get(c) == null)
            return false;//说明word不存在在trie中，用户收到false
        else
        {
            ret = remove(node.next.get(c), word, index + 1);
        }

        if(ret)
        {
            if(node.next.get(c).next.size() == 0)//如果c字符没有下一个字母，说明c单独属于word，所以直接删掉c
            {
                node.next.remove(c);
                size--;
            }
            else if(node.next.get(c).next.size() != 0 && index == word.length()-1)
            {
                node.next.get(c).isWord = false;//如果c字符有下一个字母，说明c不是单独属于word，所以将c的isWord = false;
                size--;
                ret = false;//一旦ret == false，就会一口气返回，就说明剩下word之前的字母都不用删除了。
            }
        }
        return ret;*/
        if(node.next.get(c) == null)
            return false;

        if(!remove(node.next.get(c), word, index+1))
            return false;

        //能走到这里，说明已经是保证了，word这个单词是存在在trie里面的。并且最后一个字符的isWord已经==false
        //之前讨论了，只有1/4种情况： ( 这个字符next.size() == 0 && 这个字符.isWord == false ) 需要麻烦点，需要到前一个节点来删除本节点
        //而更多的3/4种情况：( 这个字符next.size() != 0 || 这个字符.isWord == true )，是可以不删除本节点，直接返回用户true，即删除word成功了。

        Node nextNode = node.next.get(c);
        if(index == word.length()-1)
        {
            nextNode.isWord = false;
            size--;
        }
        if(nextNode.next.size() == 0 && !nextNode.isWord)//需要复杂处理的，即删除
            node.next.remove(c);

        //剩下的情况 （nextNode.next.size() != 0 || nextNode.isWord)，和上一步删除完后，直接返回true
        return true;
    }
}
