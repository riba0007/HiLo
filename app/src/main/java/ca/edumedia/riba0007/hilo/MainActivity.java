/**
 *  MAD9132 1st Assignment - Simple Guessing game
 *  @author Priscila Ribas da Costa (riba0007@algonquinlive.com)
 */
package ca.edumedia.riba0007.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";

    final int MAX_GUESSES = 10;

    private int theNumber;
    private int remainingGuesses;

    private TextView tvNGuesses;
    private Button btGuess;
    private EditText edUserGuess;
    private ImageView icCompetitor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initiate global variables
        tvNGuesses = (TextView) findViewById(R.id.tvNGuesses);
        btGuess = (Button) findViewById(R.id.btGuess);
        edUserGuess = (EditText) findViewById(R.id.edUserGuess);
        icCompetitor = (ImageView) findViewById(R.id.icCompetitor);

        //set longClick function to reset button
        findViewById(R.id.btReset).setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                onLongClickListener();
                return true;
            }
        });

        //call a new game
        newGame();
    }

    private void newGame(){
        //new random Game
        Random randomNumGenerator = new Random();
        theNumber = randomNumGenerator.nextInt(1000) +1;

        Log.i("TheNumber", Integer.toString(theNumber));

        //default values
        remainingGuesses = MAX_GUESSES;
        tvNGuesses.setText(getResources().getString(R.string.game_remainingGuesses, remainingGuesses));
        btGuess.setText(R.string.btn_guess);
        icCompetitor.setImageResource(R.drawable.ic_mhappy);
    }

    private void guessNumber(){
        //decrease remaining guesses
        remainingGuesses--;

        //text to show in a Toast
        String text = "";

        //image id to show in icCompetitor
        int imageId = R.drawable.ic_mangry;

        if(remainingGuesses >= 0){

            try{
                //get user input
                int userGuess = 0;
                userGuess = Integer.parseInt(edUserGuess.getText().toString());

                //check user input
                if (userGuess == theNumber){

                    //check win message
                    if ((MAX_GUESSES - remainingGuesses) <= 5){
                        text = getResources().getString(R.string.game_greatWin);
                    } else {
                        text = getResources().getString(R.string.game_win);
                    }

                    //win image
                    imageId = R.drawable.ic_mwin;

                    remainingGuesses = 0;

                }else if (remainingGuesses == 0){

                    //lost the game
                    text = getResources().getString(R.string.game_lose);

                }else if (userGuess > theNumber){

                    //too high
                    text = getResources().getString(R.string.game_guessHigh, userGuess);


                }else if (userGuess < theNumber){

                    //too low
                    text = getResources().getString(R.string.game_guessLow, userGuess);

                }

            }catch (NumberFormatException nfe){

                text = getResources().getString(R.string.game_notNumber);

            }finally {

                //update screen
                edUserGuess.setText("");
                tvNGuesses.setText(getResources().getString(R.string.game_remainingGuesses, remainingGuesses));
                icCompetitor.setImageResource(imageId);

                if (remainingGuesses <= 0) {
                    btGuess.setText(R.string.game_PleaseReset);
                }

                Toast.makeText( getApplicationContext(), text, Toast.LENGTH_LONG ).show();
            }
        }else{

            //show lose message
            Toast.makeText( getApplicationContext(), R.string.game_PleaseReset, Toast.LENGTH_LONG ).show();

        }
    }

    private void onLongClickListener(){
        //create message
        String text = getResources().getString(R.string.game_result, theNumber);

        //show message
        Toast.makeText( getApplicationContext(), text, Toast.LENGTH_LONG ).show();

        //reset game
        newGame();
    }

    public void onClickBtReset(View v){
        newGame();
    }

    public void onClickBtGuess(View v){
        guessNumber();
    }
}
