package com.yanrunfa.lurui.serialgen.core;


import com.yanrunfa.lurui.serialgen.core.fragment.DateFragment;
import com.yanrunfa.lurui.serialgen.core.fragment.StaticFragment;
import com.yanrunfa.lurui.serialgen.core.fragment.StreamFragment;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

public class SerialNumberBuilder {

    private SerialNumber serialNumber;

    public SerialNumberBuilder() {
        this.serialNumber = new SerialNumber();
    }

    public SerialNumberBuilder addStaticPart(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Parameter [value] cannot be Null");
        }
        this.serialNumber.addFragment(new StaticFragment(value));
        return this;
    }

    public SerialNumberBuilder addDatePart(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("Parameter [pattern] cannot be Null");
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        this.serialNumber.addFragment(new DateFragment(format));
        return this;
    }

    public SerialNumberBuilder addStreamPart(Integer length, StreamFragment.OverflowType overflowType) {
        if (StringUtils.isEmpty(length)) {
            throw new IllegalArgumentException("Parameter [length] cannot be Null");
        }
        this.serialNumber.addFragment(new StreamFragment(length, overflowType));
        return this;
    }

    public SerialNumber build() {
        return serialNumber;
    }
}
