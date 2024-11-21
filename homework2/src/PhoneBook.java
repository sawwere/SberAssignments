import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhoneBook {
    private final HashMap<String, ArrayList<String>> phoneBook;

    public PhoneBook() {
        phoneBook = new HashMap<>();
    }

    public void add(String name, String number) {
        ArrayList<String> currentNumbers = phoneBook.getOrDefault(name, new ArrayList<>());
        currentNumbers.add(number);
        phoneBook.put(name, currentNumbers);
    }

    public List<String> get(String name) {
        return phoneBook.getOrDefault(name, new ArrayList<>());
    }
}
