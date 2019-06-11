package barletta.coding.barlettapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import barletta.coding.barlettapp.HomeActivity;
import barletta.coding.barlettapp.LoginAndRegister;
import barletta.coding.barlettapp.R;
import barletta.coding.barlettapp.javaClass.Locale;
import barletta.coding.barlettapp.javaClass.SharedPrefManager;
import barletta.coding.barlettapp.util.MySingleton;

public class UserFragment extends Fragment {

    TextView name, email, accountType;
    Button buttonLogout, deleteAccount, changeLocal, buttonAskCode, buttonConfirm, addCoupon;
    Locale managerLocale;
    EditText insertCode;
    String correctCode = "865943";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (SharedPrefManager.getInstance(getContext()).getTipo() == 2) {
            return inflater.inflate(R.layout.fragment_user_profile_manager, null);
        }

        return inflater.inflate(R.layout.fragment_user_profile, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name = getView().findViewById(R.id.textViewProfileUsername);
        email = getView().findViewById(R.id.textViewProfileEmail);
        accountType = getView().findViewById(R.id.textViewProfileType);
        buttonAskCode = getView().findViewById(R.id.buttonProfileAskCode);
        insertCode = getView().findViewById(R.id.editTextInsertCode);
        buttonConfirm = getView().findViewById(R.id.buttonConfirmCode);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertCode.getText().toString().trim().equals(correctCode)) {
                    confirmCodeSend();
                }

            }
        });


        buttonAskCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMailWithCode();
            }
        });

        //Conferma elimina account

        name.setText(getString(R.string.usernameText) + " : " + SharedPrefManager.getInstance(getActivity()).getUsername());
        email.setText(getString(R.string.mailText) + " : " + SharedPrefManager.getInstance(getActivity()).getUserEmail());

        if (SharedPrefManager.getInstance(getActivity()).getTipo() == 2) {
            accountType.setText(getString(R.string.tipeAccountString) + " : " + getString(R.string.managerType));
            startManagerLayout();
        } else {
            accountType.setText(getString(R.string.tipeAccountString) + " : " + getString(R.string.userType));
        }

        buttonLogout = getView().findViewById(R.id.buttonProfileLogout);
        deleteAccount = getView().findViewById(R.id.buttonDeleteAccount);
        changeLocal = getView().findViewById(R.id.buttonChangeYourLocale);


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
        if (SharedPrefManager.getInstance(getActivity()).getTipo() == 2) {

            addCoupon = getView().findViewById(R.id.buttonAddCoupon);

            addCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment frag = new AddCouponFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentView, frag)
                            .commit();
                }
            });

            changeLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findManagerlocal();
                }
            });
        }


    }

    public void findManagerlocal() {

        int UserId = SharedPrefManager.getInstance(getActivity()).getId();
        Iterator<Locale> iterator = CategoryListActivity.lista.iterator();

        while (iterator.hasNext()) {
            Locale temp = iterator.next();
            if (temp.getIdGestore() == UserId) {
                managerLocale = temp;
            }
        }

        Fragment fragment = new OpenLocalFragment();
        OpenLocalFragment.setIdLocale(managerLocale);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragmentView, fragment)
                .commit();

    }

    public void sendMailWithCode() {

        final String email = SharedPrefManager.getInstance(getActivity()).getUserEmail().trim();

        String urlCode = "http://barlettacoding.altervista.org/sendMail.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCode, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("to", email);
                return params;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    public void confirmCodeSend() {

        final String userID = Integer.toString(SharedPrefManager.getInstance(getActivity()).getId());
        final String LocalID = "5";

        String urlChangeManager = "http://barlettacoding.altervista.org/UpdateManagerLocal.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlChangeManager, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getContext(), getString(R.string.managerSucces), Toast.LENGTH_LONG).show();
                SharedPrefManager.getInstance(getActivity()).logout();
                startActivity(new Intent(getActivity(), LoginAndRegister.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IDutente", userID);
                params.put("ID", LocalID);
                return params;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    public void startManagerLayout() {

        TextView becomeMan = getView().findViewById(R.id.textViewBecomeManager);
        becomeMan.setText("Manager Option");

        buttonAskCode.setVisibility(View.GONE);
        buttonConfirm.setVisibility(View.GONE);

        insertCode.setVisibility(View.GONE);

        TextView explanation = getView().findViewById(R.id.textViewExplanation);
        explanation.setVisibility(View.GONE);

    }

}




