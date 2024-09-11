package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static final int size = 10000000;
    private static final int sizeForTask = 40000000;

    private static final int h = size / 2;
    private static final int numThreads = 6; // кол-во потоков
    private static final int hForTask = sizeForTask / numThreads; // размер каждого подмассива
    private static int remainder = sizeForTask % numThreads; // остаток

    public static void main(String[] args) {
        float[] mainArray = new float[size];
        float[] taskArray = new float[sizeForTask];

        //заполнение массивов единицами
        Arrays.fill(mainArray, 1);
        Arrays.fill(taskArray, 1);

        System.out.println("Время выполнения первого метода: " + singleThread(mainArray) + "ms");
        System.out.println("Время выполнения второго метода: " + doubleThread(mainArray) + "ms");
        System.out.println("Время выполнения третьего метода: " + individualTask(taskArray) + "ms");
        //System.out.println("остаток: " + remainder);


    }
    public static long singleThread(float arr[]){          //один поток
        long start = System.currentTimeMillis();
        for (int i = 0; i < size;i++){
            arr[i]=(float)(arr[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
        }
        long totalTime = System.currentTimeMillis() - start;
        return totalTime;
    }
    public static long doubleThread(float arr[]){         //два потока
        float arr1[] = new float[h];
        float arr2[] = new float[h];
        long start = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        Thread thread1 = new Thread(()->{
            for (int i = 0; i < arr1.length;i++){
                arr1[i]=(float)(arr1[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
        });

        Thread thread2 = new Thread(()->{
            for (int i = 0; i < arr2.length;i++){
                arr2[i]=(float)(arr2[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
        });

        thread1.start();            //запускаем потоки
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        //System.out.println(arr[0] + " " + arr[arr.length-1]);

        long totalTime = System.currentTimeMillis() - start;
        return totalTime;
    }
    public static long individualTask(float[] arr) {
        float[][] subArrays = new float[numThreads][];          //массив из 6 подмассивов
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {                  //заполнение подмассивов
            int currentSize = (i == numThreads - 1) ? hForTask + remainder : hForTask;
            subArrays[i] = new float[currentSize];
            System.arraycopy(arr, i * hForTask, subArrays[i], 0, currentSize);

            int finalI = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < subArrays[finalI].length;j++){
                    int globalIndex = finalI * hForTask + j;
                    subArrays[finalI][j] = (float)(subArrays[finalI][j] * Math.sin(0.2f + globalIndex / 5.0) * Math.cos(0.2f + globalIndex / 5.0) * Math.cos(0.4f + globalIndex / 2.0));
                }
            });
        }

        long start = System.currentTimeMillis();

        for (Thread thread : threads) {     // запуск потоков
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < numThreads; i++) {
            System.arraycopy(subArrays[i], 0, arr, i * hForTask, subArrays[i].length);
        }

        System.out.println("Значение первой ячейки массива: " + arr[0]);
        System.out.println("Значение последней ячейки массива: " + arr[arr.length - 1]);

        long totalTime = System.currentTimeMillis() - start;

        return totalTime;
    }
}


