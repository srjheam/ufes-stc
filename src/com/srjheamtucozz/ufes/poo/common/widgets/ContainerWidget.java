package com.srjheamtucozz.ufes.poo.common.widgets;

public class ContainerWidget extends Widget {
    private Widget children;

    private int paddingTop;
    private int paddingBottom;

    public ContainerWidget(Widget children) {
        this(children, 0);
    }

    public ContainerWidget(Widget children, int padding) {
        this(children, padding, padding);
    }

    public ContainerWidget(Widget children, int paddingTop, int paddingBottom) {
        this.children = children;
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
    }

    @Override
    public void show() {
        for (int i = 0; i < paddingTop; i++) {
            System.out.println();
        }
        children.show();
        for (int i = 0; i < paddingBottom; i++) {
            System.out.println();
        }
    }
}
