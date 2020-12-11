package com.jdots.ignoupro.global;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jdots.ignoupro.Dependencies;
import com.jdots.ignoupro.R;
import com.jdots.ignoupro.global.presenter.GlobalPresenter;
import com.jdots.ignoupro.global.view.GlobalDisplayer;
import com.jdots.ignoupro.navigation.AndroidNavigator;

/**
 * Created for chatting on 08/08/16.
 */

public class GlobalFragment extends Fragment {

    private GlobalPresenter presenter;
    private AndroidNavigator navigator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_global, container, false);
        GlobalDisplayer globalDisplayer = (GlobalDisplayer) rootView.findViewById(R.id.globalView);
        getActivity().setTitle(R.string.global_toolbar_title);

        navigator = new AndroidNavigator(getActivity());
        presenter = new GlobalPresenter(
                Dependencies.INSTANCE.getLoginService(),
                Dependencies.INSTANCE.getGlobalService(),
                globalDisplayer,
                Dependencies.INSTANCE.getUserService(),
                navigator
        );

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.startPresenting();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopPresenting();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
