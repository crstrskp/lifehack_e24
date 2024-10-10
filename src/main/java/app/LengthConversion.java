package app;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class LengthConversion {

    public static void addRoutes(Javalin app) {
        app.post("convertLength", ctx -> convertLength(ctx));
    }

    public static Map<String, Double> convertLength(Context ctx) {
        System.out.println(ctx.formParam("lengthUnit"));

        double length = Double.parseDouble(ctx.formParam("length")); // Hent længde her som et tal
        String unit = ctx.formParam("lengthUnit"); // Hent enheden her
        double meters = convertToMeters(length, unit);

        // Læg alle konverteringer i en HashMap
        Map<String, Double> conversions = new HashMap<>();
        conversions.put("Meters", meters);
        conversions.put("Centimeters", metersToCentimeters(meters));
        conversions.put("Millimeters", metersToMillimeters(meters));
        conversions.put("Kilometers", metersToKilometers(meters));
        conversions.put("Feet", metersToFeet(meters));
        conversions.put("Inches", metersToInches(meters));
        conversions.put("Miles", metersToMiles(meters));


        ctx.json(conversions);
        return conversions;
    }

    // Konverter til meter baseret på den indsendte enhed
    public static double convertToMeters(double length, String unit) {
        switch (unit) {
            case "Meters":
                return length;
            case "Centimeters":
                return length / 100;
            case "Millimeters":
                return length / 1000;
            case "Kilometers":
                return length * 1000;
            case "Feet":
                return length * 0.3048;
            case "Inches":
                return length * 0.0254;
            case "Miles":
                return length * 1609.344;
            default:
                throw new IllegalArgumentException("Ukendt enhed: " + unit);
        }
    }

    // Konverteringsmetoder fra meter til andre enheder
    public static double metersToCentimeters(double meters) {
        return meters * 100;
    }

    public static double metersToMillimeters(double meters) {
        return meters * 1000;
    }

    public static double metersToKilometers(double meters) {
        return meters / 1000;
    }

    public static double metersToFeet(double meters) {
        return meters / 0.3048;
    }

    public static double metersToInches(double meters) {
        return meters / 0.0254;
    }

    public static double metersToMiles(double meters) {
        return meters / 1609.344;
    }
}
