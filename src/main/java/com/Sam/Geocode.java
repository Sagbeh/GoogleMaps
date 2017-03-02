package com.Sam;


import com.google.maps.model.GeocodingResult;
import com.google.maps.GeocodingApi;
import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.LatLng;

import java.io.*;
import java.io.StreamCorruptedException;
import java.util.*;

public class Geocode {

    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        String key = null;
        //Read key from file
        try (BufferedReader reader = new BufferedReader(new FileReader("key2.txt"))) {
            key = reader.readLine();
        } catch (Exception ioe) {
            System.out.println("no key file found, or could not read key. Please verify key.txt present");
            System.exit(-1); //Quit program - need to fix before continuing.
        }


        System.out.println("Enter the name of a location: ");
        String location = stringScanner.nextLine();
        GeoApiContext context = new GeoApiContext().setApiKey(key);


        GeocodingResult[] result = GeocodingApi.geocode(context, location).await();

        for (int i = 0; i < result.length; i++) {
            System.out.println("[" + i + "]" + result[i].formattedAddress);
        }

        System.out.println("Enter the number for the location you want: ");
        int index = numberScanner.nextInt();



            LatLng latLng = result[index].geometry.location;
            ElevationResult[] results = ElevationApi.getByPoints(context, latLng).await();

            if (results.length >= 1) {

                ElevationResult Elevation = results[0];

                System.out.println(String.format("The elevation of " + location + " is %.2f meters above sea level.", Elevation.elevation));
                System.out.println(String.format("Latitude: " + latLng.lat));
                System.out.println(String.format("Longitude: " + latLng.lng));
            }
        }
    }
