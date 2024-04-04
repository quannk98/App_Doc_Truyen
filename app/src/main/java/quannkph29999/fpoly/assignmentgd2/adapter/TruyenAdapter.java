package quannkph29999.fpoly.assignmentgd2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import quannkph29999.fpoly.assignmentgd2.R;
import quannkph29999.fpoly.assignmentgd2.fragment.ChiTietTruyenFragment;
import quannkph29999.fpoly.assignmentgd2.model.Comic;

public class TruyenAdapter extends RecyclerView.Adapter<TruyenAdapter.TruyenViewHolder> {
    private Context context;
    public List<Comic> listcomic;
    String username;
    String id_user;
    String money;

    public TruyenAdapter(Context context, List<Comic> listcomic,String username,String id_user,String money) {
        this.context = context;
        this.listcomic = listcomic != null ? listcomic : new ArrayList<>();
        this.username = username;
        this.id_user = id_user;
        this.money = money;
    }

    public void updateData(List<Comic> newData) {
        listcomic.clear();
        listcomic.addAll(newData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TruyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_truyen, parent, false);
        return new TruyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenViewHolder holder, int position) {
        Comic truyen = listcomic.get(position);
        Picasso.get()
                .load("http://192.168.0.101:3000/images/" + truyen.getCover_image())
                .placeholder(R.drawable.fpt)  // Reference to your placeholder image
                .error(R.drawable.fptremovebgpreview)
                .into(holder.imageViewTruyen);
        holder.textViewTenTruyen.setText(truyen.getNamecomic());
        holder.textViewGiaTruyen.setText(String.valueOf(truyen.getPrice()));
        holder.cardViewcomic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTietTruyenFragment chiTietTruyenFragment = new ChiTietTruyenFragment();
                Bundle bundle = new Bundle();
                bundle.putString("comicId", truyen.get_id());
                bundle.putString("pdf", truyen.getComic_content());
                bundle.putString("username",username);
                bundle.putString("idUser",id_user);
                bundle.putString("money", String.valueOf(money));
                Log.d("1.3", "onClick: "+money);
                chiTietTruyenFragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_content, chiTietTruyenFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return listcomic.size();
    }

    static class TruyenViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewTruyen;
        TextView textViewTenTruyen;
        TextView textViewGiaTruyen;
        CardView cardViewcomic;

        public TruyenViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewTruyen = itemView.findViewById(R.id.imageViewTruyen);
            textViewTenTruyen = itemView.findViewById(R.id.textViewTenTruyen);
            textViewGiaTruyen = itemView.findViewById(R.id.textViewGiaTruyen);
            cardViewcomic = itemView.findViewById(R.id.cardViewComic);
        }
    }
}
