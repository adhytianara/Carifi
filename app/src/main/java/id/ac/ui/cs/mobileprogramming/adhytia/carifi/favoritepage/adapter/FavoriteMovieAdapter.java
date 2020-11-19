package id.ac.ui.cs.mobileprogramming.adhytia.carifi.favoritepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.model.Movie;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ListViewHolder> {
    private List<Movie> listMovie;
    private OnItemClickCallback onItemClickCallback;

    public FavoriteMovieAdapter(List<Movie> list) {
        this.listMovie = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteMovieAdapter.ListViewHolder holder, int position) {
        Toast.makeText(holder.itemView.getContext(), "MovieDetailsViewModel init", Toast.LENGTH_LONG).show();

        Movie movie = listMovie.get(position);
        Glide.with(holder.itemView.getContext())
                .load(movie.getPosterURL())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.moviePoster);

//        Toast.makeText(holder.itemView.getContext(), "onBindViewHolder " + movie.getTitle() + movie.getOverview(), Toast.LENGTH_LONG).show();
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovie.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_moviePoster)
        ImageView moviePoster;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_overview)
        TextView tvOverview;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }
}