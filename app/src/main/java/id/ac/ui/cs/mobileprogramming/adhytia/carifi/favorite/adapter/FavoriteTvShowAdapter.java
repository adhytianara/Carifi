package id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;

import static com.loopj.android.http.AsyncHttpClient.log;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.ListViewHolder> {
    private List<TvShow> listTvShow;
    private OnItemClickCallback onItemClickCallback;

    public FavoriteTvShowAdapter(List<TvShow> list) {
        log.e("DEasdBUG", String.valueOf(list.size()));
        this.listTvShow = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FavoriteTvShowAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteTvShowAdapter.ListViewHolder holder, int position) {

        TvShow tvShow = listTvShow.get(position);
        Glide.with(holder.itemView.getContext())
                .load(tvShow.getPosterURL())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.tvShowPoster);

        log.e("DEBUG", String.valueOf(tvShow.getName()));

        holder.tvName.setText(tvShow.getName());
        holder.tvOverview.setText(tvShow.getOverview());

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

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_overview)
        TextView tvOverview;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShow data);
    }
}