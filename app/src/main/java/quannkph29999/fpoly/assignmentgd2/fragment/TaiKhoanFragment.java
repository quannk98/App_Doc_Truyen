package quannkph29999.fpoly.assignmentgd2.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quannkph29999.fpoly.assignmentgd2.R;
import quannkph29999.fpoly.assignmentgd2.SignIn;
import quannkph29999.fpoly.assignmentgd2.api.ApiService;
import quannkph29999.fpoly.assignmentgd2.model.User;
import quannkph29999.fpoly.assignmentgd2.model.YourComic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TaiKhoanFragment extends Fragment {
    TextView nameuser, fullnameuser, email, money, sltdm;
    Button btnNtvv, btnDx, btnDlmk;
    String username, id_user, password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString("username", "");
            id_user = getArguments().getString("idUser", "");
            password = getArguments().getString("password", "");
            Log.d("mk", "onCreate: " + password);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_tai_khoan, container, false);
        nameuser = view.findViewById(R.id.tv_nameuser);
        fullnameuser = view.findViewById(R.id.tv_fullnameuser);
        email = view.findViewById(R.id.tv_emailuser);
        money = view.findViewById(R.id.tv_moneyuser);
        btnNtvv = view.findViewById(R.id.btn_ntvv);
        sltdm = view.findViewById(R.id.tv_sltdmuser);
        btnDx = view.findViewById(R.id.btn_dangxuat);
        btnDlmk = view.findViewById(R.id.btn_dlmk);
        CountComic();
        fetchDataUser();
        btnNtvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_naptien, null);
                builder.setView(view1);
                EditText updatemoney = view1.findViewById(R.id.edt_updateMoney);
                Button btnmoney = view1.findViewById(R.id.btn_updateMoney);
                AlertDialog alertDialog = builder.create();
                btnmoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (updatemoney.getText().toString().equals(null)) {
                            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                        } else {
                            ApiService.apiservice.updateMoneyUser(id_user, updatemoney.getText().toString()).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                                        fetchDataUser();
                                        alertDialog.dismiss();
                                    } else {
                                        Toast.makeText(getContext(), "Nạp tiền thất bại", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }

                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(getContext(), "Nạp tiền lỗi", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            });
                        }

                    }
                });
                alertDialog.show();


            }
        });
        btnDlmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_restartpassword, null);
                builder.setView(view1);
                EditText oldpassword = view1.findViewById(R.id.edt_oldpassword);
                EditText newpassword = view1.findViewById(R.id.edt_newpassword);
                Button btn_restartpassword = view1.findViewById(R.id.btn_restartpassword);
                AlertDialog alertDialog = builder.create();
                btn_restartpassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!oldpassword.getText().toString().equals(password)) {
                            Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else if (oldpassword.getText().toString().equals(newpassword.getText().toString())) {
                            Toast.makeText(getContext(), "Mật khẩu cũ và mật khẩu mới không được trùng nhau", Toast.LENGTH_SHORT).show();
                        } else if (oldpassword.getText().toString().equals(null) || newpassword.getText().toString().equals(null)) {
                            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                        } else {
                            User newuser = new User();
                            newuser.setPassword(newpassword.getText().toString());
                            ApiService.apiservice.updatePasswordUser(id_user, newuser).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    } else {
                                        Toast.makeText(getContext(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }

                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(getContext(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    }
                });
                alertDialog.show();

            }
        });
        btnDx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });


        return view;
    }

    private void fetchDataUser() {
        ApiService.apiservice.getUserById(id_user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User mUser = response.body();
                    nameuser.setText(mUser.getUsername());
                    fullnameuser.setText(mUser.getFullname());
                    email.setText(mUser.getEmail());
                    money.setText(mUser.getMoney());


                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void CountComic() {
        Log.d("tk1", "onResponse: " + id_user);
        ApiService.apiservice.CountComic(id_user).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("tk2", "onResponse: ");
                if (response.isSuccessful()) {
                    Integer comicCount = response.body();
                    Log.d("tk", "onResponse: " + comicCount);
                    if (comicCount != null) {
                        sltdm.setText(String.valueOf(comicCount));
                    } else {
                        sltdm.setText("0");
                    }
                } else {
                    sltdm.setText("0");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                sltdm.setText("0");
            }
        });
    }

}