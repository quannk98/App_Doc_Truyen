package quannkph29999.fpoly.assignmentgd2.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import quannkph29999.fpoly.assignmentgd2.model.Comic;
import quannkph29999.fpoly.assignmentgd2.model.Comment;
import quannkph29999.fpoly.assignmentgd2.model.User;
import quannkph29999.fpoly.assignmentgd2.model.YourComic;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiservice = new Retrofit.Builder()
            .baseUrl("http://192.168.0.101:3000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("/api/signin")
    Call<User> SignIn(@Body User user);

    @POST("/api/signup")
    Call<User> SignUp(@Body User user);

    //Comic
    @GET("/api/listcomic")
    Call<List<Comic>> getListComic();

    @GET("/api/listcomic/comic/{id}")
    Call<Comic> getComicById(@Path("id") String comicId);


    // User
    @GET("/api/username/{username}")
    Call<User> getUserWithName(@Path("username") String username);

    @GET("/api/user/{id}")
    Call<User> getUserById(@Path("id") String id);

    @PUT("/api/updateMoney/{idUser}/{money}")
    Call<User> updateMoneyUser(@Path("idUser") String idUser, @Path("money") String money);

    @PUT("/api/changepassword/{idUser}")
    Call<User> updatePasswordUser(@Path("idUser") String idUser, @Body User user);


    //YourComic
    @GET("/api/listyourcomic/{id}")
    Call<List<YourComic>> getListYourComic(@Path("id") String id);

    @PUT("/api/yourcomic/{id}")
    Call<List<YourComic>> AddYourComic(@Path("id") String idUser, @Body YourComic yourComic);

    @GET("/api/countComic/{id}")
    Call<Integer> CountComic(@Path("id") String id);


    //Comment
    @GET("/api/getcommnet/{idComic}")
    Call<List<Comment>> getCommentByIdComic(@Path("idComic") String idComic);

    @PUT("/api/addcomment/{id}")
    Call<Comment> AddComment(@Path("id") String idComic, @Body Comment newComment);

    @PUT("/api/editcomment/{id}/{id_comment}")
    Call<Comment> editComment(@Path("id") String idComic, @Path("id_comment") String commentId, @Body Comment editComment);

    @DELETE("/api/deletecomment/{id}/{id_comment}")
    Call<Comment> deleteComment(@Path("id") String idComic, @Path("id_comment") String commentId);

    //Search
    @GET("/api/searchComicBypriceAndGenres/{price}/{genres}")
    Call<List<Comic>> getComicBypriceAndGenres(@Path("price") String price, @Path("genres") String genres);

    @GET("/api/searchNameComic/{nameComic}")
    Call<Comic> getSearchByNameComic(@Path("nameComic") String nameComic);


}
