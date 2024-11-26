package com.shiraku.javacodespring.projection;

public interface EmployeeProjection {
    String getPosition();

    String getDepartmentName();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    String getFirstName();

    String getLastName();

}
