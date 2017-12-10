package xoriant.com.xorhack.activitiy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import edu.washington.cs.touchfreelibrary.sensors.CameraGestureSensor;
import edu.washington.cs.touchfreelibrary.utilities.LocalOpenCV;
import edu.washington.cs.touchfreelibrary.utilities.PermissionUtility;
import xoriant.com.xorhack.R;

public class TestActivity extends AppCompatActivity  implements CameraGestureSensor.Listener{

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
}
