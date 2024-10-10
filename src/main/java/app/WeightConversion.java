package app;

import java.util.HashMap;
import java.util.Map;

public class WeightConversion {


    public static Map<String, Double> convertWeight(double weight, String unit) {
        double kg = convertToKilograms(weight, unit);

        Map<String, Double> conversions = new HashMap<>();
        conversions.put("Kilogram", kg);
        conversions.put("Gram", kgToGrams(kg));
        conversions.put("Metric Ton", kgToMetricTons(kg));
        conversions.put("Long Ton", kgToLongTons(kg));
        conversions.put("Short Ton", kgToShortTons(kg));
        conversions.put("Pound", kgToPounds(kg));
        conversions.put("Ounce", kgToOunces(kg));

        return conversions;
    }

    public static double convertToKilograms(double weight, String unit) {
        switch (unit) {
            case "Kilogram":
                return weight;
            case "Gram":
                return weight / 1000;
            case "Metric Ton":
                return weight * 1000;
            case "Long ton":
                return weight * 1016.0469088;
            case "Short Ton":
                return weight * 907.18474;
            case "Pound":
                return weight * 0.45359237;
            case "Ounce":
                return weight * 0.0283495231;
            default:
                throw new IllegalArgumentException("Ukendt enhed: " + unit);

        }
    }

    public static double kgToGrams(double kg) {
        return kg * 1000;
    }

    public static double kgToMetricTons(double kg) {
        return kg / 1000;
    }

    public static double kgToLongTons(double kg) {
        return kg / 1016.0469088;
    }

    public static double kgToShortTons(double kg) {
        return kg / 907.18474;
    }

    public static double kgToPounds(double kg) {
        return kg * 2.20462;
    }

    public static double kgToOunces(double kg) {
        return kg * 35.27396;
    }

}
