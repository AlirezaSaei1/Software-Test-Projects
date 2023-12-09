import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i< 10; i++){
            list.add(i);
        }

        // This will now throw ConcurrentModificationException
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            int element = iterator.next();
            if (element == 1) {
                iterator.remove();
            }
        }
        System.out.println("Modified List: " + list);

        try {
            // This will throw ConcurrentModificationException
            for (var num : list) {
                list.remove(0);
            }
        }catch (ConcurrentModificationException e){
            System.out.println("Thrown -> " + e.toString());
        }
    }
}