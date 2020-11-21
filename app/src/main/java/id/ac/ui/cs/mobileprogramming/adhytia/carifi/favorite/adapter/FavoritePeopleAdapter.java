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
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;

public class FavoritePeopleAdapter extends RecyclerView.Adapter<FavoritePeopleAdapter.ListViewHolder> {
    private List<People> listPeople;
    private OnItemClickCallback onItemClickCallback;

    public FavoritePeopleAdapter(List<People> list) {
        this.listPeople = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FavoritePeopleAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoritePeopleAdapter.ListViewHolder holder, int position) {

        People people = listPeople.get(position);
        Glide.with(holder.itemView.getContext())
                .load(people.getProfileURL())
                .apply(new RequestOptions().override(200, 200))
                .into(holder.peoplePicture);

        holder.tvTitle.setText(people.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listPeople.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPeople.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_picture)
        ImageView peoplePicture;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(People data);
    }
}