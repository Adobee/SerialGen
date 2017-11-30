package com.yanrunfa.lurui.serialgen.core;

import com.yanrunfa.lurui.serialgen.core.fragment.SerialFragment;

import java.util.ArrayList;
import java.util.List;

public class SerialNumber {

    private List<SerialFragment> fragments;

    public String getValue() {
        StringBuilder value = new StringBuilder();
        for (SerialFragment fragment: fragments) {
            value.append(fragment.getValue());
        }
        return value.toString();
    }

    public Integer getLength() {
        int length = 0;
        for (SerialFragment fragment: fragments) {
            length += fragment.getLength();
        }
        return length;
    }

    public void addFragment(SerialFragment fragment) {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.add(fragment);
    }
}
