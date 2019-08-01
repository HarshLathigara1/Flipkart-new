package lathigara.harsh.flipkart.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import lathigara.harsh.flipkart.Activities.MainActivity;
import lathigara.harsh.flipkart.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {
    private TextView donthaveAccount;
    private FrameLayout parentFrameLayout;
    private TextView forgotPassword;

    private EditText edtEmail,edtPass;
    private ImageButton imgCloseButton;
    private Button btnSignIn;
    private FirebaseAuth mAuth;
    private String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signin, container, false);
        donthaveAccount = view.findViewById(R.id.txtCreateNewAccount);
        parentFrameLayout = getActivity().findViewById(R.id.register_framlayout);
        edtEmail = view.findViewById(R.id.edtNewEmail);
        edtPass = view.findViewById(R.id.edtNewPassword);
        imgCloseButton = view.findViewById(R.id.btnCloseSignIn);
        btnSignIn = view.findViewById(R.id.btnSignInLog);
        mAuth = FirebaseAuth.getInstance();
        forgotPassword = view.findViewById(R.id.txtForegetPassword);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        donthaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignupFragment());
            }
        });
        imgCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });
       /* forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPassword = true;
                setFragment(new ResetPasswordFragment());
            }
        });*/
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPass();


            }
        });
    }

    private void mainIntent() {
        Intent mainI =new Intent(getActivity(), MainActivity.class);
        startActivity(mainI);
    }
    private void checkInputs(){
        if (!TextUtils.isEmpty(edtEmail.getText())){
            if (!TextUtils.isEmpty(edtPass.getText())){
                btnSignIn.setEnabled(true);
                btnSignIn.setTextColor(getResources().getColor(R.color.colorPrimary));


            }else{
                btnSignIn.setEnabled(false);
                btnSignIn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            }

        }else{
            btnSignIn.setEnabled(false);
            btnSignIn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }

    }
    private void checkEmailAndPass(){
        if (edtEmail.getText().toString().matches(emailPattern)){
            if (edtPass.length()>=8){
               // progressBar.setVisibility(View.VISIBLE);
                btnSignIn.setEnabled(false);
                btnSignIn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mainIntent();

                        }else{
                            //progressBar.setVisibility(View.INVISIBLE);
                            btnSignIn.setEnabled(true);
                            btnSignIn.setTextColor(getResources().getColor(R.color.colorPrimary));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }else {
                Toast.makeText(getActivity(),"Incorrect Email Or Password",Toast.LENGTH_LONG).show();

            }

        }else{
            Toast.makeText(getActivity(),"Incorrect Email Or Password",Toast.LENGTH_LONG).show();

        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
