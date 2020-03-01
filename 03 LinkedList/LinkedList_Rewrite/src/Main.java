public class Main {

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for(int i = 0; i < 5; i++)
        {
            linkedList.addFirst(i+1);
            System.out.println(linkedList);
        }

        linkedList.add(2,1106);
        System.out.println(linkedList);

        linkedList.remove(2);
        System.out.println(linkedList);

        linkedList.removeFirst();
        System.out.println(linkedList);

        Integer a = linkedList.removeLast();
        System.out.println(linkedList);
        System.out.println("remove last: " + a);
        System.out.println("get index == 1: " + linkedList.get(1));
        System.out.println("contains 2 ? -" + linkedList.contains(2));
        System.out.println("contains 4 ? -" + linkedList.contains(4));
        System.out.println("contains 1106 ? -" + linkedList.contains(1106));
        linkedList.set(1,1106);
        Integer b = linkedList.get(linkedList.getSize()-1);
        System.out.println(linkedList);
        System.out.println("get last: " + b);



        //////////////////////////////////
        System.out.println("-------------------");
        LinkedList_dummyhead<Integer> linkedListdummy = new LinkedList_dummyhead<>();
        for(int i = 0; i < 5; i++)
        {
            linkedListdummy.addFirst(i+1);
            System.out.println(linkedListdummy);
        }

        linkedListdummy.add(2,1106);
        System.out.println(linkedListdummy);

        linkedListdummy.remove(2);
        System.out.println(linkedListdummy);

        linkedListdummy.removeFirst();
        System.out.println(linkedListdummy);

        Integer c = linkedListdummy.removeLast();
        System.out.println(linkedListdummy);
        System.out.println("remove last: " + c);
        System.out.println("get index == 1: " + linkedListdummy.get(1));
        System.out.println("contains 2 ? -" + linkedListdummy.contains(2));
        System.out.println("contains 4 ? -" + linkedListdummy.contains(4));
        System.out.println("contains 1106 ? -" + linkedListdummy.contains(1106));
        linkedListdummy.set(1,1106);
        Integer d = linkedListdummy.get(linkedListdummy.getSize()-1);
        System.out.println(linkedListdummy);
        System.out.println("get last: " + d);


    }
}


/*
Linked List: size: 1 head |1 -> NULL
Linked List: size: 2 head |2 -> 1 -> NULL
Linked List: size: 3 head |3 -> 2 -> 1 -> NULL
Linked List: size: 4 head |4 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 5 head |5 -> 4 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 6 head |5 -> 4 -> 1106 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 5 head |5 -> 4 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 4 head |4 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 3 head |4 -> 3 -> 2 -> NULL
remove last: 1
get index == 1: 3
contains 2 ? -true
contains 4 ? -true
contains 1106 ? -false
Linked List: size: 3 head |4 -> 1106 -> 2 -> NULL
get last: 2
-------------------
Linked List: size: 1 head |1 -> NULL
Linked List: size: 2 head |2 -> 1 -> NULL
Linked List: size: 3 head |3 -> 2 -> 1 -> NULL
Linked List: size: 4 head |4 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 5 head |5 -> 4 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 6 head |5 -> 4 -> 1106 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 5 head |5 -> 4 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 4 head |4 -> 3 -> 2 -> 1 -> NULL
Linked List: size: 3 head |4 -> 3 -> 2 -> NULL
remove last: 1
get index == 1: 3
contains 2 ? -true
contains 4 ? -true
contains 1106 ? -false
Linked List: size: 3 head |4 -> 1106 -> 2 -> NULL
get last: 2

Process finished with exit code 0
*/