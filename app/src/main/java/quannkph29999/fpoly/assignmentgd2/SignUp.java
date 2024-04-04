package quannkph29999.fpoly.assignmentgd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quannkph29999.fpoly.assignmentgd2.api.ApiService;
import quannkph29999.fpoly.assignmentgd2.model.User;
import quannkph29999.fpoly.assignmentgd2.model.YourComic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    private EditText edtNamedk;
    private EditText edtPassworddk;
    private EditText edtEmaildk;
    private EditText edtFullnamedk;
    private Button btnDk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtNamedk = findViewById(R.id.edt_namedk);
        edtPassworddk = findViewById(R.id.edt_passworddk);
        edtEmaildk = findViewById(R.id.edt_emaildk);
        edtFullnamedk = findViewById(R.id.edt_fullnamedk);
        btnDk = findViewById(R.id.btn_dk);
        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNamedk.getText().toString().trim();
                String password = edtPassworddk.getText().toString().trim();
                String email = edtEmaildk.getText().toString().trim();
                String fullname = edtFullnamedk.getText().toString().trim();
                if (name.isEmpty() || password.isEmpty() || email.isEmpty() || fullname.isEmpty()) {
                    Toast.makeText(SignUp.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                   
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignUp.this, "Sai định dạng email", Toast.LENGTH_SHORT).show();
                } else if (!password.matches("^^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,12}$")) {
                    Toast.makeText(SignUp.this, "Sai định mật khẩu, mật khẩu có độ dài 6 - 12 và có số và chữ cái", Toast.LENGTH_SHORT).show();
                    
                } else {
                    PostSignUp();
                }
            }
        });

    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void PostSignUp() {
        List<YourComic> emptyList = new ArrayList<>();
        User user = new User("", edtNamedk.getText().toString(), edtPassworddk.getText().toString()
                , "", edtEmaildk.getText().toString(), edtFullnamedk.getText().toString(), emptyList);
        ApiService.apiservice.SignUp(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User loggedInUser = response.body();
                    if (loggedInUser != null) {
                        Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this, SignIn.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(SignUp.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(SignUp.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUp.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}