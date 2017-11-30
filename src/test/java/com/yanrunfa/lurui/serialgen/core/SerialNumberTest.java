package com.yanrunfa.lurui.serialgen.core;

import com.yanrunfa.lurui.serialgen.core.fragment.StreamFragment;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

public class SerialNumberTest {

    @Test
    public void testGetValue() {
        SerialNumberBuilder builder = new SerialNumberBuilder();
        builder.addStaticPart("RUI").addDatePart("yyyyMMdd").addStreamPart(3, StreamFragment.OverflowType.RESET);
        SerialNumber serialNumber = builder.build();
        for (int i=0; i< 1000; i++) {
            System.out.print(serialNumber.getValue()+"\n");
        }
    }

    @Test
    public void testGetValueOverflow() {
        SerialNumberBuilder builder = new SerialNumberBuilder();
        builder.addStaticPart("RUI").addDatePart("yyyyMMdd").addStreamPart(3, StreamFragment.OverflowType.THROW_EXCEPTION);
        SerialNumber serialNumber = builder.build();
        try {
            for (int i=0; i< 1000; i++) {
                System.out.print(serialNumber.getValue()+"\n");
            }
            Assert.fail();
        } catch (StreamFragment.StreamOverflowException e) {

        }
    }

    @Test
    public void testGetValueMultiThread() {
        SerialNumberBuilder builder = new SerialNumberBuilder();
        builder.addStaticPart("RUI").addDatePart("yyyyMMdd").addStreamPart(6, StreamFragment.OverflowType.THROW_EXCEPTION);
        SerialNumber serialNumber = builder.build();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        Set<String> resultSum = new HashSet<>();
        for (int i=0; i< 100; i++) {
            Future<List<String>> result = executor.submit(new GetValueCallable(serialNumber));
            try {
                resultSum.addAll(result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                Assert.fail();
            }
        }
        List<String> listResult = new ArrayList<>(resultSum);
        Collections.sort(listResult);
        for (String result: listResult) {
            System.out.println(result);
        }
        Assert.assertEquals(100*1000, listResult.size());
    }


    private class GetValueCallable implements Callable<List<String>> {
        private SerialNumber serialNumber;

        public GetValueCallable(SerialNumber serialNumber) {
            this.serialNumber = serialNumber;
        }

        @Override
        public List<String> call() throws Exception {
            List<String> results = new ArrayList<>();
            for (int i=0; i< 1000; i++) {
                String value = serialNumber.getValue();
                results.add(value);
                System.out.println(value);
            }
            return results;
        }
    }
}
