package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;

public class TvShowListAdapter extends RecyclerView.Adapter<TvShowListAdapter.ListViewHolder> {
    private List<TvShow> listTvShow;
    private OnItemClickCallback onItemClickCallback;

    public TvShowListAdapter(List<TvShow> list) {
        this.listTvShow = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TvShowListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_homepage, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowListAdapter.ListViewHolder holder, int position) {
        TvShow tvShow = listTvShow.get(position);
        Glide.with(holder.itemView.getContext())
                .load(tvShow.getPosterURL())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.tvShowPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTvShow.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_picture)
        ImageView tvShowPoster;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShow data);
    }
}