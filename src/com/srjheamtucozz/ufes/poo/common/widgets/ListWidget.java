package com.srjheamtucozz.ufes.poo.common.widgets;

public class ListWidget extends Widget {
    private Iterable<Widget> children;

    public ListWidget(Iterable<Widget> children) {
        this.children = children;
    }

    @Override
    public void show() {
        for (Widget children : children) {
            showItem(children);
        }
    }

    protected void showItem(Widget children) {
        children.show();
    }
}
