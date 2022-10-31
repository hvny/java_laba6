package org.example;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    private static final int size = 10000000;
    private static final int h = size / 2;
    private static float[] mainArray = new float[size];

    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            mainArray[i] = 1;
        }

        System.out.println("Время выполнения первого метода: " + singleThread(mainArray) + "ms");
        System.out.println("Время выполнения второго метода: " + doubleThread(mainArray) + "ms");

        /*
        new Thread (()->e1.singleThread(mainArray)).start();
        new Thread (()->e1.doubleThread(mainArray)).start();
        */
    }
    public static long singleThread(float arr[]){          //один поток
        long start = System.currentTimeMillis();
        for (int i = 0; i < size;i++){
            arr[i]=(float)(arr[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
        }
        long total = System.currentTimeMillis() - start;
        return total;
    }
    public static long doubleThread(float arr[]){         //два потока
        float arr1[] = new float[h];
        float arr2[] = new float[h];
        long start = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        new Thread (()->{
            for (int i = 0; i < arr1.length;i++){
                arr1[i]=(float)(arr1[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
            //System.out.println("1: "+ Arrays.toString(arr1));
        }).start();

        new Thread (()->{
            for (int i = 0; i < arr2.length;i++){
                arr2[i]=(float)(arr2[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
            //System.out.println("2: "+ Arrays.toString(arr2));
        }).start();
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        long total = System.currentTimeMillis() - start;
        return total;
    }
}


