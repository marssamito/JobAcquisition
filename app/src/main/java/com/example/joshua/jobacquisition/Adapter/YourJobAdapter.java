package com.example.joshua.jobacquisition.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.joshua.jobacquisition.Network.CustomVolleyRequest;
import com.example.joshua.jobacquisition.R;
import com.example.joshua.jobacquisition.model.Image;

import java.util.List;

/**
 * Created by joshua on 8/19/2017.
 */
public class YourJobAdapter extends RecyclerView.Adapter<YourJobAdapter.MyViewHolder>{

    private ImageLoader imageLoader;
    private List<Image> images;
    private Context mContext;

    String v;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,date;

        public MyViewHolder(View view) {
            super(view);

            //thumbnailimg = (NetworkImageView) view.findViewById(R.id.thumbnail);
            title = (TextView) view.findViewById(R.id.jobTittle);
            date = (TextView) view.findViewById(R.id.jobDatePosted);


        }
    }


    public YourJobAdapter(Context context, List<Image> images) {
        mContext = context;
        this.images = images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yourjobs, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Image image = images.get(position);

        imageLoader = CustomVolleyRequest.getInstance((mContext).getApplicationContext())
                .getImageLoader();

        // holder.thumbnailimg.setImageUrl(image.getImage_url(), imageLoader);
        holder.title.setText(image.getMytitle());
        holder.date.setText(image.getMydate());

    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private YourJobAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final YourJobAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
