package com.yanrunfa.lurui.serialgen.core.fragment;

public class StaticFragment implements SerialFragment {

    private String value;

    public StaticFragment(String staticValue) {
        this.value = staticValue;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Integer getLength() {
        return value.length();
    }
}
