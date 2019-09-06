package model;

import com.alibaba.fastjson.annotation.JSONCreator;

/**
 * 职称
 */
public class Title {
    private int id;
    private String name;
    private int registrationLevel;
    private static int flag = 0;

    /**
     * 获取id
     *
     * @return id id
     */
    public int getId() {
        return id;
    }

    /**
     * 获取名称
     *
     * @return 名称 name
     */
    public String getName() {
        return name;
    }

    /**
     * 获取职称对应的挂号等级id
     *
     * @return 挂号等级id
     */
    public int getRegistrationLevel() {
        return registrationLevel;
    }

    private Title(int id, String name, int registrationLevel) {
        this.id = id;
        this.name = name;
        this.registrationLevel = registrationLevel;
    }

    /**
     * 新建一个职称对象并获取
     *
     * @param name              名称
     * @param registrationLevel 对应的挂号等级id
     * @return id自动生成的职称对象
     */
    public static Title insert(String name, int registrationLevel) {
        // todo Id
        int id = 0;
        return new Title(id, name, registrationLevel);
    }

}
