package quannkph29999.fpoly.assignmentgd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import quannkph29999.fpoly.assignmentgd2.api.ApiService;
import quannkph29999.fpoly.assignmentgd2.fragment.DanhSachTruyenDaMuaFragment;
import quannkph29999.fpoly.assignmentgd2.fragment.DanhSachTruyenFragment;
import quannkph29999.fpoly.assignmentgd2.fragment.TaiKhoanFragment;
import quannkph29999.fpoly.assignmentgd2.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    String username,id_user;
    String money,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if(intent != null){
            username = intent.getStringExtra("username");
            id_user = intent.getStringExtra("idUser");
            money = intent.getStringExtra("money");
            password = intent.getStringExtra("password");
            Log.d("1.1", "onCreate: "+money);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navi);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        DanhSachTruyenFragment danhSachTruyenFragment = new DanhSachTruyenFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("idUser", id_user);
        bundle.putString("money", String.valueOf(money));
        bundle.putString("password", password);
        danhSachTruyenFragment.setArguments(bundle);
        replaceFragment(danhSachTruyenFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.ds_truyen) {
            DanhSachTruyenFragment danhSachTruyenFragment = new DanhSachTruyenFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("idUser", id_user);
            bundle.putString("money", money);
            bundle.putString("password", password);
            danhSachTruyenFragment.setArguments(bundle);
            replaceFragment(danhSachTruyenFragment);
            drawerLayout.close();

        } else if (id == R.id.ds_muatruyen) {
            DanhSachTruyenDaMuaFragment danhSachTruyenDaMuaFragment = new DanhSachTruyenDaMuaFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username",username);
            bundle.putString("idUser", id_user);
            bundle.putString("money", money);
            bundle.putString("password", password);
            danhSachTruyenDaMuaFragment.setArguments(bundle);
            replaceFragment(danhSachTruyenDaMuaFragment);
            drawerLayout.close();

        } else if (id == R.id.taikhoan) {
            TaiKhoanFragment taiKhoanFragment = new TaiKhoanFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username",username);
            bundle.putString("idUser", id_user);
            bundle.putString("money", money);
            bundle.putString("password", password);
            taiKhoanFragment.setArguments(bundle);
            replaceFragment(taiKhoanFragment);
            drawerLayout.close();

        } else if (id == R.id.logout) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setMessage("Bạn có muốn đăng xuất hay không");
            alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this,SignIn.class);
                    startActivity(intent);
                }
            });
            alertDialog.show();
        }

     return true;

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_content, fragment);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else {
            super.onBackPressed();
        }
    }
}