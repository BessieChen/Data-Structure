public interface Stack<Element> {
    void push(Element e);//增
    Element pop();//删
    Element peek();//查
    int getSize();
    boolean isEmpty();

}
