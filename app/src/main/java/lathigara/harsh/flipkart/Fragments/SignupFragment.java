package lathigara.harsh.flipkart.Fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import lathigara.harsh.flipkart.Activities.MainActivity;
import lathigara.harsh.flipkart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {
    private TextView alreadyHaveAnAccount;
    private FrameLayout parentframeLayout;
    private EditText edtEmail,edtName,edtPass,edtConfirm;
    private ImageButton btnClose;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseFirestore firebaseFirestore;



    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_signup, container, false);
        alreadyHaveAnAccount = view.findViewById(R.id.txtSignIn);
        parentframeLayout = getActivity().findViewById(R.id.register_framlayout);

        edtEmail = view.findViewById(R.id.edtNewEmail);
        edtName = view.findViewById(R.id.edtName);
        edtPass = view.findViewById(R.id.edtNewPassword);
        edtConfirm = view.findViewById(R.id.edtConfirmPass);
        btnClose = view.findViewById(R.id.btnSignUpClose);
        btnSignUp = view.findViewById(R.id.btnSignInLog);
        //progressBar = view.findViewById(R.id.progressBarSignUp);
      //  progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SigninFragment());
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();

            }
        });
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
        edtName.addTextChangedListener(new TextWatcher() {
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
        edtConfirm.addTextChangedListener(new TextWatcher() {
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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEmailAndPass();



            }
        });
    }
    private void checkInputs(){
        if (!TextUtils.isEmpty(edtEmail.getText())){
            if (!TextUtils.isEmpty(edtName.getText())){
                if (!TextUtils.isEmpty(edtPass.getText()) && edtPass.length() >= 8){
                    if (!TextUtils.isEmpty(edtConfirm.getText())){
                        btnSignUp.setEnabled(true);
                        btnSignUp.setTextColor(getResources().getColor(R.color.colorPrimary));

                    }else{
                        btnSignUp.setEnabled(false);
                        btnSignUp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                    }
                }else {
                    btnSignUp.setEnabled(false);
                    btnSignUp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                }

            }else{
                btnSignUp.setEnabled(false);
                btnSignUp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            }

        }else {
            btnSignUp.setEnabled(false);
            btnSignUp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }
    }
    private void CheckEmailAndPass(){
        Drawable cutomError = getResources().getDrawable(R.drawable.ic_arrow_);
        cutomError.setBounds(0,0,cutomError.getIntrinsicWidth(),cutomError.getIntrinsicHeight());
        if (edtEmail.getText().toString().matches(emailPattern)){
            if (edtPass.getText().toString().equals(edtConfirm.getText().toString())){
               // progressBar.setVisibility(View.VISIBLE);
                btnSignUp.setEnabled(false);
                btnSignUp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Map<Object,String> userData = new HashMap<>();
                            userData.put("name",edtName.getText().toString());
                            // userData.put("name",edtName.getText().toString());
                            firebaseFirestore.collection("Users").add(userData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        mainIntent();

                                    }else{
                                       // progressBar.setVisibility(View.INVISIBLE);
                                        btnSignUp.setEnabled(true);
                                        btnSignUp.setTextColor(getResources().getColor(R.color.colorPrimary));
                                        String error =task.getException().getMessage();
                                        Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                    }

                                }
                            });



                        }else{
                           // progressBar.setVisibility(View.INVISIBLE);
                            btnSignUp.setEnabled(true);
                            btnSignUp.setTextColor(getResources().getColor(R.color.colorPrimary));
                            String error =task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }else{
                edtConfirm.setError("Please Match Your Password",cutomError);

            }

        }else{
            edtEmail.setError("Invalid Email",cutomError);

        }

    }

    private void mainIntent() {
        Intent mainI =new Intent(getActivity(), MainActivity.class);
        startActivity(mainI);
        getActivity().finish();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
