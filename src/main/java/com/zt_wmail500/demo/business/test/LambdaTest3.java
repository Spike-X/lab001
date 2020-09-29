package com.zt_wmail500.demo.business.test;

import com.zt_wmail500.demo.business.pojo.Item;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @program: demo
 * @description: test3
 * @author: tao.zhang
 * @create: 2020-08-09 21:59
 **/
public class LambdaTest3 {
    public static void main(String[] args) {
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item(13, "背心", 7.80));
        list.add(new Item(11, "半袖", 37.80));
        list.add(new Item(14, "风衣", 139.80));
        list.add(new Item(12, "秋裤", 55.33));
        Item item = Item.builder()
                .id(10)
                .name("衬衫")
                .price(23.20).build();
        list.add(item);

        /*
        list.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getId()  - o2.getId();
            }
        });
        */

//        list.sort(Comparator.comparingInt(Item::getId));
        list.sort(Comparator.comparing(Item::getPrice));
        list.forEach(System.out::println);
    }
}
