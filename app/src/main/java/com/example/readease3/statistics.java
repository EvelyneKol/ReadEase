package com.example.readease3;

public class statistics {
    // Define a variable to store the selected statistics type
    private static String statisticsType;

    // Method to set the selected statistics type
    public static void setStatisticsType(String type) {
        statisticsType = type;
    }

    // Method to get the selected statistics type
    public static String statisticsType() {
        return statisticsType;
    }
}
