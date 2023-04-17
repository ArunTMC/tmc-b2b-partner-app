package com.tmc.tmcb2bpartnerapp.modal;
public class Model_Test {

    private String text;
    private boolean isSelected = false;

    public Model_Test(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}