package com.yanrunfa.lurui.serialgen.core;

import com.yanrunfa.lurui.serialgen.core.fragment.SerialFragment;
import com.yanrunfa.lurui.serialgen.core.fragment.StreamFragment;

import java.util.ArrayList;
import java.util.List;

public class SerialNumber {

    private List<SerialFragment> fragments;

    /**
     * 每个序列号有且仅有一个流水号字段
     */
    private StreamFragment streamFragment;

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
        if (fragment instanceof StreamFragment) {
            streamFragment = (StreamFragment) fragment;
        }
        fragments.add(fragment);
    }


    public List<SerialFragment> getFragments() {
        return fragments;
    }
}
