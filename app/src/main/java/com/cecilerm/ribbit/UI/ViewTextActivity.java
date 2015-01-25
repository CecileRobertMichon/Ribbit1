package com.cecilerm.ribbit.UI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cecilerm.ribbit.R;


public class ViewTextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_text);

        Bundle extras = getIntent().getExtras();
        TextView chatText = (TextView) findViewById(R.id.chatText);

        if (extras != null) {
            String text = extras.getString("chatText");
            chatText.setText(text);
        }

    }
}
