package com.kwarkbit.guessinggame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class StartScreen extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.startscreen);
	    
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    
	    hideKeyboard();
	}
	
	public void play(View view) {
		Intent game = new Intent(this, GuessingGame.class);
		startActivity(game);
	}
	
	public void exit(View view) {
		finish();
	}
	
	public void showPrefs() {
    	Intent prefs = new Intent(this, Preferences.class);
    	startActivity(prefs);
    }
    
    public void showHelp() {
    	Intent prefs = new Intent(this, HelpDialog.class);
    	startActivity(prefs);
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.app_menu, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.preferences:
        	showPrefs();
            return true;
        case R.id.help:
        	showHelp();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)
        this.getSystemService(Context.INPUT_METHOD_SERVICE);

	    if (imm != null){
	        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
	    }
	}

}
