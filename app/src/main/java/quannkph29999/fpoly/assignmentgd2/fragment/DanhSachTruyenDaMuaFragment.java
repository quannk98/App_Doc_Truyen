package quannkph29999.fpoly.assignmentgd2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import quannkph29999.fpoly.assignmentgd2.R;
import quannkph29999.fpoly.assignmentgd2.adapter.YourComicAdapter;
import quannkph29999.fpoly.assignmentgd2.api.ApiService;
import quannkph29999.fpoly.assignmentgd2.model.YourComic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DanhSachTruyenDaMuaFragment extends Fragment {
    YourComicAdapter yourComicAdapter;
    RecyclerView recyclerView;
    String username,id_user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            username = getArguments().getString("username","");
            id_user = getArguments().getString("idUser","");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_truyen_da_mua, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_tdm);
        yourComicAdapter = new YourComicAdapter(getContext(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(yourComicAdapter);
        fetchListYC();
        return view;
    }
    private void fetchListYC(){
        ApiService.apiservice.getListYourComic(id_user).enqueue(new Callback<List<YourComic>>() {
            @Override
            public void onResponse(Call<List<YourComic>> call, Response<List<YourComic>> response) {
                if (response.isSuccessful()){
                    List<YourComic> yourComics = response.body();
                    yourComicAdapter.setData(yourComics);
                }
            }

            @Override
            public void onFailure(Call<List<YourComic>> call, Throwable t) {

            }
        });
    }

}