package lathigara.harsh.flipkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import lathigara.harsh.flipkart.Activities.RegisterActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        SystemClock.sleep(3000);
        Intent loginintent = new Intent(SplashActivity.this, RegisterActivity.class);
        startActivity(loginintent);
        finish();
    }
}
