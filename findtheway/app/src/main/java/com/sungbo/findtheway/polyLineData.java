package com.sungbo.findtheway;

import android.content.Context;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.sungbo.findtheway.data.Direction;
import com.sungbo.findtheway.data.Step;
import com.sungbo.findtheway.routeData.RouteTransit;
import com.sungbo.findtheway.routeData.RouteWalk;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class polyLineData {

    public static HashMap<String, LatLng> getMarkerPoint(Direction direction, Context context){
        HashMap<String, LatLng> list = new HashMap<>();

        List<Step> step = direction.getRoutes().get(0).getLegs().get(0).getSteps();

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        String markerTitle = "";

        for(int i = 0 ; i < step.size(); i++){
            LatLng latlng = new LatLng(step.get(i).getEndLocation().getLat(), step.get(i).getEndLocation().getLng());
            try {
                markerTitle = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1).get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            list.put(markerTitle, latlng);
        }

        return list;
    }


    public static List<LatLng> getPolyLines(Direction direction) {
        List<LatLng> list = new ArrayList<LatLng>();

        List<Step> step = direction.getRoutes().get(0).getLegs().get(0).getSteps();

        for (int i = 0; i < step.size(); i++) {
            String polyline = step.get(i).getPolyline().getPoints();

            list.addAll(decodePoly(polyline));

        }
        return list;
    }

    private static List decodePoly(String polyline) {

        List list = new ArrayList();
        int index = 0, len = polyline.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = polyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = polyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            list.add(p);
        }

        return list;
    }
}
