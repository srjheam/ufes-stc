package com.srjheamtucozz.ufes.poo.common.widgets;

public class Text extends Widget {
    private String content;

    public Text(String content) {
        this.content = content;
    }

    @Override
    public void show() {
        System.out.println(content);
    }
}
