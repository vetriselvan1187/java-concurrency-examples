import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ImmutableMain {

    // immutable class
    static final class Coordinate {
        private final int x;
        private final int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }

        public Coordinate getCoordinate(int changeX, int changeY) {
            return new Coordinate(x+changeX, y+changeY);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    // immutable casList
    static final class CASList {
        private final AtomicReference<List<Integer>> atomicReference =  new AtomicReference<>(Collections.emptyList());

        public void add(Integer n) {
            List<Integer> oldList = atomicReference.get();
            List<Integer> newList = new ArrayList<>(oldList);
            newList.add(n);
            while(true) {
                if(atomicReference.compareAndSet(oldList, newList)) {
                    return;
                }
            }
        }

        public List<Integer> getList() {
            List<Integer> copyList = new ArrayList<>(atomicReference.get());
            return copyList;
        }
    }

    public static void main(String[] args) {
        Coordinate coordinate = new Coordinate(0, 0);
        System.out.println(coordinate);
        Coordinate c1 = coordinate.getCoordinate(5, -10);
        System.out.println(c1);
        Coordinate c2 = c1.getCoordinate(-5, 15);
        System.out.println(c2);

        CASList casList = new CASList();
        for(int i = 0; i < 5; i++) {
            casList.add(i);
            System.out.println(casList.getList());
        }
    }
}
