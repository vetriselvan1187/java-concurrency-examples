import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinPoolMain {

    // fork join pool recursiveAction
    // fork join pool recursiveTask

    static final class ArrayTransformer extends RecursiveAction {
        private final int[] srcArray;
        private final int start;
        private final int end;
        private final int[] destArray;

        public ArrayTransformer(int[] srcArray, int start, int end, int[] destArray) {
            this.srcArray = srcArray;
            this.start = start;
            this.end = end;
            this.destArray = destArray;
        }

        @Override
        protected void compute() {
            if(end-start < 100) {
                for(int i = start; i <= end; i++) {
                    destArray[i] = 2*srcArray[i];
                }
                return;
            }
            int mid = start+(end-start)/2;
            ArrayTransformer transformer1 = new ArrayTransformer(srcArray, start, mid, destArray);
            ArrayTransformer transformer2 = new ArrayTransformer(srcArray, mid+1, end, destArray);
            transformer1.fork();
            transformer2.fork();
            transformer1.join();
            transformer2.join();
        }
    }

    static final class SumArray extends RecursiveTask<Integer> {
        private final int[] array;
        private final int start;
        private final int end;

        public SumArray(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if(end-start < 100) {
                return Arrays.stream(array, start, end+1).sum();
            }
            int mid = start+(end-start)/2;
            SumArray s1 = new SumArray(array, start, mid);
            SumArray s2 = new SumArray(array, mid+1, end);
            s1.fork();
            s2.fork();
            return s1.join()+s2.join();
        }
    }

    public static void main(String[] args) {
        int[] srcArray = IntStream.range(0, 1000).toArray();
        int[] destArray = IntStream.range(0, 1000).toArray();
        for(int i = 0; i < srcArray.length; i++) {
            srcArray[i] = i;
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(16);
        ArrayTransformer transformer = new ArrayTransformer(srcArray, 0, 999, destArray);
        forkJoinPool.invoke(transformer);
        //transformer.fork();
        //transformer.join();
        Arrays.stream(destArray).forEach(x -> System.out.println(x));

        System.out.println(forkJoinPool.getParallelism());
        SumArray array = new SumArray(srcArray, 0, 999);
        System.out.println(forkJoinPool.invoke(array));
        //System.out.println(array.join());

    }
}
