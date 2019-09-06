package view;

import java.util.HashMap;

public class SceneMaster {
    static private HashMap<String, Object> scenes = new HashMap<>();


    /**
     * 将一个Scene对象注册到容器中
     *
     * @param scene 待注册的Scene对象
     */
    public static void registerScene(Object scene) {
        if (scene != null)
            scenes.put(scene.getClass().getSimpleName(), scene);
    }

    /**
     * 获取到容器中注册过的Scene对象
     *
     * @param name Scene对象的类名
     * @param <T>  Scene对象的类型
     * @return Scene对象
     */
    public static <T> T getScene(String name) {
        return (T)scenes.get(name);
    }
}
