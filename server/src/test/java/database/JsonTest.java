package database;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import model.Registration;
import model.RegistrationLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonTest {
    public static void main(String[] args) {
        /*HashMap<Integer, RegistrationLevel> registrationLevels = new HashMap<>();
        registrationLevels.put(0, new RegistrationLevel(0, 0, 0));
        registrationLevels.put(1, new RegistrationLevel(1, 120, 20));
        registrationLevels.put(2, new RegistrationLevel(2, 80, 60));
        registrationLevels.put(3, new RegistrationLevel(3, 40, 100));
        System.out.println(JSON.toJSONString(JSON.toJSON(registrationLevels)));
*/
        List<Registration> list = new ArrayList<>();
        list.add(new Registration());

        System.out.println(JSON.toJSONString(list));

    }
}
