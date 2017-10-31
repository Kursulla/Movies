package com.eutechpro.movies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements MvpActivityCallback {
    public static final String TAG = "BaseActivity";

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    @Override
    public void showToast(@StringRes int messageId, boolean shouldKillActivity) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
        killIfNeeded(shouldKillActivity);
    }

    @Override
    public void showToast(@StringRes int messageId, String params, boolean shouldKillActivity) {
        Toast.makeText(this, getString(messageId, params), Toast.LENGTH_SHORT).show();
        killIfNeeded(shouldKillActivity);
    }

    @Override
    public void openActivity(Bundle bundle, Class activityClass, boolean shouldKillActivity) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivity(intent);
        killIfNeeded(shouldKillActivity);
    }

    @Override
    public void openActivity(Bundle bundle, int requestCode, Class activityClass, boolean shouldKillActivity) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        killIfNeeded(shouldKillActivity);
    }

    @Override
    public void openActivity(Intent intent, boolean shouldKillActivity) {
        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null){
            startActivity(intent);
            killIfNeeded(shouldKillActivity);
        } else {
            Log.e(TAG, "openActivity: ", new IllegalStateException("Can't fire Intent! No application for that job!"));
        }

    }

    private void killIfNeeded(boolean shouldKillActivity) {
        if (shouldKillActivity){
            finish();
        }
    }
}
