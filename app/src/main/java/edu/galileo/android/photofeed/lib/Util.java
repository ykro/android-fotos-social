package edu.galileo.android.photofeed.lib;

import android.location.Address;
import android.location.Geocoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import edu.galileo.android.photofeed.lib.base.BaseUtil;

/**
 * Created by ykro.
 */
public class Util implements BaseUtil {
    private Geocoder geocoder;
    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public Util(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @Override
    public String getAvatarUrl(String username) {
        return GRAVATAR_URL + md5(username) + "?s=64";
    }

    @Override
    public String getFromLocation(double lat, double lng) {
        String result = "";
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            result = addressFragments.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
