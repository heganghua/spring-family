package xyz.ganghua.arithmetic;

import java.util.Arrays;

/**
 * 十大排序算法
 * 
 * @author ganghua
 * @date 2022/10/11
 */
public class TenSort {

    public static void main(String[] args) {
        int arr[] = {3, 4, 9, 1, 30, 5, 2, 10, -1};
        bubbleSort(arr);
        selectionSort(arr);
        quickSort(arr, 0, arr.length - 1);
        System.out.println("-----------");
        quickSort2(arr);
    }

    /**
     * 冒泡排序
     */
    public static void bubbleSort(int[] arr) {
        int temp = 0;
        boolean swap = false;
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swap = true;
                }
            }
            if (swap == false) {
                System.out.println("break is run!");
                break;
            }
        }
        System.out.print("冒泡排序： ");
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 选择排序
     * 
     * @param arr
     */
    public static void selectionSort(int[] arr) {
        int temp, min = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    min = j;
                }
            }
            if (min != i) {
                temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
        System.out.print("选择排序： ");
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 快速排序
     * 
     * @param arr
     */
    public static void quickSort(int[] arr, int start, int end) {
        if (start > end) {
            return;
        }
        int pivot = arr[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (arr[j] >= pivot && j > i) {
                j--;
            }
            while (arr[i] <= pivot && i < j) {
                i++;
            }
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        arr[start] = arr[i];
        arr[i] = pivot;
        quickSort(arr, start, i - 1);
        quickSort(arr, i + 1, end);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 快速排序2
     * 
     * @param arr
     */
    public static void quickSort2(int[] arr) {
        qSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void qSort(int[] arr, int low, int high) {
        if (low > high) {
            return;
        }
        // 1、将数组分为两部分
        int pivot = partition(arr, low, high);
        qSort(arr, low, pivot - 1);
        qSort(arr, pivot + 1, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];
        while (low < high) {
            while (arr[high] >= pivot && high > low) {
                high--;
            }
            arr[low] = arr[high];
            while (arr[low] <= pivot && low < high) {
                ++low;
            }
            arr[high] = arr[low];
        }
        System.out.println(low + ": " + high);
        arr[low] = pivot;
        return low;
    }

}
