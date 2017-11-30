package com.yanrunfa.lurui.serialgen.core.fragment;

import java.math.BigInteger;
import java.util.UUID;

public class StreamFragment implements SerialFragment {
    /**
     * 溢出后的处理类型
     */
    public enum OverflowType {
        /**
         * 重置为0
         */
        RESET,

        /**
         * 抛出异常
         */
        THROW_EXCEPTION
    }

    public class StreamOverflowException extends RuntimeException {
        public StreamOverflowException(String message) {
            super(message);
        }
    }

    private OverflowType overflowType;

    private BigInteger now = BigInteger.ONE;

    private Integer length;

    private BigInteger max = BigInteger.ZERO;

    private final UUID id = UUID.randomUUID();

    public StreamFragment(Integer length, OverflowType overflowType)  {
        this.length = length;
        if (overflowType == null) {
            overflowType = OverflowType.RESET;
        }
        this.overflowType = overflowType;
        StringBuilder maxString = new StringBuilder();
        for (int i=0; i< length; i++) {
            maxString.append(9);
        }
        this.max = new BigInteger(maxString.toString());
    }

    public StreamFragment(Integer length) {
        this(length, null);
    }

    @Override
    public String getValue() {
        BigInteger value;
        synchronized (id) {
            if (now.add(BigInteger.ONE).compareTo(max) > 0) {
                handleOverflow();
            }
            now = now.add(BigInteger.ONE);
            value = now;
        }
        return getStringValue(value);
    }

    /**
     * 处理序号达到上限的情况
     */
    private void handleOverflow() {
        switch (overflowType) {
            case RESET:
                now = BigInteger.ONE;
                break;
            case THROW_EXCEPTION:
                throw new StreamOverflowException("Stream overflow, ID: "+id.toString());
        }
    }

    /**
     * 把数字序号转化为String，并根据定义的长度补0
     * @param intValue  Integer value
     * @return String value
     */
    private String getStringValue(BigInteger intValue) {
        StringBuilder value = new StringBuilder();
        value.append(intValue);
        while (value.length() < length) {
            value.insert(0, 0);
        }
        return value.toString();
    }

    @Override
    public Integer getLength() {
        return length;
    }


}
