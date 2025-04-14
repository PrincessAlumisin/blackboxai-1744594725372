package com.example.loginapp.dashboard;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.loginapp.R;
import com.example.loginapp.utils.SharedPrefManager;
import com.example.loginapp.viewmodel.PostViewModel;

public class AdminActivity extends AppCompatActivity {
    private TextView welcomeText;
    private RecyclerView postsRecyclerView;
    private PostViewModel postViewModel;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sharedPrefManager = new SharedPrefManager(this);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        welcomeText = findViewById(R.id.welcome_text);
        postsRecyclerView = findViewById(R.id.posts_recycler_view);

        setupUI();
        setupPostsRecyclerView();
        observePosts();
    }

    private void setupUI() {
        String username = sharedPrefManager.getUsername();
        welcomeText.setText(String.format("Welcome, %s!", username));
    }

    private void setupPostsRecyclerView() {
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsRecyclerView.setAdapter(new PostAdapter());
    }

    private void observePosts() {
        postViewModel.getPosts().observe(this, posts -> {
            PostAdapter adapter = (PostAdapter) postsRecyclerView.getAdapter();
            if (adapter != null) {
                adapter.setPosts(posts);
            }
        });
    }
}
