package com.ssannttoss.ciandt.mvc.view;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.ssannttoss.ciandt.R;
import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.mvc.view.AbstractActViewExt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActView extends AbstractActViewExt<Login> {
    @Override
    public int getCustomContainerId() {
        return R.id.frm_content;
    }

    @Override
    public int getLayoutContentId() {
        return R.layout.login_view;
    }

    @Override
    protected void onAddFragments() {
        addFragment(LoginFragView.newInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (request(this, REQUEST_PERMISSION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
        }
    }

    public static boolean request(Activity activity, int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final List<String> perms = new ArrayList<>();
            for (String permission : permissions) {
                int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    perms.add(permission);
                }
            }
            if (!perms.isEmpty()) {
                String[] arr = perms.stream().toArray(String[]::new);
                activity.requestPermissions(arr, requestCode);
                return false;
            }
        }
        return true;
    }
}
