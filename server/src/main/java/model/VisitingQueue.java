package model;

import database.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class VisitingQueue {
    private List<VisitingQueueElement> registrations = new ArrayList<>() {{
        add(null);
    }};// 不用下标0
    private int typeA = 0;
    private int typeB = 0;
    private int urgent = 0;
    private int out = 0;

    public VisitingQueue() {
    }

    public VisitingQueue(Registration[] registrations) {
        this.registrations.addAll(Arrays.stream(registrations).map(this::calculatePower).collect(Collectors.toList()));
        build();
    }

    private VisitingQueueElement calculatePower(Registration registration) {
        VisitingQueueElement newElement = new VisitingQueueElement().setRegistration(registration);
        if (registration.isUrgent()) {
            urgent++;
            return newElement.setPower(-100 + urgent).setType(VisitingQueueElement.TYPE_URGENT);
        }
        if (Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw()
                .containsKey(registration.getId())) {
            typeB++;
            return newElement.setPower((typeB - 1 + out) * 2 + 1).setType(VisitingQueueElement.TYPE_B);
        }
        typeA++;
        return newElement.setPower((typeA - 1 + out) * 2).setType(VisitingQueueElement.TYPE_A);
    }

    private void build() {
        for (int i = registrations.size() / 2; i > 0; i--) {
            modifyDown(i);
        }
    }

    private void modifyUp(int i) {
        // 第i个节点的左右节点数组下标为 i*2 i*2+1
        if (i == 1)
            return;

        int right, left, parent, min;
        if (i % 2 == 0) {
            left = i;
            right = i + 1;
        } else {
            right = i;
            left = i - 1;
        }
        min = parent = left / 2;

        if (compare(i, right, left, parent, min)) return;
        modifyUp(parent);
    }

    private boolean compare(int i, int right, int left, int parent, int min) {
        if (left < registrations.size() &&
                registrations.get(left).getPower() < registrations.get(parent).getPower())
            min = left;
        if (right < registrations.size() &&
                registrations.get(right).getPower() < registrations.get(min).getPower())
            min = right;

        if (parent == min)
            return true;

        VisitingQueueElement temp = registrations.get(parent);
        registrations.set(parent, registrations.get(min));
        registrations.set(min, temp);
        return false;
    }

    private void modifyDown(int i) {
        int left = i * 2;
        int right = i * 2 + 1;
        int min = i;

        if (compare(i, right, left, i, min)) return;
        modifyDown(min);
    }


    // 获取较小的元素
    public Registration get() {
        VisitingQueueElement popElement = registrations.get(1);
        registrations.set(1, registrations.get(registrations.size() - 1));
        registrations.remove(registrations.size() - 1);
        modifyDown(1);
        out++;
        return popElement.getRegistration();
    }

    // 插入元素
    public void insert(Registration val) {
        VisitingQueueElement newElement = calculatePower(val);
        registrations.add(newElement);
        modifyUp(registrations.size() - 1);
    }


    public List<VisitingQueueElement> getAllElement() {
        return registrations;
    }
}
