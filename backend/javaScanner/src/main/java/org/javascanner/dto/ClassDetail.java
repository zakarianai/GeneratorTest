package org.javascanner.dto;

import java.util.List;

public class ClassDetail {

    private String className;
    private Type type;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private String packageName;
    private List<FieldDetail> fields;
    private List<MethodDetail> methodDetail;

    public String getClassName() {
        return className;
    }

    public List<FieldDetail> getFields() {
        return fields;
    }

    public void setFields(List<FieldDetail> fields) {
        this.fields = fields;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<MethodDetail> getMethodDetail() {
        return methodDetail;
    }

    public void setMethodDetail(List<MethodDetail> methodDetail) {
        this.methodDetail = methodDetail;
    }
}
