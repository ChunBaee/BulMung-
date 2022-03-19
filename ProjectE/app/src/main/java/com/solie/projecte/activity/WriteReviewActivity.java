package com.solie.projecte.activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.solie.projecte.AppHelper;
import com.solie.projecte.R;
import com.solie.projecte.data.ReviewParcelable;
import com.solie.projecte.data.WriteResult;

import java.util.HashMap;
import java.util.Map;

public class WriteReviewActivity extends AppCompatActivity {


    int pageId;
    int id;

    TextView movieName;
    RatingBar ratingBar;
    ImageView movieGrade;
    EditText movieContent;
    Button save, cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review);

        movieName = findViewById(R.id.writeReviewMovieName);
        movieGrade = findViewById(R.id.writeReviewMovieGrade);
        ratingBar = findViewById(R.id.writeReviewMovieRating);
        movieContent = findViewById(R.id.writeReviewContent);
        save = findViewById(R.id.writeReviewSave);
        cancel = findViewById(R.id.writeReviewCancel);

        if(getIntent() != null) {
            String name = getIntent().getExtras().getString("movieName");
            movieName.setText(name);
            id = getIntent().getExtras().getInt("movieNumber");
            int grade = getIntent().getExtras().getInt("movieGrade");
            pageId = getIntent().getExtras().getInt("pageNumber");
            switch (grade) {
                case 12:
                    movieGrade.setImageResource(R.drawable.ic_12);
                    break;
                case 15:
                    movieGrade.setImageResource(R.drawable.ic_15);
                    break;
                case 19:
                    movieGrade.setImageResource(R.drawable.ic_19);
                    break;
            }
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestReviewUpdate();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"한줄평 작성을 취소하셨습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void RequestReviewUpdate() {
        String url = "Http://" + AppHelper.host + ":" + AppHelper.port + "/movie/createComment";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        WriteUpdateResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",Integer.toString(id));
                params.put("writer","작성자");
                params.put("rating",Float.toString(ratingBar.getRating()));
                params.put("time","작성 시간");
                params.put("contents",movieContent.getText().toString());
                return params;
            }
        };
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }
    public void WriteUpdateResponse(String response) {
        Gson gson = new Gson();
        WriteResult writeResult = gson.fromJson(response,WriteResult.class);
        if(writeResult != null) {
            if(writeResult.code == 200) {
                Intent intent = new Intent();
                intent.putExtra("contents",movieContent.getText().toString());
                intent.putExtra("writer","작성자");
                intent.putExtra("rating",ratingBar.getRating());
                intent.putExtra("time","작성 시간");
                setResult(RESULT_OK,intent);
                Toast.makeText(getApplicationContext(),"한줄평 작성에 성공했습니다.",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(),"한줄평 작성에 실패했습니다.",Toast.LENGTH_SHORT).show();

                finish();
            }
        }
    }
}
