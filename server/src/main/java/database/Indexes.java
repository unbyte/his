package database;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public enum Indexes {
    INSTANCE;

    @Getter
    @Setter
    private Map<Long, List<Long>> medicalRecordMapToRegistration;
    // todo 自己实现一个hash map然后在数据库初始化的时候存个映射，然后每次都更新一下，这个就懒得做啥封装了
}
