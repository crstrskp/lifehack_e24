package app;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class WeightConversion {


    public static void addRoutes(Javalin app) {
        app.post("convertWeight", ctx -> convertWeight(ctx));
    }

    // Læg alle konverteringer i en HashMap
    public static Map<String, Double> convertWeight(Context ctx) {
        double weight = Double.parseDouble(ctx.formParam("weight")); //Hent vægten her
        String unit = ctx.formParam("unit"); // Hent enheden her
        double kg = convertToKilograms(weight, unit);
        Map<String, Double> conversions = new HashMap<>();
        conversions.put("Kilogram", kg);
        conversions.put("Gram", kgToGrams(kg));
        conversions.put("Metric Ton", kgToMetricTons(kg));
        conversions.put("Long Ton", kgToLongTons(kg));
        conversions.put("Short Ton", kgToShortTons(kg));
        conversions.put("Pound", kgToPounds(kg));
        conversions.put("Ounce", kgToOunces(kg));


        ctx.json(conversions);
        return conversions;
    }

    public static double convertToKilograms(double weight, String unit) {
        switch (unit.toLowerCase()) {
            case "kilogram":
                return weight;
            case "gram":
                return weight / 1000;
            case "metric ton":
                return weight * 1000;
            case "long ton":
                return weight * 1016.0469088;
            case "short ton":
                return weight * 907.18474;
            case "pound":
                return weight * 0.45359237;
            case "ounce":
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