package com.yanrunfa.lurui.serialgen.core;


import com.yanrunfa.lurui.serialgen.core.fragment.DateFragment;
import com.yanrunfa.lurui.serialgen.core.fragment.StaticFragment;
import com.yanrunfa.lurui.serialgen.core.fragment.StreamFragment;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

public class SerialNumberBuilder {

    private SerialNumber serialNumber;

    /**
     * 序列号中必须要有流水号
     */
    private boolean streamPartAdded = false;


    public SerialNumberBuilder() {
        this.serialNumber = new SerialNumber();
    }

    /**
     * 添加固定值片段
     * @param value 固定值
     * @return this
     */
    public SerialNumberBuilder addStaticPart(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Parameter [value] cannot be Null");
        }
        this.serialNumber.addFragment(new StaticFragment(value));
        return this;
    }

    /**
     * 添加日期值字段
     * @param pattern 日期pattern
     * @return this
     */
    public SerialNumberBuilder addDatePart(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("Parameter [pattern] cannot be Null");
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        this.serialNumber.addFragment(new DateFragment(format));
        return this;
    }

    /**
     * 添加流水号字段
     * @param length 流水号长度
     * @param overflowType 溢出处理类型
     * @return this
     */
    public SerialNumberBuilder addStreamPart(Integer length, StreamFragment.OverflowType overflowType) {
        if (StringUtils.isEmpty(length)) {
            throw new IllegalArgumentException("Parameter [length] cannot be Null");
        }
        if (this.streamPartAdded) {
            throw new IllegalSerialNumberException("Only one stream part could be added");
        }
        this.serialNumber.addFragment( new StreamFragment(length, overflowType));
        this.streamPartAdded = true;
        return this;
    }

    public SerialNumber build() {
        if (!streamPartAdded) {
            throw new IllegalSerialNumberException("There must be one stream part in a SerialNumber");
        }
        return serialNumber;
    }

    public class IllegalSerialNumberException extends RuntimeException {
        public IllegalSerialNumberException(String msg) {
            super(msg);
        }
    }
}
