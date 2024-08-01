package com.globocom.testdemoapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.globocom.testdemoapp.adapter.PostAdapter;
import com.globocom.testdemoapp.databinding.ActivityPostBinding;
import com.globocom.testdemoapp.models.Post;
import com.globocom.testdemoapp.viewmodels.PostViewModel;

import java.util.List;

public class PostActivity extends AppCompatActivity {
    private PostViewModel postViewModel;
    private ActivityPostBinding binding;
    private PostAdapter postAdapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        setUpProgressDialog();
        postViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    dialog.show();
                } else {
                    dialog.dismiss();
                }
            }
        });
        postViewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                setUpAdapter(posts);
            }
        });
        postViewModel.loadPosts();
    }

    /**
     * Initializes adapter and set to recyclerview by getting posts from viewmodel
     */
    private void setUpAdapter(List<Post> posts) {
        postAdapter = new PostAdapter(posts);
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPosts.setAdapter(postAdapter);
    }

    /**
     * Initializes progress dialog
     */
    private void setUpProgressDialog() {
        dialog = new ProgressDialog(PostActivity.this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
    }
}