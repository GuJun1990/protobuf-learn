package org.example;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FieldFormat {

    /**
     * 将PB转成Map
     * @param message
     * @return
     */
    static public Map<String, Object> format(final Message message) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> field : message.getAllFields().entrySet()) {
            Descriptors.FieldDescriptor descriptor = field.getKey();
            Object value = field.getValue();
            if (descriptor.isRepeated()) {
                // List类型
                System.out.println("name: " + descriptor.getName() + "\ttype: list " + descriptor.getJavaType() + "\tvalue: " + value.toString());
                map.put(descriptor.getName(), value.toString());
            } else {
                System.out.println("name: " + descriptor.getName() + "\ttype: " + descriptor.getJavaType() + "\tvalue: " + value.toString());
                map.put(descriptor.getName(), value);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        PB.Info info = PB.Info.newBuilder()
                .setIsValid(true)
                .setChannelId(10086)
                .setExpandBit(123456789L)
                .addAllChildClipId(Arrays.asList(1L))
                .addAllVirtualChannel(Arrays.asList("a", "b", "c", "d"))
                .setSnsScore(0.99)
                .build();
        Map<String, Object> map = format(info);
        System.out.println(map);
        for (Map.Entry<String, Object> kv : map.entrySet()) {
            if (kv.getValue() instanceof Boolean) {
                System.out.println(kv.getKey() + ", BOOLEAN");
            } else if (kv.getValue() instanceof Integer) {
                System.out.println(kv.getKey() + ", INTEGER");
            } else if (kv.getValue() instanceof Long) {
                System.out.println(kv.getKey() + ", LONG");
            } else if (kv.getValue() instanceof Double) {
                System.out.println(kv.getKey() + ", DOUBLE");
            } else if (kv.getValue() instanceof String) {
                System.out.println(kv.getKey() + ", STRING");
            }
        }
    }
}
