package com.ocean.cloudcms.controller;

import com.google.common.collect.Lists;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: chenhy
 * @Date: 2019/2/20 17:15
 * @Version 1.0
 */
public class QuarzController {

    public static void main(String[] args){

        Set<Integer> ilist = new HashSet<>();
        ilist.add(1);
        ilist.add(3);
        ilist.add(4);
        ilist.add(2);
        List<Integer> data = ilist.stream().sorted().collect(Collectors.toList());
        List<Integer> list = Lists.newArrayList(3, 5, 2, 9, 1);
        List<String> list2 = Lists.newArrayList("adb", "abbc", "cd", "d", "e");
        int maxInt = list.stream().min(Integer::compareTo).get();
        String tr = list2.stream().min(String::compareTo).get();
        System.out.println(list.stream().filter(x -> x>2).collect(Collectors.toList()));
        List<Integer>result = list.stream().filter(t-> t>5).sorted().collect(Collectors.toList());


    }

}
