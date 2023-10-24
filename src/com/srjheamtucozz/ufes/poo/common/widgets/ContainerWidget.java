package com.srjheamtucozz.ufes.poo.common.widgets;

public class ContainerWidget extends Widget {
    private Widget children;

    private int paddingTop;
    private int paddingBottom;

    public ContainerWidget(Widget content) {
        this(content, 0);
    }

    public ContainerWidget(Widget content, int padding) {
        this(content, padding, padding);
    }

    public ContainerWidget(Widget content, int paddingTop, int paddingBottom) {
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
