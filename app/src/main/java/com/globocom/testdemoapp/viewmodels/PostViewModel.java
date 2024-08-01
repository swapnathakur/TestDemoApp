package com.globocom.testdemoapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.globocom.testdemoapp.models.Post;
import com.globocom.testdemoapp.network.ApiService;
import com.globocom.testdemoapp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {
    private ApiService apiService;
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public PostViewModel() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void loadPosts() {
        isLoading.setValue(true);
        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    posts.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                isLoading.setValue(false);
                posts.setValue(null);
            }
        });
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<Post>> getPosts() {
        return posts;
    }
}
