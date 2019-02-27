package barletta.coding.barlettapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserFragment extends Fragment {

    TextView name, email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_user_profile, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = getView().findViewById(R.id.textViewProfileUsername);
        email = getView().findViewById(R.id.textViewProfileEmail);

        name.setText(SharedPrefManager.getInstance(getActivity()).getUsername());
        email.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());
    }
}
