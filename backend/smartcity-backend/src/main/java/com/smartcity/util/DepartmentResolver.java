package com.smartcity.util;

import java.util.Map;

public class DepartmentResolver {

    private static final Map<String, String> CATEGORY_TO_DEPARTMENT = Map.of(
            "ROADS", "Road Department",
            "WATER", "Water Supply Department",
            "ELECTRICITY", "Electricity Department",
            "WASTE", "Waste Management",
            "TRAFFIC", "Traffic Control",
            "OTHERS", "General Services"
    );

    public static String getDepartmentName(String category) {
        if (category == null) return "General Services";
        return CATEGORY_TO_DEPARTMENT.getOrDefault(category.toUpperCase(), "General Services");
    }

    public static String getDepartment(String category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepartment'");
    }
}
