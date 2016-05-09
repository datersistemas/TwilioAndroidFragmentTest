package co.inamoto.sample.twilioandroidfragmenttest;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.twilio.common.TwilioAccessManager;
import com.twilio.common.TwilioAccessManagerFactory;
import com.twilio.common.TwilioAccessManagerListener;
import com.twilio.conversations.CameraCapturer;
import com.twilio.conversations.ConversationsClient;
import com.twilio.conversations.ConversationsClientListener;
import com.twilio.conversations.IncomingInvite;
import com.twilio.conversations.TwilioConversations;
import com.twilio.conversations.TwilioConversationsException;

/**
 * Created by willturnage on 5/9/16.
 */
public class TwilioFragment extends Fragment {


    private String token = "<REPLACE_WITH_VALID_TOKEN>";
    private TwilioAccessManager accessManager;
    private CameraCapturer cameraCapturer;
    private ConversationsClient conversationsClient;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_twilio, container, false);

        Button removeButton = (Button) v.findViewById(R.id.removeTwilioFragmentButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity a = (MainActivity) getActivity();
                a.removeFragment();
            }
        });

        return v;

    }

    public void onResume() {
        super.onResume();
        setupTwilio();
    }

    @Override
    public void onPause() {
        super.onPause();
        shutdownTwilio();
    }



    // setup and initialize Twilio

    private void setupTwilio() {

        if (!TwilioConversations.isInitialized()) {

            TwilioConversations.setLogLevel(TwilioConversations.LogLevel.DEBUG);

            TwilioConversations.initialize(getActivity().getApplicationContext(), new TwilioConversations.InitListener() {

                @Override
                public void onInitialized() {
                    accessManager = TwilioAccessManagerFactory.createAccessManager(getActivity().getApplicationContext(), token, accessManagerListener());
                    conversationsClient = TwilioConversations.createConversationsClient(accessManager, conversationsClientListener());
                    conversationsClient.listen();
                }

                @Override
                public void onError(Exception e) {
                }

            });
        }
    }




    // clean up and shutdown Twilio

    private void shutdownTwilio() {

        if (conversationsClient != null) {
            if (conversationsClient.isListening()) {
                conversationsClient.unlisten();
            }
            conversationsClient = null;
        }

        if (accessManager != null) {
            accessManager.dispose();
            accessManager = null;
        }

        // CRASH: happens now when you call TwilioConversations.destroy()
        TwilioConversations.destroy();

    }




    // empty listener

    private TwilioAccessManagerListener accessManagerListener() {

        return new TwilioAccessManagerListener() {

            @Override
            public void onTokenExpired(TwilioAccessManager twilioAccessManager) {
            }

            @Override
            public void onTokenUpdated(TwilioAccessManager twilioAccessManager) {
            }

            @Override
            public void onError(TwilioAccessManager twilioAccessManager, String s) {
            }

        };
    }


    // empty listener

    private ConversationsClientListener conversationsClientListener() {
        return new ConversationsClientListener() {
            @Override
            public void onStartListeningForInvites(ConversationsClient conversationsClient) {
            }

            @Override
            public void onStopListeningForInvites(ConversationsClient conversationsClient) {
            }

            @Override
            public void onFailedToStartListening(ConversationsClient conversationsClient, TwilioConversationsException e) {
            }

            @Override
            public void onIncomingInvite(ConversationsClient conversationsClient, IncomingInvite incomingInvite) {
            }

            @Override
            public void onIncomingInviteCancelled(ConversationsClient conversationsClient, IncomingInvite incomingInvite) {
            }

        };
    }


}
