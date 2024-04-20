package com.example.btl_api.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.btl_api.model.NewsHeadlines;
import com.example.btl_api.databinding.ActivityDetailsBinding;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    NewsHeadlines headlines;

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");
        initContent();
        setListener();
    }

    private void setListener() {
//        binding.backBtn.setOnClickListener(v -> {
//            BackStac
//            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        });
    }

    private void initContent() {
        binding.textDetailTitle.setText(headlines.getTitle());
        binding.textDetailAuthor.setText(headlines.getAuthor());
        binding.textDetailTime.setText(headlines.getPublishedAt());
        binding.textDetailDetail.setText(headlines.getDescription());
        binding.textDetailContent.setText(headlines.getContent());
        Picasso.get().load(headlines.getUrlToImage()).into(binding.imgDetailNews);
    }
}