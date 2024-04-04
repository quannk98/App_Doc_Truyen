package quannkph29999.fpoly.assignmentgd2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import quannkph29999.fpoly.assignmentgd2.R;
import quannkph29999.fpoly.assignmentgd2.model.YourComic;

public class YourComicAdapter extends RecyclerView.Adapter<YourComicAdapter.YourComicViewHolder> {
    Context context;
    List<YourComic> comicList;

    public YourComicAdapter(Context context, List<YourComic> comicList) {
        this.context = context;
        this.comicList = comicList;
    }

    public void setData(List<YourComic> newData) {
        comicList.clear();
        comicList.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public YourComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dstruyendamua, parent, false);
        return new YourComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourComicViewHolder holder, int position) {
        YourComic yourComic = comicList.get(position);
        Picasso.get()
                .load("http://192.168.0.101:3000/images/" + yourComic.getImg())
                .placeholder(R.drawable.fpt)  // Reference to your placeholder image
                .error(R.drawable.fptremovebgpreview)
                .into(holder.img_yc);
        holder.name_yc.setText(yourComic.getNamecomic());
        holder.price_yc.setText(yourComic.getPrice());
        holder.date_yc.setText(yourComic.getDate());
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    static class YourComicViewHolder extends RecyclerView.ViewHolder {
        ImageView img_yc;
        TextView name_yc, price_yc, date_yc;

        public YourComicViewHolder(@NonNull View itemView) {
            super(itemView);
            img_yc = itemView.findViewById(R.id.img_yc);
            name_yc = itemView.findViewById(R.id.tv_nameyc);
            price_yc = itemView.findViewById(R.id.tv_priceyc);
            date_yc = itemView.findViewById(R.id.tv_dateyc);
        }
    }
}
