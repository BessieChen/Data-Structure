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
        System.out.println(a);
        System.out.println(linkedList.get(1));
        System.out.println(linkedList.contains(3));
        System.out.println(linkedList.contains(1106));
        linkedList.set(1,1106);
        System.out.println(linkedList);

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

        Integer b = linkedListdummy.removeLast();
        System.out.println(linkedListdummy);
        System.out.println(b);
        System.out.println(linkedListdummy.get(1));
        System.out.println(linkedListdummy.contains(3));
        System.out.println(linkedListdummy.contains(1106));
        linkedListdummy.set(1,1106);
        System.out.println(linkedListdummy);


    }
}
