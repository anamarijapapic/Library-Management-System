package org.oss.LibraryManagementSystem.models.enums;

public enum Role {
    MEMBER("member"),
    LIBRARIAN("librarian"),
    ADMIN("admin");

    public final String label;

    private Role(String label) {
        this.label = label;
    }
}
