package com.example.loginapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.loginapp.model.Post;
import com.example.loginapp.service.ApiService;
import com.example.loginapp.service.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
    private ApiService apiService;
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public PostRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<Post>> getPosts() {
        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // Handle error
            }
        });
        return posts;
    }
}
