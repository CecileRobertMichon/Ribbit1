package com.cecilerm.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class SendTextActivity extends Activity {

    protected EditText mMessage;
    protected ImageButton mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_send_text);

        mMessage = (EditText) findViewById(R.id.sendChat);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessage.getText().toString();
                if (message.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            SendTextActivity.this);
                    builder.setMessage(R.string.send_text_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    setProgressBarVisibility(true);
                    // start recipient activity
                    Intent recipientsIntent = new Intent(SendTextActivity.this, RecipientsActivity.class);
                    String fileType = ParseConstants.TYPE_TEXT;
                    String time = Long.toString(System.currentTimeMillis());
                    recipientsIntent.putExtra(ParseConstants.KEY_MESSAGE, message);
                    recipientsIntent.putExtra(ParseConstants.KEY_FILE_TYPE, fileType);
                    recipientsIntent.putExtra(ParseConstants.KEY_CREATED_AT, time);
                    startActivity(recipientsIntent);
                    setProgressBarVisibility(false);
                    finish();
                }
            }
        });
    }

}
