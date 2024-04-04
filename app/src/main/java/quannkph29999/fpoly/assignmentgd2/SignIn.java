package quannkph29999.fpoly.assignmentgd2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import quannkph29999.fpoly.assignmentgd2.api.ApiService;
import quannkph29999.fpoly.assignmentgd2.model.User;
import quannkph29999.fpoly.assignmentgd2.model.YourComic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignIn extends AppCompatActivity {

    private EditText edtName;
    private EditText edtPassword;
    String id_user;
    String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtName = findViewById(R.id.edt_namedn);
        edtPassword = findViewById(R.id.edt_passworddn);
        TextView tvDktk = findViewById(R.id.tv_dktk);
        Button btnDangNhap = findViewById(R.id.btn_dn);
        tvDktk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignIn.this, "Không để trống tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();

                } else {
                    ApiService.apiservice.getUserWithName(edtName.getText().toString()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User user = response.body();
                                if (user != null) {
                                    id_user = user.get_id();
                                    money = user.getMoney();
                                    Log.d("1.5", "ID: " + money);
                                    checkSignIn();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            // Xử lý lỗi nếu có
                            Log.e("ChiTietTruyenFragment", "Error fetching user: " + t.getMessage());
                        }
                    });



                }
            }

        });

    }

    private void checkSignIn() {
        Log.d("1.6", "ID: " + id_user);
        List<YourComic> emptyList = new ArrayList<>();
        User user = new User("", edtName.getText().toString(), edtPassword.getText().toString(), "", "", "", emptyList);

        ApiService.apiservice.SignIn(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User loggedInUser = response.body();
                    if (loggedInUser != null) {

                        Toast.makeText(SignIn.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                        intent.putExtra("username",edtName.getText().toString() );
                        intent.putExtra("password",edtPassword.getText().toString());
                        intent.putExtra("idUser",id_user );
                        intent.putExtra("money",money );
                        startActivity(intent);
                    } else {

                        Toast.makeText(SignIn.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(SignIn.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(SignIn.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
