package com.yanrunfa.lurui.serialgen.core.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFragment implements SerialFragment {
    private SimpleDateFormat dateFormat;

    public DateFragment(SimpleDateFormat format) {
        this.dateFormat = format;
    }

    @Override
    public String getValue() {
        return dateFormat.format(new Date());
    }

    @Override
    public Integer getLength() {
        return dateFormat.toPattern().length();
    }
}
