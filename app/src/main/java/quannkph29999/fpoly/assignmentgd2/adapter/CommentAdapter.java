package quannkph29999.fpoly.assignmentgd2.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import quannkph29999.fpoly.assignmentgd2.R;
import quannkph29999.fpoly.assignmentgd2.api.ApiService;
import quannkph29999.fpoly.assignmentgd2.model.Comic;
import quannkph29999.fpoly.assignmentgd2.model.Comment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    public List<Comment> listcomment;
    String id_user;
    String idComic;

    public CommentAdapter(Context context, List<Comment> listcomment, String id_user, String idComic) {
        this.context = context;
        this.listcomment = listcomment != null ? listcomment : new ArrayList<>();
        this.id_user = id_user;
        this.idComic = idComic;
    }

    public void updateDataComment(List<Comment> newData) {
        listcomment.clear();
        listcomment.addAll(newData);
        notifyDataSetChanged();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView edit, delete;
        TextView nameComment, contentComment, date;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.item_editComment);
            delete = itemView.findViewById(R.id.item_deleteComment);
            nameComment = itemView.findViewById(R.id.item_nameComment);
            contentComment = itemView.findViewById(R.id.item_contentComment);
            date = itemView.findViewById(R.id.item_dateComment);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Comment comment = listcomment.get(position);
        holder.nameComment.setText(comment.getUsername());
        holder.contentComment.setText(comment.getContent());
        holder.date.setText(comment.getDate());

        if (id_user.equals(comment.getId_user())) {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.dialog_editcomment, null);
                    builder.setView(view);
                    EditText editContent = view.findViewById(R.id.edt_editContentCmt);
                    Button btnsuaCmt = view.findViewById(R.id.bt_updateComment);
                    editContent.setText(comment.getContent());
                    AlertDialog alertDialog = builder.create();
                    btnsuaCmt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String datemoi;
                            String Contentnew = editContent.getText().toString();
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            datemoi = dateFormat.format(calendar.getTime());
                            Comment newComment = new Comment();
                            newComment.setContent(Contentnew);
                            newComment.setDate(datemoi);

                            ApiService.apiservice.editComment(idComic, comment.get_id(), newComment).enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {
                                    if (response.isSuccessful()) {
                                        listcomment.set(position, response.body());
                                        notifyItemChanged(position);
                                        notifyItemRangeChanged(position, listcomment.size());
                                        alertDialog.dismiss();
                                        Toast.makeText(context, "Đã cập nhật bình luận", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(context, "Lỗi cập nhật bình luận", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {

                                }
                            });
                        }
                    });
                    alertDialog.show();
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Bạn Có Muốn Xóa Bình Luận Này Không?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApiService.apiservice.deleteComment(idComic, comment.get_id()).enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {
                                    if (response.isSuccessful()) {
                                        listcomment.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, listcomment.size());
                                        Toast.makeText(context, "Đã xóa bình luận", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("deletecmt", "idComic: " + idComic + "idcomment" + comment.get_id());
                                        Toast.makeText(context, "Lỗi xóa bình luận", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        } else {
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return listcomment.size();
    }


}
