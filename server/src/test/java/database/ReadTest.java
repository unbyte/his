package database;

import java.util.HashMap;

public class ReadTest {
    public static void main(String[] args) {
        long uu = 100;
        HashMap<Long,String> a = new HashMap<>();
        a.put(100L,"123");

        System.out.println(a.get(uu));
    }
}
