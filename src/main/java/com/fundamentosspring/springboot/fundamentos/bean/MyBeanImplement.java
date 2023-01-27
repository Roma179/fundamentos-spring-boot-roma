package com.fundamentosspring.springboot.fundamentos.bean;

public class MyBeanImplement implements MyBean{
    @Override
    public void print() {
        System.out.println("HELLO from my implement in my bean");
    }
}
