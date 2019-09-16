package view;

import javafx.stage.Stage;

import java.util.HashMap;

/**
 * 可视化界面对象容器
 */
public class ViewCenter {
    private static HashMap<String, Object> scenes = new HashMap<>();
    private static HashMap<String, Stage> stages = new HashMap<>();

    /**
     * 将一个Scene对象注册到容器中<br/>
     * 默认注册的名称为对象的类名
     *
     * @param scene 待注册的Scene对象
     */
    public static void registerScene(Object scene) {
        registerScene(scene.getClass().getSimpleName(), scene);
    }

    /**
     * 将一个Scene对象注册到容器中
     *
     * @param name  注册的名称
     * @param scene 待注册的Scene对象
     */
    public static void registerScene(String name, Object scene) {
        if (scene != null && name != null)
            scenes.put(name, scene);
    }

    /**
     * 获取到容器中注册过的Scene对象
     *
     * @param name 注册的名称
     * @return Scene对象
     */
    public static <T> T getScene(String name, Class<T> clazz) {
        return clazz.cast(scenes.get(name));
    }

    /**
     * 获取到容器中注册过的Stage对象
     *
     * @param name  注册的名称
     * @param stage 注册的Stage对象
     */
    public static void registerStage(String name, Stage stage) {
        if (name != null && stage != null)
            stages.put(name, stage);
    }

    /**
     * 获取到容器中注册过的Stage对象
     *
     * @param name 注册的名称
     * @return stage对象
     */
    public static Stage getStage(String name) {
        return stages.get(name);
    }
}
