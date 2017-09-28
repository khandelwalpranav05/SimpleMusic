package com.example.pranav.playmusic;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by Pranav on 01-07-2017.
 */

public class Perman {

    public interface OnPermissionRequestResult {
        void onGranted(String permission);
        void onDenied(String permission);
    }

    static class PermissionResult {
        String permission;
        OnPermissionRequestResult requestResultListener;

        public PermissionResult(String permission, OnPermissionRequestResult requestResultListener) {
            this.permission = permission;
            this.requestResultListener = requestResultListener;
        }
    }

    static ArrayList<PermissionResult> permissionResults = new ArrayList<>();

    public static void askForPermission (Activity act, String permission, OnPermissionRequestResult oprr) {

        if (ContextCompat.checkSelfPermission(act, permission) != PackageManager.PERMISSION_GRANTED) {
            // ask
            int reqCode = permissionResults.size();
            permissionResults.add(reqCode, new PermissionResult(permission, oprr));
            ActivityCompat.requestPermissions(act,
                    new String[] {permission}, reqCode);
        } else {
            oprr.onGranted(permission);
        }

    }

    public static void onPermissionResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        PermissionResult perRes = permissionResults.get(requestCode);

        if (permissions[0].equals(perRes.permission)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                perRes.requestResultListener.onGranted(perRes.permission);
            } else {
                perRes.requestResultListener.onDenied(perRes.permission);
            }
        }
        permissionResults.remove(requestCode);
        permissionResults.trimToSize();
    }
}

