package quannkph29999.fpoly.assignmentgd2.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import quannkph29999.fpoly.assignmentgd2.R;
import quannkph29999.fpoly.assignmentgd2.adapter.TruyenAdapter;
import quannkph29999.fpoly.assignmentgd2.api.ApiService;
import quannkph29999.fpoly.assignmentgd2.model.Comic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTruyenFragment extends Fragment {
    private RecyclerView recyclerView;
    private TruyenAdapter truyenAdapter;
    Spinner spPrice, spGenres;
    Button loc;
    EditText timkiem;
    ImageView alert_comic;
    TextView tvNotificationCount;
    int notificationCount = 0;

    String username, id_user;
    String money;
    Socket socket;
    List<String> newComicNames = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_danh_sach_truyen, container, false);
        if (getArguments() != null) {
            username = getArguments().getString("username", "");
            id_user = getArguments().getString("idUser", "");
            money = getArguments().getString("money", "");
            Log.d("1.2", "onCreateView: " + money);
        } else {
            Log.d("1.2", "lỗi ");
        }
        spPrice = view.findViewById(R.id.sp_searchprice);
        spGenres = view.findViewById(R.id.sp_searchgenres);
        String[] priceOptions = {"Miễn phí", "Trả phí"};
        String[] genreOptions = {"Hành Động", "Trinh Thám", "Anime", "Kinh Dị", "Tình Cảm"};
        ArrayAdapter<String> priceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, priceOptions);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPrice.setAdapter(priceAdapter);

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, genreOptions);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenres.setAdapter(genreAdapter);
        alert_comic = view.findViewById(R.id.img_alertComic);
        tvNotificationCount = view.findViewById(R.id.tv_notification_count);

        loc = view.findViewById(R.id.btn_loc);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedPrice = spPrice.getSelectedItem().toString();
                String selectedGenres = spGenres.getSelectedItem().toString();
                Log.d("tim", "chay " + selectedPrice + "a" + selectedGenres);
                if (selectedPrice.equals("Miễn phí")) {

                    fetchListComicByPriceAndGenres("0", selectedGenres);

                } else if (selectedPrice.equals("Trả phí")) {
                    Log.d("tim", "chay2 ");
                    fetchListComicByPriceAndGenres("1", selectedGenres);
                    Log.d("tim", "chay3 ");
                }

            }
        });
        timkiem = view.findViewById(R.id.edt_timkiem);
        timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("tk", "afterTextChanged: " + s.toString());
                searchComicByKeyword(s.toString());
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        truyenAdapter = new TruyenAdapter(getContext(), new ArrayList<>(), username, id_user, money);
        recyclerView.setAdapter(truyenAdapter);
        fetchListComic();
        try {
            socket = IO.socket("http://192.168.0.101:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        socket.connect();


        socket.on("newComicAdded", args -> {
            String newComicName = args[0].toString();
            newComicNames.add(newComicName);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notificationCount++;
                    updateNotificationCount(notificationCount);
                    showComicAddedDialog(newComicName);
                }
            });
        });


        return view;

    }
    private void showComicAddedDialog(String newComicName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Truyện mới được thêm");
        builder.setMessage("Truyện " + newComicName + " đã được thêm vào ứng dụng!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fetchListComic();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateNotificationCount(int count) {
        if (count > 0) {
            tvNotificationCount.setText(String.valueOf(count));
            tvNotificationCount.setVisibility(View.VISIBLE);
        } else {

            tvNotificationCount.setVisibility(View.GONE);
        }
    }
    private void searchComicByKeyword(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            ApiService.apiservice.getSearchByNameComic(keyword).enqueue(new Callback<Comic>() {
                @Override
                public void onResponse(Call<Comic> call, Response<Comic> response) {
                      if(response.isSuccessful()){
                          Comic newComic = response.body();
                          List<Comic> listcomic = new ArrayList<>();
                          if(newComic != null){
                              listcomic.add(newComic);
                              truyenAdapter.updateData(listcomic);
                          }
                      }
                }

                @Override
                public void onFailure(Call<Comic> call, Throwable t) {

                }
            });

        } else {

            fetchListComic();
        }
    }

    private void fetchListComicByPriceAndGenres(String price, String genres) {
        ApiService.apiservice.getComicBypriceAndGenres(price, genres).enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                List<Comic> comics = response.body();
                Log.d("tim", "chay4 " + comics);

                if (comics != null && !comics.isEmpty()) {
                    truyenAdapter.updateData(comics);
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {

            }
        });

    }

    private void fetchListComic() {
        ApiService.apiservice.getListComic().enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                List<Comic> comics = response.body();

                if (comics != null && !comics.isEmpty()) {
                    truyenAdapter.updateData(comics);
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            socket.disconnect();
            socket.off("newComicAdded");
        }
    }

}