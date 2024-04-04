package quannkph29999.fpoly.assignmentgd2.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import quannkph29999.fpoly.assignmentgd2.R;
import quannkph29999.fpoly.assignmentgd2.adapter.CommentAdapter;
import quannkph29999.fpoly.assignmentgd2.api.ApiService;
import quannkph29999.fpoly.assignmentgd2.model.Comic;
import quannkph29999.fpoly.assignmentgd2.model.Comment;
import quannkph29999.fpoly.assignmentgd2.model.User;
import quannkph29999.fpoly.assignmentgd2.model.YourComic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietTruyenFragment extends Fragment {
    ImageView coverImage,img_comment;
    TextView namecomic, price, author, publishing, describe,genres;
    Button btn_doctruyen;
    EditText edt_comment;
    RecyclerView recyclerViewComment;

    String comicId, pdf, username, id_user, date, dateyc;
    Comic comic;
    CommentAdapter commentAdapter;
    String money;
    private Handler handler;
    private final int COMMENT_REFRESH_INTERVAL = 2000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comicId = getArguments().getString("comicId", "");
            pdf = getArguments().getString("pdf", "");
            username = getArguments().getString("username", "");
            id_user = getArguments().getString("idUser", "");
            money = getArguments().getString("money", "");
            Log.d("1.4", "onCreate: " + money);
        }
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chi_tiet_truyen, container, false);
        coverImage = view.findViewById(R.id.img_coverimagechitiet);
        namecomic = view.findViewById(R.id.tv_namecomicchitiet);
        price = view.findViewById(R.id.tv_pricechitiet);
        author = view.findViewById(R.id.tv_authorchitiet);
        publishing = view.findViewById(R.id.tv_publishingchitiet);
        describe = view.findViewById(R.id.tv_describechitiet);
        genres = view.findViewById(R.id.tv_genreschitiet);
        btn_doctruyen = view.findViewById(R.id.btn_doctruyenchitiet);
        edt_comment = view.findViewById(R.id.edt_comment);
        img_comment = view.findViewById(R.id.img_commentchitiet);
        recyclerViewComment = view.findViewById(R.id.recyclerViewComment);

        commentAdapter = new CommentAdapter(getContext(), new ArrayList<>(), id_user, comicId);
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewComment.setAdapter(commentAdapter);

        ApiService.apiservice.getListYourComic(id_user).enqueue(new Callback<List<YourComic>>() {
            @Override
            public void onResponse(Call<List<YourComic>> call, Response<List<YourComic>> response) {
                if (response.isSuccessful()) {
                    List<YourComic> yourComicList = response.body();
                    boolean isComicExist = false;
                    if (yourComicList != null) {
                        for (YourComic yourComic : yourComicList) {
                            if (yourComic.getId_comic().equals(comicId)) {
                                isComicExist = true;
                                break;
                            }
                        }
                    }
                    if (isComicExist) {
                        btn_doctruyen.setText("Đọc Truyện");
                        btn_doctruyen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DocTruyenFragment docTruyenFragment = new DocTruyenFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("contentpdf", pdf);
                                docTruyenFragment.setArguments(bundle);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.layout_content, docTruyenFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }

                        });
                    } else if (price.getText().toString() == "0") {
                        btn_doctruyen.setText("Đọc Truyện");
                    } else {
                        btn_doctruyen.setText("Mua Truyện");
                        btn_doctruyen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Integer.parseInt(money) < Integer.parseInt(price.getText().toString())) {
                                    Toast.makeText(getContext(), "Tài khoản của bạn không đủ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    dateyc = dateFormat.format(calendar.getTime());
                                    AddYourComic(id_user, comicId, comic.getNamecomic(), comic.getCover_image(), dateyc, comic.getPrice());
                                    Toast.makeText(getContext(), "Mua truyện thành công", Toast.LENGTH_SHORT).show();
                                    DocTruyenFragment docTruyenFragment = new DocTruyenFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("contentpdf", pdf);
                                    docTruyenFragment.setArguments(bundle);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.layout_content, docTruyenFragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();

                                }
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<List<YourComic>> call, Throwable t) {

            }
        });


        img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                date = dateFormat.format(calendar.getTime());
                AddComment(comicId, id_user, username, edt_comment.getText().toString(), date);
                Toast.makeText(getContext(), "Bình Luận Thành Công", Toast.LENGTH_SHORT).show();
                edt_comment.setText("");
                fetchComic();
            }
        });
        fetchComic();
        ReloadComment();

        return view;
    }


    private void fetchComic() {
        ApiService.apiservice.getComicById(comicId).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                Comic comic = response.body();
                updateUI(comic);
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {

            }
        });
    }

    private void updateUI(Comic comic) {
        this.comic = comic;
        namecomic.setText(comic.getNamecomic());
        price.setText(String.valueOf(comic.getPrice()));
        author.setText(comic.getAuthor());
        publishing.setText(comic.getPublishing_year());
        describe.setText(comic.getDescribe());
        genres.setText(comic.getGenres());
        Picasso.get().load("http://192.168.0.101:3000/images/" + comic.getCover_image())
                .into(coverImage);

    }


    private void fetchComments() {
        ApiService.apiservice.getCommentByIdComic(comicId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> comments = response.body();
                    if (comments != null) {
                        commentAdapter.updateDataComment(comments);
                    } else {
                        Log.d("ChiTietTruyenFragment1", "No comments found for comic ID: " + comicId);
                    }
                } else {
                    // Handle API error (optional)
                    Log.e("ChiTietTruyenFragment2", "Error fetching comments: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("ChiTietTruyenFragment3", "Error fetching comments: " + t.getMessage());
            }
        });
    }


    private void AddComment(String idcomic, String namecomment, String id_user, String content, String date) {
        Comment newComment = new Comment(idcomic, namecomment, id_user, content, date);
        ApiService.apiservice.AddComment(comicId, newComment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    Comment addedComment = response.body();
                    Log.d("ChiTietTruyenFragment", "Comment added successfully: " + addedComment);
                } else {
                    // Xử lý lỗi từ phản hồi nếu cần
                    Log.e("ChiTietTruyenFragment", "Error adding comment: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("ChiTietTruyenFragment", "Error adding comment: " + t.getMessage());
            }
        });
    }

    private void AddYourComic(String id_user, String idComic, String namecomic, String img, String date, String price) {
        YourComic newyourcomic = new YourComic(id_user, idComic, namecomic, img, price, date);
        ApiService.apiservice.AddYourComic(id_user, newyourcomic).enqueue(new Callback<List<YourComic>>() {
            @Override
            public void onResponse(Call<List<YourComic>> call, Response<List<YourComic>> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<List<YourComic>> call, Throwable t) {

            }
        });
    }

    private void ReloadComment() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Gọi fetchComments để cập nhật dữ liệu comment
                fetchComments();
                // Lặp lại sau mỗi COMMENT_REFRESH_INTERVAL milliseconds
                handler.postDelayed(this, COMMENT_REFRESH_INTERVAL);
            }
        }, COMMENT_REFRESH_INTERVAL); // Khởi chạy lần đầu sau COMMENT_REFRESH_INTERVAL milliseconds
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Hủy lặp lại khi Fragment bị hủy
        handler.removeCallbacksAndMessages(null);
    }


}

