package com.kwarkbit.guessinggame;

import java.util.Random;

import com.kwarkbit.guessinggame.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class GuessingGame extends Activity {
    /** Called when the activity is first created. */
	
	SharedPreferences sp;
	static int RandomNumber;
	static int mode;
	static int difficulty;
	static int tries = 1;
	static int level = 1;
	final static int TOOLOW = -1;
	final static int TOOHIGH = 1;
	final static int EXACTLY = 0;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        getInitalValues();
        showKeyboard();
    }
    
    protected void onStop() {
    	finish();
    	super.onStop();
    }

	public void showKeyboard() {
		InputMethodManager imm = (InputMethodManager)
        this.getSystemService(Context.INPUT_METHOD_SERVICE);

	    if (imm != null){
	        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
	    }
	}
	
	public void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)
        this.getSystemService(Context.INPUT_METHOD_SERVICE);

	    if (imm != null){
	        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
	    }
	}
    
    public CountDownTimer makeTimer(int time) {
    	return new CountDownTimer(time, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				//TODO Auto Generated
			}
			
			@Override
			public void onFinish() {
				showStatsTimeMode();
			}
		};
    }
    
    public void reset(View view) {
    	((EditText)findViewById(R.id.input)).setText("");
    }

	public void getInitalValues() {
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		mode = Integer.parseInt(sp.getString("mode", "1"));
    	
		if(mode == 1) {
			setTitle("Guessing Game - Time Mode");
			startTimeMode();
		} else if(mode == 2) {
			startTriesMode();
			setTitle("Guessing Game - Tries Mode");
		}
	}

	public void startTriesMode() {
		difficulty = Integer.parseInt(sp.getString("difficulty", "2"));
		int difficulty10 = (int) Math.pow(10, difficulty);
    	RandomNumber = makeRandomNumber(difficulty10);
    	tries = 1;
	}
	
	public void startTimeMode() {
		RandomNumber = makeRandomNumber((int)Math.pow(10, level));
		int time = Integer.parseInt(sp.getString("time", "60000"));
		CountDownTimer cdt = makeTimer(time);
		cdt.cancel();
		cdt.start();
	}
    
    public void checkNumber(View view) {
    	String inputSting = ((EditText)findViewById(R.id.input)).getText().toString();
    	
    	if(!inputSting.equals("")) {
    		int input = Integer.parseInt(inputSting);
    		if(input < RandomNumber) {
    			announcePlayer(-1);
    		} else if(input > RandomNumber) {
    			announcePlayer(1);
    		} else if(input == RandomNumber) {
    			announcePlayer(0);
    		} else {
    			announcePlayer(10); //Just for debug
    		}
    		((EditText)findViewById(R.id.input)).setHint(inputSting);
    		((EditText)findViewById(R.id.input)).setText("");
    		
    	} else {
    		Toast.makeText(this, "No input given", Toast.LENGTH_SHORT).show();
    	}
    	
    }
    
    public void announcePlayer(int status) {
    	String output = "";
    	switch(status) {
    	case -1:
    		output = "Too low";
    		break;
    	case 1:
    		output = "Too high";
    		break;
    	case 0:
    		if(mode == 1) { raiseLevel(); output = "Level up!"; } else { output = "Congratulations"; showStatsTriesMode(); }
    		break;
    	default:
    		output = "Something went wrong";
    		break;
    	}
    	((TextView)findViewById(R.id.result)).setText(output);
    	if(mode == 2) {
    		tries += 1;
    	}
    }
    
    public void raiseLevel() {
    	level += 1;
    	RandomNumber = makeRandomNumber((int)Math.pow(10, level));
    }
    
    public void showStatsTriesMode() {
    	String stats = "The correct number was: " + RandomNumber + "\nNumber of tries: " + tries;
    	((TextView)findViewById(R.id.stats)).setText(stats);
    	endGame();
    }
    
    public void showStatsTimeMode() {
    	String stats = "You made it to level: " + level;
    	((TextView)findViewById(R.id.stats)).setText(stats);
    	((TextView)findViewById(R.id.result)).setText("Times up!");
    	endGame();
    }

	public void endGame() {
		((EditText)findViewById(R.id.input)).setText("");
		((EditText)findViewById(R.id.input)).setHint(Integer.toString(RandomNumber));
    	((Button)findViewById(R.id.back)).setVisibility(View.VISIBLE);
    	hideKeyboard();
	}
    
    public int makeRandomNumber(int limit) {
    	Random r = new Random();
    	return r.nextInt(limit);
    }
    
    public void back(View view) {
    	finish();
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.ingame_menu, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.endgame:
        	finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}