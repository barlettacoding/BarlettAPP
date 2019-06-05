package barletta.coding.barlettapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import barletta.coding.barlettapp.HomeActivity;
import barletta.coding.barlettapp.LoginAndRegister;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.SharedPrefManager;

public class UserFragment extends Fragment {

    TextView name, email;
    Button buttonLogout, deleteAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_profile, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name = getView().findViewById(R.id.textViewProfileUsername);
        email = getView().findViewById(R.id.textViewProfileEmail);


        //Conferma elimina account

        name.setText(getString(R.string.usernameText) + " : " + SharedPrefManager.getInstance(getActivity()).getUsername());
        email.setText(getString(R.string.mailText) + " : " + SharedPrefManager.getInstance(getActivity()).getUserEmail());

        buttonLogout = getView().findViewById(R.id.buttonProfileLogout);
        deleteAccount = getView().findViewById(R.id.buttonDeleteAccount);

        buttonLogout.setText(getString(R.string.buttonProfileLogout));
        deleteAccount.setText(getString(R.string.buttonDeleteAccount));

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getActivity()).logout();
                startActivity(new Intent(getActivity(), LoginAndRegister.class));
            }
        });


        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((HomeActivity) getActivity()).createPopup(getView());


            }
        });


    }

}



