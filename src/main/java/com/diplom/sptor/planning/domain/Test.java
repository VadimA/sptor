package com.diplom.sptor.planning.domain;

import java.util.*;

/**
 * Created by user on 11.02.2018.
 */

public class Test {

    static void changeString(String str){
        str = "World";
    }

    static void arrayString(String [] arr){
        arr[1] = "Abba";
        arr[0] = "Happy";
    }
    public static void main(String [] args){
        String s1 = "Hello";
        changeString(s1);
        System.out.println(s1);
        String [] arr = new String[2];
        arr[0] = "Bollo";
        arr[1] = "Heheh";
        arrayString(arr);
        System.out.println(arr[0] + " 1= " + arr[1]);

        List<String> list1 = new ArrayList<>();
        list1.add("foo");

        List<String> list2 = list1;
        List<String> list3 = new ArrayList<>(list2);

        list1.clear();
        list2.add("bar");
        list3.add("baz");
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);

    }
}
