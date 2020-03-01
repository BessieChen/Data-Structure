import java.util.Random;

public class Main {

    private static double testHeap(Integer[] testData, boolean isHeapify) {

        long startTime = System.nanoTime();

        MaxHeap<Integer> maxHeap;
        if (isHeapify)
            maxHeap = new MaxHeap<>(testData);
        else {
            maxHeap = new MaxHeap<>();
            for (int num : testData)
                //maxHeap.add_teacher(num);
                maxHeap.add(num);
        }

        System.out.println(maxHeap);//我是用来检查MaxHeap类调用的是不是Array内部的toString()函数？

        int[] arr = new int[testData.length];
        for(int i = 0 ; i < testData.length ; i ++)
            //arr[i] = maxHeap.extractMax_teacher();
            arr[i] = maxHeap.extractMax();

        for(int i = 1 ; i < testData.length ; i ++)
            if(arr[i-1] < arr[i])
                throw new IllegalArgumentException("Error");
        System.out.println("Test MaxHeap completed.");

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        //int n = 1000000;
        int n = 10;
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        for(int i = 0 ; i < n ; i ++)
            //maxHeap.add_teacher(random.nextInt(Integer.MAX_VALUE));
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));

        int[] arr = new int[n];
        for(int i = 0 ; i < n ; i ++)
            //arr[i] = maxHeap.extractMax_teacher();
            arr[i] = maxHeap.extractMax();

        for(int i = 1 ; i < n ; i ++)
            if(arr[i-1] < arr[i])
                throw new IllegalArgumentException("Error");

        System.out.println("Test MaxHeap completed.");

        //n = 1000000;
        Integer[] testData = new Integer[n];
        for(int i = 0 ; i < n ; i ++)
            testData[i] = random.nextInt(Integer.MAX_VALUE);

        double time1 = testHeap(testData, false);
        System.out.println("Without heapify: " + time1 + " s");//Without heapify: 0.7976558 s

        double time2 = testHeap(testData, true);
        System.out.println("With heapify: " + time2 + " s");//With heapify: 0.816499688 s
    }
}
