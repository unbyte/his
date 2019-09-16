package bridge;

import lib.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * 使得java和js共享一套配置参数，实现在前端页面调取或修改java中的参数
 */
public class ConfigBridge implements Bridge {
    private HashMap<String, String> configs = new HashMap<>();
    private Path configPath = Paths.get("./config.ini");

    {
        String[] configItems = FileUtils.readStringFromFile(configPath, "127.0.0.1:30000").split(":");
        if (configItems.length > 1) {
            configs.put("server_address", configItems[0]);
            configs.put("server_port", configItems[1]);
        } else {
            FileUtils.writeStringToFile(configPath, "127.0.0.1:30000", false);
            configs.put("server_address", "127.0.0.1");
            configs.put("server_port", "30000");
        }
    }

    /**
     * 供js调取配置
     *
     * @param configName 配置项名称
     * @return 配置项内容
     */
    public String getConfig(String configName) {
        return configs.getOrDefault(configName, "");
    }

    /**
     * 供js设置或更新配置
     *
     * @param configName 配置项名称
     * @param newValue   配置项新值
     */
    public void setConfig(String configName, String newValue) {
        configs.put(configName, newValue);
    }

}
