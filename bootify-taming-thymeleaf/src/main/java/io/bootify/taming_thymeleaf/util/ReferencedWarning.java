package io.bootify.taming_thymeleaf.util;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class ReferencedWarning {

    private String key = null;
    private ArrayList<Object> params = new ArrayList<>();

    public void addParam(final Object param) {
        params.add(param);
    }

    public String toMessage() {
        String message = key;
        if (!params.isEmpty()) {
            message += "," + params.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
        }
        return message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public ArrayList<Object> getParams() {
        return params;
    }

    public void setParams(final ArrayList<Object> params) {
        this.params = params;
    }

}
