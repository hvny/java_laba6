package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static final int size = 10000000;
    private static final int h = size / 2;
    private static float[] mainArray = new float[size];

    private static final int sizeForTask = 40000000;
    private static final int hForTask = sizeForTask / 6;
    private static int remainder = sizeForTask % 6;
    private static float[] taskArray = new float[sizeForTask];
    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            mainArray[i] = 1;
        }

        for (int i = 0; i < sizeForTask; i++) {
            taskArray[i] = 1;
        }

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
                //System.out.println("Second thread elem: " + arr[i]);
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

   /* public static float[] createThread(float arr[]) { //функция создания потоков
        float tempArr[] = new float[hForTask];
        System.arraycopy(arr, 0, tempArr, 0, hForTask);
        //System.out.println("length: " + arr.length);
        new Thread(() -> {
            for (int j = 0; j < arr.length;j++){
                arr[j]=(float)(arr[j] * Math.sin(0.2f + j/5) * Math.cos(0.2f + j/5) * Math.cos(0.4f + j/2));
                System.out.println("elem: " + arr[j]);

            }
        }).start();
        *//*for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }*//*
        return arr;
    }*/

    public static long individualTask(float arr[]) {
        long start = System.currentTimeMillis();
        float tempArr[] = new float[hForTask];
        float tempArr2[] = new float[hForTask];
        float tempArr3[] = new float[hForTask];
        float tempArr4[] = new float[hForTask];
        float tempArr5[] = new float[hForTask];
        float tempArr6[] = new float[hForTask+remainder];
        ArrayList<float[]> listOfArrs = new ArrayList<>();

        System.arraycopy(arr, 0, tempArr, 0, hForTask);
        System.arraycopy(arr, hForTask, tempArr2, 0, hForTask);
        System.arraycopy(arr, hForTask*2, tempArr3, 0, hForTask);
        System.arraycopy(arr, hForTask*3, tempArr4, 0, hForTask);
        System.arraycopy(arr, hForTask*4, tempArr5, 0, hForTask);
        System.arraycopy(arr, hForTask*5, tempArr6, 0, hForTask+remainder);

        Thread thread1 = new Thread(()->{
            for (int i = 0; i < tempArr.length;i++){
                tempArr[i]=(float)(tempArr[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
        });

        Thread thread2 = new Thread(()->{
            for (int i = 0; i < tempArr2.length;i++){
                tempArr2[i]=(float)(tempArr2[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
        });

        Thread thread3 = new Thread(()->{
            for (int i = 0; i < tempArr3.length;i++){
                tempArr3[i]=(float)(tempArr3[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
        });

        Thread thread4 = new Thread(()->{
            for (int i = 0; i < tempArr4.length;i++){
                tempArr4[i]=(float)(tempArr4[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
        });

        Thread thread5 = new Thread(()->{
            for (int i = 0; i < tempArr5.length;i++){
                tempArr5[i]=(float)(tempArr5[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
        });

        Thread thread6 = new Thread(()->{
            for (int i = 0; i < tempArr6.length;i++){
                tempArr6[i]=(float)(tempArr6[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
            }
        });

        thread1.start();            //запускаем потоки
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

       /* listOfArrs.add(tempArr);
        listOfArrs.add(tempArr2);
        listOfArrs.add(tempArr3);
        listOfArrs.add(tempArr4);
        listOfArrs.add(tempArr5);
        listOfArrs.add(tempArr6);

        for (int i = 0; i < listOfArrs.size(); i++) {
            createThread(listOfArrs.get(i));
        }*/

        System.arraycopy(tempArr, 0, arr, 0, hForTask);
        System.arraycopy(tempArr2, 0, arr, hForTask, hForTask);
        System.arraycopy(tempArr3, 0, arr, hForTask*2, hForTask);
        System.arraycopy(tempArr4, 0, arr, hForTask*3, hForTask);
        System.arraycopy(tempArr5, 0, arr, hForTask*4, hForTask);
        System.arraycopy(tempArr6, 0, arr, hForTask*5, hForTask+remainder);

        System.out.println("Значение первой ячейки массива: " + arr[0] + "\nЗначение последней ячейки: " + arr[arr.length-1]);

        long totalTime = System.currentTimeMillis() - start;
        return totalTime;
    }

}


