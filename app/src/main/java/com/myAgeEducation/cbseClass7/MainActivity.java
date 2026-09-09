package com.myAgeEducation.cbseClass7;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;*/

public class MainActivity extends Activity {

    private String android_id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_login);
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Util.Android_id = android_id;
        Log.d("AndroidId", android_id);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String savedLogin = sharedPreferences.getString("uuid", "");

        if(savedLogin.isEmpty() || savedLogin.length() < 32){
            try {
                //Firebase.goOnline();
                //login();
            } catch (Exception ex) {
                findViewById(R.id.buttonRetry).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.txtHeading)).setText(ex.getMessage());
            }
        }
        else
        {
            Util.UserUid = savedLogin;
            Intent intent = new Intent(MainActivity.this, SubjectList.class);
            startActivity(intent);
            finish();
        }
    }


    public void onClickRetry(View view)
    {
        view.setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.txtHeading)).setText("Trying to connect the server, please wait...");
//        login();
    }
}
