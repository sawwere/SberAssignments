import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void task1() {
        System.out.println("=====================");
        System.out.println("|      Task 1       |");
        System.out.println("=====================");

        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(
                "bucket", "plane", "sphere", "sphere",
                "bucket", "desk", "deck", "sphere",
                "sphere", "bingo", "bucket", "ok",
                "dinosaur", "sphere", "sphere")
        );
        HashMap<String, Integer> map = new HashMap<>();
        for (var s : wordsList) {
            map.put(s, map.getOrDefault(s, 0) + 1);

        }
        System.out.println("List of unique words and their count");
        for (var k : map.keySet()) {
            System.out.printf("%s - %d%n", k, map.get(k));
        }
    }

    public static void task2() {
        System.out.println("=====================");
        System.out.println("|      Task 2       |");
        System.out.println("=====================");
        PhoneBook phoneBook = new PhoneBook();

        phoneBook.add("Ivan","890432115");
        phoneBook.add("Sonya","911");
        phoneBook.add("Viktor","112");
        phoneBook.add("Ivan","93256583");
        phoneBook.add("Ivan","0000000");
        phoneBook.add("Sonya","987654321");
        phoneBook.add("Vlad","114");

        System.out.println("Ivan: " + String.join(", ", phoneBook.get("Ivan")));
        System.out.println("Sonya: " + String.join(", ", phoneBook.get("Sonya")));
        System.out.println("Viktor: " + String.join(", ", phoneBook.get("Viktor")));
        System.out.println("Vlad: " + String.join(", ", phoneBook.get("Vlad")));

    }

    public static void main(String[] args) {
        task1();
        task2();
    }
}