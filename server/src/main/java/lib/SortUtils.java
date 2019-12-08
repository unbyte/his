package lib;

public class SortUtils {
    private static void merge(Long[] a, int left, int mid, int right) {
        Long[] tmp = new Long[a.length];
        int p1 = left, p2 = mid + 1, k = left;

        while (p1 <= mid && p2 <= right) {
            if (a[p1] <= a[p2])
                tmp[k++] = a[p1++];
            else
                tmp[k++] = a[p2++];
        }

        while (p1 <= mid) tmp[k++] = a[p1++];
        while (p2 <= right) tmp[k++] = a[p2++];

        if (right + 1 - left >= 0)
            System.arraycopy(tmp, left, a, left, right + 1 - left);
    }

    public static void mergeSort(Long[] a, int start, int end) {
        if (start >= end)
            return;
        int mid = (start + end) / 2;
        mergeSort(a, start, mid);
        mergeSort(a, mid + 1, end);
        merge(a, start, mid, end);
    }


}
