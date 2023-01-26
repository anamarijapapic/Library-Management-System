package org.oss.LibraryManagementSystem.models.enums;

public enum Status {

    OK("OK"),
    DAMAGED("damaged"),
    LOST("lost");

    public final String label;

    private Status(String label) {
        this.label = label;
    }

}
