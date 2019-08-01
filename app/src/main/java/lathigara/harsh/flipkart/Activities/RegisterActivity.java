package lathigara.harsh.flipkart.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import lathigara.harsh.flipkart.Fragments.SigninFragment;
import lathigara.harsh.flipkart.Fragments.SignupFragment;
import lathigara.harsh.flipkart.R;

public class RegisterActivity extends AppCompatActivity {
    // signup Login fragments//
    private FrameLayout registerFramLayout;
    public static boolean onResetPassword = true;
    public static boolean setSignUpFragment = false;
    // signup Login fragments//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerFramLayout = findViewById(R.id.register_framlayout);
        if (setSignUpFragment){
            setSignUpFragment  =false;
            setFragment(new SignupFragment());
        }else {
            setFragment(new SigninFragment());
        }
    }
        // signup Login fragments//
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(registerFramLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    // signup Login fragments//
}
