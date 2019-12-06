package database;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import model.Disease;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class TreeTest {
    public static void main(String[] args) {
        Database.INSTANCE.boot();
        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();
//        System.out.println(JSON.toJSONString(diseases.values().stream().filter(i -> i.getParent() == null).collect(Collectors.toList()),new SimplePropertyPreFilter(Disease.class, "id","code","name","clazz","children")));

        System.out.println(getChildrenDiseaseID(1, diseases));

    }

    private static List<Integer> getChildrenDiseaseID(Integer id, Map<Integer, Disease> disease) {
        if (disease.get(id).getChildren().size() == 0)
            return new ArrayList<>(Arrays.asList(id));
        List<Integer> children = disease.get(id).getChildren().stream()
                .map(i -> getChildrenDiseaseID(i.getId(), disease))
                .reduce((x, y) -> {
                    x.addAll(y);
                    return x;
                })
                .get();
        children.add(id);
        return children;
    }
}
