package ru.ipimenov.myviolets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.ipimenov.myviolets.data.Violet;

public class VioletAdapter extends RecyclerView.Adapter<VioletAdapter.VioletViewHolder> {

    private ArrayList<Violet> violets;
    private OnVioletThumbnailClickListener onVioletThumbnailClickListener;
    private OnReachEndListener onReachEndListener;

    public VioletAdapter() {
        violets = new ArrayList<>();
    }

    interface OnVioletThumbnailClickListener {
        void onVioletThumbnailClick(int position);
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnVioletThumbnailClickListener(OnVioletThumbnailClickListener onVioletThumbnailClickListener) {
        this.onVioletThumbnailClickListener = onVioletThumbnailClickListener;
    }

    @NonNull
    @Override
    public VioletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.violet_item, parent, false);
        return new VioletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VioletViewHolder holder, int position) {
        if (position > violets.size() - 2 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        Violet violet = violets.get(position);
        ImageView imageView = holder.imageViewVioletThumbnail;
        Picasso.get().load(violet.getVioletThumbnailPath()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return violets.size();
    }

    class VioletViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewVioletThumbnail;

        public VioletViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewVioletThumbnail = itemView.findViewById(R.id.imageViewVioletThumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onVioletThumbnailClickListener != null) {
                        onVioletThumbnailClickListener.onVioletThumbnailClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setViolets(ArrayList<Violet> violets) {
        this.violets = violets;
        notifyDataSetChanged();
    }

    public void addViolets(ArrayList<Violet> violets) {
        this.violets.addAll(violets);
        notifyDataSetChanged();
    }

    public ArrayList<Violet> getViolets() {
        return violets;
    }
}
