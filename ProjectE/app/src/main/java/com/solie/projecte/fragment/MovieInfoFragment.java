package com.solie.projecte.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.solie.projecte.AppHelper;
import com.solie.projecte.NetworkStatus;
import com.solie.projecte.R;
import com.solie.projecte.activity.GalleryImage;
import com.solie.projecte.activity.MainActivity;
import com.solie.projecte.activity.SeeAllReviewActivity;
import com.solie.projecte.activity.WriteReviewActivity;
import com.solie.projecte.adapter.RecyclerAdapter;
import com.solie.projecte.adapter.ReviewAdapter;
import com.solie.projecte.data.CommentList;
import com.solie.projecte.data.CommentListResult;
import com.solie.projecte.data.LikeDisLikeData;
import com.solie.projecte.data.LikeDisLikeResult;
import com.solie.projecte.data.MoiveListResult;
import com.solie.projecte.data.MovieList;
import com.solie.projecte.data.ReviewParcelable;
import com.solie.projecte.data.ThumbnailItem;
import com.solie.projecte.database.DBHelper;
import com.solie.projecte.database.DatabaseContract;

import java.util.ArrayList;

import static com.solie.projecte.activity.MainActivity.database;

public class MovieInfoFragment extends Fragment {


    public static Toolbar toolbar;
    public static TextView toolbarTitle;


    ImageView poster, thumbUp, thumbDown, grade;
    TextView name, date, genre, goodCountView, badCountView, bookRank, rate, viewerCount, synopsis, director, actor;
    Button writeReview1, writeReview2, seeReviews;

    RatingBar ratingBar;

    ScrollView scrollView;
    ListView listView;
    ReviewAdapter adapter;
    ArrayList<ReviewParcelable> reviews = new ArrayList<>();
    LikeDisLikeData likeDisLikeData = new LikeDisLikeData();

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    String[] videos;

    String movieName;
    int roof, movieNumber, goodCount, badCount, intgrade, totcount;
    int WRITE_CODE = 101;
    int LIST_CODE = 102;

    int photoNumber;
    int videoNumber;

    public int status;

    public static MovieInfoFragment newInstance(int roof) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putInt("bundle", roof);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            roof = getArguments().getInt("bundle");
            movieNumber = roof + 1;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_info, container, false);

        toolbarTitle.setText("영화 상세");

        poster = rootView.findViewById(R.id.movieInfoPoster);
        name = rootView.findViewById(R.id.movieInfoName);
        date = rootView.findViewById(R.id.movieInfoDate);
        genre = rootView.findViewById(R.id.movieInfoGenre);
        bookRank = rootView.findViewById(R.id.movieInfoBookRank);
        rate = rootView.findViewById(R.id.movieInfoReviewRateCount);
        viewerCount = rootView.findViewById(R.id.movieInfoViewerCount);
        synopsis = rootView.findViewById(R.id.movieInfoSynopsis);
        director = rootView.findViewById(R.id.movieInfoDirector);
        actor = rootView.findViewById(R.id.movieInfoActor);
        ratingBar = rootView.findViewById(R.id.movieInfoReviewRatingBar);
        grade = rootView.findViewById(R.id.movieInfoGrade);

        thumbUp = rootView.findViewById(R.id.thumbUp);
        thumbDown = rootView.findViewById(R.id.thumbDown);
        goodCountView = rootView.findViewById(R.id.goodCount);
        badCountView = rootView.findViewById(R.id.badCount);
        writeReview1 = rootView.findViewById(R.id.gotoWrite1);
        writeReview2 = rootView.findViewById(R.id.gotoWrite2);
        seeReviews = rootView.findViewById(R.id.seeAllReview);

        scrollView = rootView.findViewById(R.id.scrollView);
        listView = rootView.findViewById(R.id.listView);
        adapter = new ReviewAdapter(getActivity(), reviews);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(getContext());
        NetworkCheck();
        recyclerView.setAdapter(recyclerAdapter);

        MainActivity.navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            MainActivity.drawerLayout.closeDrawers();
            int id = menuItem.getItemId();
            if (id == R.id.menu_list) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                Toast.makeText(getContext(), "영화 리스트로 돌아갑니다.", Toast.LENGTH_SHORT).show();

            } else if (id == R.id.menu_api) {
                Toast.makeText(getContext(), "한줄평 작성 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), WriteReviewActivity.class);
                intent.putExtra("movieName", movieName);
                intent.putExtra("movieNumber", movieNumber);
                intent.putExtra("movieGrade", intgrade);
                startActivity(intent);
            } else if (id == R.id.menu_book) {
                Toast.makeText(getContext(), "영화 에매 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "사용자 설정 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            }
            return true;
        });


        thumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
                    Toast.makeText(getActivity(), "인터넷 연결을 먼저 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    thumbUpClick();
                }
            }
        });
        thumbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
                    Toast.makeText(getActivity(), "인터넷 연결을 먼저 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    thumbDownClick();
                }
            }
        });

        writeReview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
                    Toast.makeText(getActivity(), "인터넷 연결을 먼저 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    GotoWrite();
                }
            }
        });
        writeReview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
                    Toast.makeText(getActivity(), "인터넷 연결을 먼저 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    GotoWrite();
                }
            }
        });
        seeReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
                    Toast.makeText(getActivity(), "인터넷 연결을 먼저 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    SeeReviews();
                }
            }
        });

        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, View view, int position) {
                ThumbnailItem item = recyclerAdapter.getItem(position);
                if (item.isPhotoOrVideo()) {
                    Intent intent = new Intent(getActivity(), GalleryImage.class);
                    intent.putExtra("ImageResource", item.getThumbImage());
                    startActivityForResult(intent, 103);
                } else if (!item.isPhotoOrVideo()) {
                    String url = videos[position - photoNumber];
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                //이미지 액티비티 전환 or 유튜브 화면
            }
        });

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }
        adapter.notifyDataSetChanged();
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

        return rootView;
    }

    public void NetworkCheck() {
        status = NetworkStatus.getConnectivityStatus(getActivity());
        if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
            selectPageInfoData(DatabaseContract.infoData.TABLE_NAME);
            recyclerView.setVisibility(View.GONE);
        } else {
            RequestMovieInfo();
            RequestComments();
        }
    }

    public void selectPageInfoData(String tableName) {
        if (database != null) {
            Cursor cursor = database.query(tableName, new String[]{
                    DatabaseContract.infoData.COLUMN_NAME_infoImage,
                    DatabaseContract.infoData.COLUMN_NAME_infoName,
                    DatabaseContract.infoData.COLUMN_NAME_infoGrade,
                    DatabaseContract.infoData.COLUMN_NAME_infoDate,
                    DatabaseContract.infoData.COLUMN_NAME_infoGenre,
                    DatabaseContract.infoData.COLUMN_NAME_infoGood,
                    DatabaseContract.infoData.COLUMN_NAME_infoBad,
                    DatabaseContract.infoData.COLUMN_NAME_infoReservationGrade,
                    DatabaseContract.infoData.COLUMN_NAME_infoReservationRate,
                    DatabaseContract.infoData.COLUMN_NAME_infoRating,
                    DatabaseContract.infoData.COLUMN_NAME_infoAudience,
                    DatabaseContract.infoData.COLUMN_NAME_infoSynopsis,
                    DatabaseContract.infoData.COLUMN_NAME_infoDirector,
                    DatabaseContract.infoData.COLUMN_NAME_infoActor
            }, null, null, null, null, null);

            cursor.moveToPosition(roof);
            Glide.with(this).load(cursor.getString(0)).into(poster);
            name.setText(cursor.getString(1));
            date.setText(cursor.getString(3));
            genre.setText(cursor.getString(4));
            goodCountView.setText(Integer.toString(cursor.getInt(5)));
            badCountView.setText(Integer.toString(cursor.getInt(6)));
            bookRank.setText(cursor.getString(7) + "위 " + cursor.getFloat(8) + "%");
            ratingBar.setRating(cursor.getFloat(9));
            rate.setText(Float.toString(cursor.getFloat(8)));
            viewerCount.setText(cursor.getString(10) + " 명");
            synopsis.setText(cursor.getString(11));
            director.setText(cursor.getString(12));
            actor.setText(cursor.getString(13));
            switch (cursor.getInt(2)) {
                case 12:
                    grade.setImageResource(R.drawable.ic_12);
                    break;

                case 15:
                    grade.setImageResource(R.drawable.ic_15);
                    break;

                case 19:
                    grade.setImageResource(R.drawable.ic_19);
                    break;
            }
        }
    }

    public void thumbUpClick() {
        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }
        if (likeDisLikeData.getLikestatus() == 0) {
            likeDisLikeData.setLikeyn("Y");
            likeDisLikeData.setLikestatus(1);
            thumbUp.setImageResource(R.drawable.ic_thumb_up_selected);
            goodCount++;
            goodCountView.setText(String.valueOf(goodCount));
            Toast.makeText(getActivity(), "goodcount올라갑니다.", Toast.LENGTH_SHORT).show();
            if (likeDisLikeData.getDislikestatus() > 0) {
                likeDisLikeData.setDislikeyn("N");
                likeDisLikeData.setLikestatus(0);
                likeDisLikeData.setDislikestatus(0);
                thumbUp.setImageResource(R.drawable.ic_thumb_up);
                thumbDown.setImageResource(R.drawable.ic_thumb_down);
                badCount--;
                badCountView.setText(String.valueOf(badCount));
                Toast.makeText(getActivity(), "중립입니다.", Toast.LENGTH_SHORT).show();
            }
        } else if (likeDisLikeData.getLikestatus() > 0) {
            likeDisLikeData.setLikeyn("N");
            likeDisLikeData.setLikestatus(0);
            thumbUp.setImageResource(R.drawable.ic_thumb_up);
            goodCount--;
            goodCountView.setText(String.valueOf(goodCount));
        }
        RequestThumbUPClick(likeDisLikeData.getLikeyn());
    }

    public void thumbDownClick() {
        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }
        if (likeDisLikeData.getDislikestatus() == 0) {
            likeDisLikeData.setDislikeyn("Y");
            likeDisLikeData.setDislikestatus(1);
            thumbDown.setImageResource(R.drawable.ic_thumb_down_selected);
            badCount++;
            badCountView.setText(String.valueOf(badCount));
            Toast.makeText(getActivity(), "badcount올라갑니다.", Toast.LENGTH_SHORT).show();
            if (likeDisLikeData.getLikestatus() > 0) {
                likeDisLikeData.setLikeyn("N");
                likeDisLikeData.setLikestatus(0);
                likeDisLikeData.setDislikestatus(0);
                thumbUp.setImageResource(R.drawable.ic_thumb_up);
                thumbDown.setImageResource(R.drawable.ic_thumb_down);
                goodCount--;
                goodCountView.setText(String.valueOf(goodCount));
                Toast.makeText(getActivity(), "중립입니다.", Toast.LENGTH_SHORT).show();
            }
        } else if (likeDisLikeData.getDislikestatus() > 0) {
            likeDisLikeData.setDislikeyn("N");
            likeDisLikeData.setDislikestatus(0);
            thumbDown.setImageResource(R.drawable.ic_thumb_down);
            badCount--;
            badCountView.setText(String.valueOf(badCount));
        }
        RequestThumbDownClick(likeDisLikeData.getDislikeyn());
    }


    public void RequestThumbUPClick(final String likeyn) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/increaseLikeDisLike";
        url += "?id=" + movieNumber + "&likeyn=Y";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ResponseThumbClick(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error + "가 문제입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    public void RequestThumbDownClick(final String dislikeyn) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/increaseLikeDisLike";
        url += "?id=" + movieNumber + "&dislikeyn=Y";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ResponseThumbClick(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error + "가 문제입니다.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }


    public void ResponseThumbClick(String response) {
        Gson gson = new Gson();
        LikeDisLikeResult result = gson.fromJson(response, LikeDisLikeResult.class);
        if (result != null) {
            if (result.code == 200) {
                Toast.makeText(getActivity(), result.result, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), result.result, Toast.LENGTH_SHORT).show();
            }
        }

    }

    protected void RequestMovieInfo() {
        String movieurl = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovie";
        movieurl += "?" + "id=" + movieNumber;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                movieurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getMovieDetail(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    protected void getMovieDetail(String response) {
        Gson gson = new Gson();
        MoiveListResult moiveListResult = gson.fromJson(response, MoiveListResult.class);
        if (moiveListResult != null) {
            ArrayList<MovieList> result = moiveListResult.result;
            if (moiveListResult.code == 200) {
                MovieList list = result.get(0);
                Glide.with(this).load(list.thumb).into(poster);
                name.setText(list.title);
                movieName = list.title;
                date.setText(list.date);
                rate.setText(Float.toString(list.user_rating));
                intgrade = list.grade;
                goodCount = list.like;
                goodCountView.setText(Integer.toString(list.like));
                badCount = list.dislike;
                badCountView.setText(Integer.toString(list.dislike));
                bookRank.setText(list.reservation_grade + "위 " + list.reservation_rate + "%");
                ratingBar.setRating(list.user_rating);
                viewerCount.setText(Integer.toString(list.audience) + "명");
                director.setText(list.director);
                synopsis.setText(list.synopsis);
                genre.setText(list.genre);
                actor.setText(list.actor);
                if (list.id == 1) {
                    String photo = list.photos;
                    String[] photos = photo.split(",");
                    photoNumber = photos.length;
                    for (int i = 0; i < photos.length; i++) {
                        recyclerAdapter.addItem(new ThumbnailItem(photos[i], true));
                    }
                    String video = list.videos;
                    videos = video.split(",");
                    videoNumber = photoNumber + videos.length;
                    for (int i = 0; i < videos.length; i++) {
                        String[] thumbSplit = videos[i].split("/");
                        recyclerAdapter.addItem(new ThumbnailItem(AppHelper.thumbnailHost1 + thumbSplit[3] + AppHelper.thumbnailHost2, false));
                    }
                }
                switch (list.grade) {
                    case 12:
                        grade.setImageResource(R.drawable.ic_12);
                        break;
                    case 15:
                        grade.setImageResource(R.drawable.ic_15);
                        break;
                    case 19:
                        grade.setImageResource(R.drawable.ic_19);
                        break;
                }

                DBHelper.insertPageInfoData(list.thumb, list.title, list.grade, list.date, list.genre, list.like, list.dislike, list.reservation_grade, list.reservation_rate, list.user_rating, list.audience, list.synopsis, list.director, list.actor);
            }
        }
    }

    public void RequestComments() {
        String commenturl = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        commenturl += "?" + "id=" + movieNumber;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                commenturl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CommentResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    public void CommentResponse(String response) {
        Gson gson = new Gson();
        CommentListResult commentListResult = gson.fromJson(response, CommentListResult.class);
        if (commentListResult != null) {
            ArrayList<CommentList> commentLists = commentListResult.result;
            if (commentListResult.code == 200) {
                CommentList commentList2 = commentLists.get(0); //오프라인시 상위1개만 받아오기 위해 선언
                for (int i = 0; i < commentLists.size(); i++) {
                    CommentList commentList = commentLists.get(i);
                    totcount = commentListResult.totalCount;
                    reviews.add(new ReviewParcelable(commentList.writer_image, commentList.writer, commentList.time, commentList.contents, commentList.recommend, commentList.rating));
                    listView.setAdapter(adapter);
                }
            }
        }
    }

    public void GotoWrite() {
        Intent intent = new Intent(getActivity(), WriteReviewActivity.class);
        intent.putExtra("movieName", name.getText().toString());
        intent.putExtra("movieGrade", intgrade);
        intent.putExtra("movieNumber", movieNumber);

        startActivityForResult(intent, WRITE_CODE);
    }

    public void SeeReviews() {
        Intent intent = new Intent(getActivity(), SeeAllReviewActivity.class);
        intent.putExtra("movieName", name.getText().toString());
        intent.putExtra("movieGrade", intgrade);
        intent.putExtra("movieId", movieNumber);
        intent.putExtra("movieRating", ratingBar.getRating());
        intent.putExtra("movieTotalCount", totcount);
        startActivityForResult(intent, LIST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_CODE) {
            String content = data.getStringExtra("contents");
            String writer = data.getStringExtra("writer");
            String time = data.getStringExtra("time");
            float rating = data.getFloatExtra("rating", 0);
            reviews.add(new ReviewParcelable(SeeAllReviewActivity.userProfile, writer, time, content, 0, rating));
            adapter.notifyDataSetChanged();
        } else if (requestCode == 105) {
           toolbarTitle.setText("영화 상세");
        }
    }
}