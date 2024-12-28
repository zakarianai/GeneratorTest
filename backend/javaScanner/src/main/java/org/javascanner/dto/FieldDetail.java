package org.javascanner.dto;

public class FieldDetail {
    private String name;   // Field name
    private String type;   // Field type (e.g., int, String)

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
