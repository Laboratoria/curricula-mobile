package com.fast.commerce.register;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digits.sdk.android.DigitsAuthButton;
import com.fast.commerce.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRegistrationListener} interface
 * to handle interaction events.
 */
public class RegistrationFragment extends Fragment {

    /** Fabric Credentials */
    public static final String TWITTER_KEY = "CNiYuMzUzYpvNoSAXjeaV4Q4y";
    public static final String
            TWITTER_SECRET = "hAEjfOAXeZ62gsHUKxt4bgYgFWG8ET4QiEMynqKA906gXXPJtV";

    private DigitsAuthButton digitsButton;
    private OnRegistrationListener onRegistrationListener;

    public RegistrationFragment() {}

    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(
                R.layout.fragment_registration,
                container,
                false);
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(
                TWITTER_KEY,
                TWITTER_SECRET);
        Fabric.with(getActivity(), new Twitter(authConfig));
        digitsButton = (DigitsAuthButton) rootView.findViewById(R.id.digitsButton);
        digitsButton.setCallback(new DigitsHelper(getActivity(), onRegistrationListener));
        System.out.println("root view = " + rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegistrationListener) {
            onRegistrationListener = (OnRegistrationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRegistrationListener {
        void onRegistration();
    }
}
