package algorithm;

import model.Registration;

public class Sorting {


    /**
     * 冒泡排序
     */

    public static void bubbleSort(Registration[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j].getId() > a[j + 1].getId()) {
                    Registration tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
    }

    /**
     * 插入排序
     *
     */
    public static void insertSort(Registration[] a) {
        for (int i = 1; i < a.length; i++) {
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (a[j].getId() < a[i].getId()) {
                    break;
                }
            }
            if (j != (i - 1)) {
                Registration tmp = a[i];
                int k;
                for (k = i - 1; k > j; k--) {
                    a[k + 1] = a[k];
                }
                a[k + 1] = tmp;
            }
        }
    }


    /**
     * 快速排序
     *
     * @param a    待排序数组
     * @param low  子数组开始的下标
     * @param high 子数组结束的下标
     */

    public static void quickSort(Registration[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        int low0 = low;
        int high0 = high;
        boolean forward = false;
        while (low0 != high0) {
            if (a[low0].getId() > a[high0].getId()) {
                Registration tmp = a[low0];
                a[low0] = a[high0];
                a[high0] = tmp;
                forward = !forward;
            }
            if (forward) {
                low0++;
            } else {
                high0--;
            }
        }
        low0--;
        high0++;
        quickSort(a, low, low0);
        quickSort(a, high0, high);
    }


    /**
     * 归并排序<br>
     *
     * @param a 待排序数组；
     * @return 有序数组；
     */
    public static Registration[] mergeSort(Registration[] a) {
        if (a.length == 1) {
            return a;
        }
        int mid = a.length / 2;
        Registration[] leftPart = new Registration[mid];
        Registration[] rightPart = new Registration[a.length - mid];
        System.arraycopy(a, 0, leftPart, 0, leftPart.length);
        System.arraycopy(a, mid, rightPart, 0, rightPart.length);
        leftPart = mergeSort(leftPart);
        rightPart = mergeSort(rightPart);
        return merge(leftPart, rightPart);
    }

    private static Registration[] merge(Registration[] a, Registration[] b) {
        Registration result[] = new Registration[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i].getId() < b[j].getId()) {
                result[k++] = a[i];
                i++;
            } else {
                result[k++] = b[j];
                j++;
            }
        }
        while (i < a.length) {
            result[k++] = a[i++];
        }
        while (j < b.length) {
            result[k++] = b[j++];
        }
        return result;
    }

    /**
     * 选择排序
     *
     * @param a 待排数组
     */
    public static void selectSort(Registration[] a) {
        for (int i = 0; i < a.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].getId() < a[minIndex].getId()) {
                    minIndex = j;
                }
            }
            Registration tmp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = tmp;
        }
    }


    /**
     * 堆排序
     *
     * @param a 待排数组
     */
    public static void heapSort(Registration[] a) {
        for (int i = (a.length - 2) / 2; i >= 0; i--) {
            maxHeapAdjust(a, i, a.length);
        }
        for (int i = a.length - 1; i > 0; i--) {
            Registration tmp = a[i];
            a[i] = a[0];
            a[0] = tmp;
            maxHeapAdjust(a, 0, i);
        }
    }

    private static void maxHeapAdjust(Registration[] a, int i, int len) {
        Registration tmp = a[i];
        int j = i * 2 + 1;
        while (j < len) {
            if ((j + 1) < len && a[j + 1].getId() > a[j].getId()) {
                j++;
            }
            if (a[j].getId() < tmp.getId()) {
                break;
            }
            a[i] = a[j];
            i = j;
            j = 2 * i + 1;
        }
        a[i] = tmp;
    }


}
