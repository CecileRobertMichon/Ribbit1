package com.cecilerm.ribbit;

import android.app.Application;

import com.parse.Parse;

public class RibbitApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "jZKUzApk2etjqiOjRmkRsUQbodYI19s1BTaN4i9I",
				"QwyZtX8ycjzGMSDImMeQCPrn3QuIEfsS23tNsLju");


	}

}
