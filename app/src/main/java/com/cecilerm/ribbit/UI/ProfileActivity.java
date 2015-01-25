package com.cecilerm.ribbit.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.cecilerm.ribbit.R;


public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle extras = getIntent().getExtras();
        TextView usernameText = (TextView) findViewById(R.id.username);
        TextView firstNameText = (TextView) findViewById(R.id.firstName);
        TextView lastNameText = (TextView) findViewById(R.id.lastName);
        TextView ageText = (TextView) findViewById(R.id.age);
        TextView hometownText = (TextView) findViewById(R.id.hometown);

        if (extras != null) {
            String username = extras.getString("username");
            usernameText.setText(username);
            String firstName = extras.getString("firstName");
            firstNameText.setText(firstName);
            String lastName = extras.getString("lastName");
            lastNameText.setText(lastName);
            String age = extras.getString("age");
            ageText.setText(age);
            String hometown = extras.getString("hometown");
            hometownText.setText(hometown);
        }

    }

}
