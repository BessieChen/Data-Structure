import java.util.TreeMap;

public class Trie {

    private class Node
    {
        private boolean isWord;
        private TreeMap<Character, Node> next;

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

    public Trie()
    {
        root = new Node();
        size = 0;
    }

    public int getSize()
    {
        return size;
    }
    //-------------------------------------add starts------------------------------

    public void add(String word)
    {
        Node cur = root;
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            //如果没有c的映射，就创建新的映射，
            if(cur.next.get(c) == null)
            {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if(!cur.isWord)
        {
            cur.isWord = true;
            size++;
        }
    }

    public void add_R(String word)
    {
        add_R(root, word, 0);
    }

    private void add_R(Node node, String word, int index)
    {
        if(index == word.length())
        {
            if(!node.isWord)
            {
                node.isWord = true;
                size++;
            }
            return;
        }
        char c = word.charAt(index);
        if(node.next.get(c) == null)
        {
            node.next.put(c, new Node());
        }
        add_R(node.next.get(c), word, index+1);
    }

    //-------------------------------------add ends------------------------------

    //-------------------------------------contains starts------------------------------
    public boolean contains(String word)
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

        //method 1:
        if(!cur.isWord)
        {
            return false;
        }
        return true;

        //或者直接
        // method 2:
        // return cur.isWord;
    }

    public boolean contains_R(String word)
    {
        return contains_R(root, word, 0);
    }

    private boolean contains_R(Node node, String word, int index)
    {
        if(index == word.length() && node.isWord)
        {
            return true;
        }
        char c = word.charAt(index);
        if(node.next.get(c) == null)
        {
            return false;
        }
        return contains_R(node.next.get(c), word, index+1);
    }

    //-------------------------------------contains ends------------------------------

    //-------------------------------------prefix starts------------------------------
    public boolean isPrefix(String prefix)
    {
        Node cur = root;
        for(int i = 0; i < prefix.length(); i++)
        {
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null)
            {
                return false;
            }
            cur = cur.next.get(c);
        }

        return true;
    }

    public boolean isPrefix_R(String prefix)
    {
        return isPrefix_R(root, prefix, 0);
    }

    private boolean isPrefix_R(Node node, String prefix, int index)
    {

        //下面的是错的，但是中间的判断其实没有必要了，因为index == prefix时候已经是判断过了这个index存不存在了，而因为这个index是最后一个，那肯定就没有下一个
//        if(index == prefix.length())
//        {
//            char c = prefix.charAt(index);
//            if(node.next.get(c) != null)//这里一定是node.next.get(c) == null
//            {
//                return true;
//            }
//        }
        if(index == prefix.length())
        {
            return true;
        }
        char c = prefix.charAt(index);
        if(node.next.get(c) == null)
        {
            return false;
        }
        return isPrefix_R(node.next.get(c), prefix, index+1);

    }
    //-------------------------------------prefix ends------------------------------

    //-------------------------------------add/contains from teacher starts------------------------------
    public void add_teacher_NR(String word){

        Node cur = root;
        for(int i = 0 ; i < word.length() ; i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }

        if(!cur.isWord){
            cur.isWord = true;
            size ++;
        }
    }
    public void add_teacher(String word){

        add_teacher(root, word, 0);
    }

    // 向以node为根的Trie中添加word[index...end), 递归算法
    private void add_teacher(Node node, String word, int index){

        if(index == word.length()){
            if(!node.isWord){
                node.isWord = true;
                size ++;
            }
            return;
        }

        char c = word.charAt(index);
        if(node.next.get(c) == null)
            node.next.put(c, new Node());
        add_teacher(node.next.get(c), word, index + 1);
    }

    // 查询单词word是否在Trie中
    public boolean contains_teacher_NR(String word){

        Node cur = root;
        for(int i = 0 ; i < word.length() ; i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    // 查询单词word是否在Trie中
    public boolean contains_teacher(String word){
        return contains_teacher(root, word, 0);
    }

    // 在以node为根的Trie中查询单词word[index...end)是否存在, 递归算法
    private boolean contains_teacher(Node node, String word, int index){

        if(index == word.length())
            return node.isWord;

        char c = word.charAt(index);
        if(node.next.get(c) == null)
            return false;

        return contains_teacher(node.next.get(c), word, index + 1);
    }

    // 查询在以Node为根的Trie中是否有单词以prefix[index...end)为前缀, 递归算法
    private boolean isPrefix_teacher(Node node, String prefix, int index){

        if(index == prefix.length())
            return true;

        char c = prefix.charAt(index);
        if(node.next.get(c) == null)
            return false;

        return isPrefix_teacher(node.next.get(c), prefix, index + 1);
    }
    //-------------------------------------add/contains from teacher ends------------------------------


}

