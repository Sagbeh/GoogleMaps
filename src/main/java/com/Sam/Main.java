package com.Sam;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.LatLng;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.StreamCorruptedException;

public class Main {

    public static void main(String[] args) throws Exception {

        String key = null;
        //Read key from file
        try (BufferedReader reader = new BufferedReader(new FileReader("key.txt"))) {
            key = reader.readLine();
            System.out.println(key);  //Just checking...
        } catch (Exception ioe) {
            System.out.println("no key file found, or could not read key. Please verify key.txt present");
            System.exit(-1); //Quit program - need to fix before continuing.
        }

        GeoApiContext context = new GeoApiContext().setApiKey(key);

        LatLng mctcLatlng = new LatLng(44.973074, -93.283356);

        ElevationResult[] results = ElevationApi.getByPoints(context, mctcLatlng).await();

        if (results.length >=1) {

            ElevationResult mctcElevation = results[0];
            System.out.println("The elevatio of MCTC avbove sea level is " + mctcElevation.elevation + " meters");

            System.out.println(String.format("the elevation of MCTC above sea level is %.2f meters.", mctcElevation.elevation));
        }
    }
}
