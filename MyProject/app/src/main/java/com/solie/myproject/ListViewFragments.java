package com.solie.myproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.solie.myproject.data.ListInfo;

import java.util.ArrayList;

public class ListViewFragments extends Fragment {

    int a, b;

    ListView listView;
    ReviewAdapter reviewAdapter;

    public static ListViewFragments newInstance(int i) {
        ListViewFragments fragments = new ListViewFragments();
        Bundle args = new Bundle();
        args.putInt("bundle",i);
        fragments.setArguments(args);
        return fragments;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(getArguments() != null) {
            a = getArguments().getInt("bundle");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.list_view,container,false);

        listView = rootView.findViewById(R.id.listView);
        reviewAdapter = new ReviewAdapter();
        listView.setAdapter(reviewAdapter);

        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }

        RequestListItem();

        return rootView;
    }

    public void RequestListItem() {
        b = a + 1;
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=" + b;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProcessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }
    public void ProcessResponse(String response) {
        Gson gson = new Gson();
        ListInfo info = gson.fromJson(response,ListInfo.class);
        if(info.code == 200) {
            ArrayList<ListViewFile> list = info.result;

            if(info.message.equals("movie readCommentList 성공")) {
                ListViewFile file = list.get(0);
                for(int i =0; i<list.size(); i++) {
                    reviewAdapter.addItem(new ListViewFile(file.writer, file.writer_image, file.time, file.rating, file.contents, file.recommend));
                }
            }
        }
    }


    public class ReviewAdapter extends BaseAdapter {

        ArrayList<ListViewFile> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(ListViewFile item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewFileData view = new ListViewFileData(getActivity());

            ListViewFile file = items.get(position);
            view.setReviewImage(file.getWriter_image());
            view.setReviewName(file.getWriter());
            view.setReviewRating(file.getRating());
            view.setReviewTime(file.getTime());
            view.setReviewMain(file.getContents());
            view.setRecommend(file.getRecommend());
            return view;
        }
    }
}
