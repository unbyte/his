package database;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import model.Department;

import java.util.HashSet;

public class JsonTest {
    public static void main(String[] args) {
        /*HashMap<Integer, RegistrationLevel> registrationLevels = new HashMap<>();
        registrationLevels.put(0, new RegistrationLevel(0, 0, 0));
        registrationLevels.put(1, new RegistrationLevel(1, 120, 20));
        registrationLevels.put(2, new RegistrationLevel(2, 80, 60));
        registrationLevels.put(3, new RegistrationLevel(3, 40, 100));
        System.out.println(JSON.toJSONString(JSON.toJSON(registrationLevels)));
*/
        /*List<Registration> list = new ArrayList<>();
        list.add(new Registration());

        System.out.println(JSON.toJSONString(list));

        */

//        JSONObject a = new JSONObject().fluentPut("a", "123333321");
//        System.out.println(a.getLong("a"));

//        System.out.println(JSON.parseObject("{\"a\":[1,2,3,4]}").getJSONArray("a"));
//        System.out.println(new JSONObject().fluentPut("a",new Department(1,"1",(byte)1)).toJSONString());
        System.out.println(JSON.toJSONString(new HashSet<Integer>(){{add(1);}}));
    }
}
