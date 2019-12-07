package lib;

import java.util.Arrays;

public class MinHeap {
    private int[] data;//基本结构

    public MinHeap(int[] data) {
        this.data = data;
        buildHeap();//创建最小堆
    }

    /*
        private void buildHeap() {
            *//*
     * 完全二叉树只有数组小标小于或等于（data.length）/2 -1的元素有孩子节点
     *//*
        for (int i = data.length / 2 - 1; i >= 0; i--) {
            //对有孩子节点的元素heapify
            modify(i);
        }
    }

    private void modify(int i) {
        // 自顶向下
        // 第i个节点的左右节点数组下标为 (i+1)*2-1 (i+1)*2
        int left = (i + 1) * 2 - 1;
        int right = (i + 1) * 2;
        int minIndex = i;
        if (left < data.length && data[left] < data[i])
            minIndex = left;
        if (right < data.length && data[right] < data[minIndex])
            minIndex = right;
        if (i == minIndex)
            return;
        int temp = data[i];
        data[i] = data[minIndex];
        data[minIndex] = temp;
        modify(minIndex);
    }*/
    private void buildHeap() {
        for (int i = data.length / 2; i > 0; i--) {
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
            right = i+1;
        } else {
            right = i;
            left = i-1;
        }
        min = parent = left / 2;

        if (left < data.length && data[left] < data[parent])
            min = left;
        if (right < data.length && data[right] < data[min])
            min = right;
        if (parent == min) {
            print();
            return;
        }

        int temp = data[parent];
        data[parent] = data[min];
        data[min] = temp;
        print();
        modifyUp(parent);
    }

    private void modifyDown(int i) {
        int left = i * 2;
        int right = i * 2 + 1;
        int min = i;

        if (left < data.length && data[left] < data[i])
            min = left;
        if (right < data.length && data[right] < data[min])
            min = right;

        if (i == min)
            return;

        int temp = data[i];
        data[i] = data[min];
        data[min] = temp;
        modifyDown(min);
    }


    //获取较小的元素
    public int get() {
        return data[1];
    }

    //插入元素
    public void insert(int val) {
        int len = data.length;
        data = Arrays.copyOf(data, data.length + 1);
        data[len] = val;
        modifyUp(len);
    }

    //获取较小的元素
/*
    public int get() {
        return data[0];
    }
*/

/*    //插入元素
    public void insert(int val) {
        data[0] = val;
        modify(0);
    }*/

    public void print() {
        System.out.println(Arrays.toString(data));
    }

    public static void main(String[] args) {
//        MinHeap minHeap = new MinHeap(new int[]{1, 4, 2, 5, 7, 0, 8, 13, 9, -2});
        MinHeap minHeap1 = new MinHeap(new int[]{1, 4, 2, 5, 7, 0, 8, 13, 9, -2});
        minHeap1.insert(-4);
//        minHeap.print();
        minHeap1.print();
    }
}
