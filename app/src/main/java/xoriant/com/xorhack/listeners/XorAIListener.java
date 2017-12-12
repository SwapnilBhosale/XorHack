package xoriant.com.xorhack.listeners;

import ai.api.AIListener;

/**
 * Created by C5265858 on 12/12/2017.
 */

public class XorAIListener implements AIListener {

    private BotMessageReceive m;

    public XorAIListener(BotMessageReceive m){
        this.m = m;
    }
    /**
     * Event fires when entire process finished successfully, and returns result object
     *
     * @param result the result object, contains server answer
     */
    @Override
    public void onResult(ai.api.model.AIResponse result) {
        m.onMessage(result);
    }

    /**
     * Event fires if something going wrong while recognition or access to the AI server
     *
     * @param error the error description object
     */
    @Override
    public void onError(ai.api.model.AIError error) {

    }

    /**
     * Event fires every time sound level changed. Use it to create visual feedback. There is no guarantee that this method will
     * be called.
     *
     * @param level the new RMS dB value
     */
    @Override
    public void onAudioLevel(float level) {

    }

    /**
     * Event fires when recognition engine start listening
     */
    @Override
    public void onListeningStarted() {

    }

    /**
     * Event fires when recognition engine cancel listening
     */
    @Override
    public void onListeningCanceled() {

    }

    /**
     * Event fires when recognition engine finish listening
     */
    @Override
    public void onListeningFinished() {

    }
}
