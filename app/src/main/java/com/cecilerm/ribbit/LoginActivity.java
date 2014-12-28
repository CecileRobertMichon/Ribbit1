package com.cecilerm.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class LoginActivity extends Activity {

	protected TextView mSignUpTextView;
	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mLoginButton;
	protected TextView mForgotPasswordTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);

		mSignUpTextView = (TextView) findViewById(R.id.SignUpText);
		mSignUpTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(LoginActivity.this,
						SignUpActivity.class);
				startActivity(intent);

			}
		});

		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);

		mForgotPasswordTextView = (TextView) findViewById(R.id.ForgotPasswordText);
		mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder prompt = new AlertDialog.Builder(
						LoginActivity.this)
						.setTitle(R.string.enter_email_title).setMessage(
								R.string.enter_email_message);

				// Set an EditText view to get user input
				final EditText input = new EditText(LoginActivity.this);
				prompt.setView(input).setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String email = input.getText().toString();
								// Do something with value!
								setProgressBarIndeterminateVisibility(true);
								ParseUser.requestPasswordResetInBackground(
										email,
										new RequestPasswordResetCallback() {
											public void done(ParseException e) {
												setProgressBarIndeterminateVisibility(false);
												if (e == null) {
													// An email was successfully
													// sent with reset
													// instructions.
													AlertDialog.Builder builder = new AlertDialog.Builder(
															LoginActivity.this);
													builder.setMessage(
															R.string.email_sent_message)
															.setTitle(
																	R.string.email_sent_title)
															.setPositiveButton(
																	android.R.string.ok,
																	null);
													AlertDialog dialog = builder
															.create();
													dialog.show();
												} else {
													// Something went wrong.
													// Look at the
													// ParseException to see
													// what's up.
													AlertDialog.Builder builder = new AlertDialog.Builder(
															LoginActivity.this);
													builder.setMessage(
															e.getMessage())
															.setTitle(
																	R.string.login_error_title)
															.setPositiveButton(
																	android.R.string.ok,
																	null);
													AlertDialog dialog = builder
															.create();
													dialog.show();
												}
											}
										});
							}
						});

				prompt.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

				prompt.show();

			}
		});

		mLoginButton = (Button) findViewById(R.id.LoginButton);
		mLoginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();

				username = username.trim();
				password = password.trim();

				if (username.isEmpty() || password.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							LoginActivity.this);
					builder.setMessage(R.string.login_error_message)
							.setTitle(R.string.login_error_title)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();

				} else {
					setProgressBarIndeterminateVisibility(true);
					// Login
					ParseUser.logInInBackground(username, password,
							new LogInCallback() {
								@Override
								public void done(ParseUser user,
										ParseException e) {
									setProgressBarIndeterminateVisibility(false);
									if (e == null) {
										// Hooray! The user is logged in.
										Intent intent = new Intent(
												LoginActivity.this,
												MainActivity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
										startActivity(intent);
									} else {
										// Login failed. Look at the
										// ParseException to see what happened.
										AlertDialog.Builder builder = new AlertDialog.Builder(
												LoginActivity.this);
										builder.setMessage(e.getMessage())
												.setTitle(
														R.string.login_error_title)
												.setPositiveButton(
														android.R.string.ok,
														null);
										AlertDialog dialog = builder.create();
										dialog.show();
									}
								}
							});
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
}
