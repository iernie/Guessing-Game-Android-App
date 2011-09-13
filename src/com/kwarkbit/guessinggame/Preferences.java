package com.kwarkbit.guessinggame;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.inputmethod.InputMethodManager;

public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        hideKeyboard();
    }
    
    public void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)
        this.getSystemService(Context.INPUT_METHOD_SERVICE);

	    if (imm != null){
	        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
	    }
	}
}
