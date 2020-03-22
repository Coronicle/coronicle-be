package de.wirvsvirus.coronicle;

import de.wirvsvirus.coronicle.db.model.InfectedPoint;

import java.util.List;

import java.time.Duration;


public class InfectionChecker {

    //TODO: we need to specify this number!
    private static final double MAGIC_NUMBER = 10;
    private static final int VIRUS_IN_AIR_HOURS = 3;


    //TODO: optimize
    public static boolean check(List<InfectedPoint> userTrace, List<InfectedPoint> dataBaseTrace) {
        for (InfectedPoint userLocation : userTrace) {
            for (InfectedPoint dataBaseLocation : dataBaseTrace) {
                if (wasInSameTime(userLocation, dataBaseLocation) && wasInSameArea(userLocation, dataBaseLocation)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean wasInSameArea(InfectedPoint userLocation, InfectedPoint dataBaseLocation) {
        double deltaLat = Math.toRadians(userLocation.getLat() - dataBaseLocation.getLat());
        double deltaLon = Math.toRadians(userLocation.getLon() - dataBaseLocation.getLon());
        double alpha = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(Math.toRadians(userLocation.getLat())) * Math.cos(Math.toRadians(dataBaseLocation.getLat()))
                        * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(alpha), Math.sqrt(1 - alpha));
        double EARTH_RADIUS = 6371;
        return (EARTH_RADIUS * c * 1000) < MAGIC_NUMBER;
    }

    private static boolean wasInSameTime(InfectedPoint userLocation, InfectedPoint dataBaseLocation) {
        return Duration.between(userLocation.getTime(), dataBaseLocation.getTime()).compareTo(Duration.ofHours(VIRUS_IN_AIR_HOURS)) < 0;
    }
}


