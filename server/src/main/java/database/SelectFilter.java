package database;

import java.util.HashMap;
import java.util.List;

@FunctionalInterface
public interface SelectFilter {
    List filter(HashMap data);
}
