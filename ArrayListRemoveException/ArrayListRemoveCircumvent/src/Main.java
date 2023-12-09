import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i< 10; i++){
            list.add(i);
        }

        // This will throw ConcurrentModificationException
        for (var num: list){
            list.remove(0);
        }
    }
}