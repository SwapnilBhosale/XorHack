package xoriant.com.xorhack.activitiy;

import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import ai.api.model.Status;
import edu.washington.cs.touchfreelibrary.sensors.CameraGestureSensor;
import edu.washington.cs.touchfreelibrary.utilities.LocalOpenCV;
import edu.washington.cs.touchfreelibrary.utilities.PermissionUtility;
import xoriant.com.xorhack.R;
import xoriant.com.xorhack.config.Constants;
import xoriant.com.xorhack.listeners.*;
import xoriant.com.xorhack.modules.DayModule;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class TestActivity extends AppCompatActivity  implements CameraGestureSensor.Listener,BotMessageReceive{


    private final AIConfiguration config = new AIConfiguration(Constants.BOT_ACCESS_TOKEN,AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System);

    private AIService aiService;
    private AIDataService aiDataService;
    private boolean isListening = false;
    private static final String TAG = TestActivity.class.getSimpleName();
    private XorAIListener aiListener;
    private TextToSpeech tts;

    private Button sendButton;
    private TextView dayText;
    private TextView botText;

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionUtility.checkCameraPermission(this)) {
            LocalOpenCV loader = new LocalOpenCV(this, this);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(PermissionUtility.checkCameraPermission(this)){
            //The third passing in represents a separate click sensor which is not required if you just want the hand motions
            LocalOpenCV loader = new LocalOpenCV(this,this);
        }
        setContentView(R.layout.activity_test);

        //set full screen

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initControls();
        initApiAi();

    }

    private void setControlInitValues(){
        dayText.setText(DayModule.getDay());
        botText.setText("");
        sendButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:
                        aiService.startListening();
                        Toast.makeText(TestActivity.this, "Button is pressed ", Toast.LENGTH_SHORT).show();
                        break;
                    case ACTION_UP:
                        Toast.makeText(TestActivity.this, "Button is release  ", Toast.LENGTH_SHORT).show();
                        aiService.stopListening();
                        break;
                }
                return true;
            }
        });
    }

    private void initControls(){
        dayText = (TextView) findViewById(R.id.dayText);
        botText = (TextView) findViewById(R.id.botText);
        sendButton = (Button) findViewById(R.id.sendButton);
        setControlInitValues();
    }


    private void initApiAi(){
        aiService = AIService.getService(this,config);
        aiListener = new XorAIListener(this);
        aiService.setListener(aiListener);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                    tts.setLanguage(Locale.UK);
            }
        });
    }

    /**
     * Called when an up gesture is triggered
     *
     * @param caller        the CameraGestureSensor object that made the call
     * @param gestureLength the amount of time the gesture took in milliseconds
     */
    @Override
    public void onGestureUp(CameraGestureSensor caller, long gestureLength) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestActivity.this,"Inside onGestureUp",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Called when a down gesture is triggered
     *
     * @param caller        the CameraGestureSensor object that made the call
     * @param gestureLength the amount of time the gesture took in milliseconds
     */
    @Override
    public void onGestureDown(CameraGestureSensor caller, long gestureLength) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestActivity.this,"Inside onGestureDown",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Called when a left gesture is triggered
     *
     * @param caller        the CameraGestureSensor object that made the call
     * @param gestureLength the amount of time the gesture took in milliseconds
     */
    @Override
    public void onGestureLeft(CameraGestureSensor caller, long gestureLength) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestActivity.this,"Inside onGestureLeft",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Called when a right gesture is triggered
     *
     * @param caller        the CameraGestureSensor object that made the call
     * @param gestureLength the amount of time the gesture took in milliseconds
     */
    @Override
    public void onGestureRight(CameraGestureSensor caller, long gestureLength) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestActivity.this,"Inside onGestureRight",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Handle onNewIntent() to inform the fragment manager that the
     * state is not saved.  If you are handling new intents and may be
     * making changes to the fragment state, you want to be sure to call
     * through to the super-class here first.  Otherwise, if your state
     * is saved but the activity is not stopped, you could get an
     * onNewIntent() call which happens before onResume() and trying to
     * perform fragment operations at that point will throw IllegalStateException
     * because the fragment manager thinks the state is still saved.
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setViewState();
    }


    private void setViewState(){
        dayText.setText(DayModule.getDay());
    }

    @Override
    public void onMessage(AIResponse response) {
        parseAndShowResponse(response);
    }

    private void parseAndShowResponse(AIResponse result) {
        final Status status = result.getStatus();
        Log.d(TAG, "Status : " + status.getCode());
        if (status.getCode() == 200) {
            final Result res = result.getResult();
            final String speech = res.getFulfillment().getSpeech();
            Log.d(TAG, "Speech : "+speech);
            botText.setText(speech);
            tts.speak(speech, TextToSpeech.QUEUE_FLUSH,null);
        }else {
            Log.d(TAG, "REsponse is not 200 " + status.toString());
        }
    }
}
