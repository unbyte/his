package net.handler.analyser;

import java.util.HashMap;

public class AnalyserCenter {
    private static HashMap<String, ResultAnalyser> analysers = new HashMap<>();

    static {
        analysers.put("default", ((ctx, result) -> {
        }));
    }

    /**
     * 获取到指定的ResultAnalyser，若不存在则返回默认空操作
     *
     * @param analyserName 应该与methodName同名
     * @return analyser
     */
    public static ResultAnalyser getAnalyser(String analyserName) {
        if (!analysers.containsKey(analyserName))
            return analysers.get("default");
        return analysers.get(analyserName);
    }
}
