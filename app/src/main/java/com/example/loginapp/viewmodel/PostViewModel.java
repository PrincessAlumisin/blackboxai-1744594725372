package com.example.loginapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.loginapp.data.PostRepository;
import com.example.loginapp.model.Post;
import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private PostRepository postRepository;
    private LiveData<List<Post>> posts;

    public PostViewModel(Application application) {
        super(application);
        postRepository = new PostRepository();
        posts = postRepository.getPosts();
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }
}
