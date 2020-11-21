package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.viewmodel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.repository.TvShowRepository;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<List<TvShow>> tvShowList;
    private TvShowRepository mRepo;

    public void init(FragmentActivity activity) {
        if (tvShowList != null) {
            return;
        }
        tvShowList = new MutableLiveData<>();
        tvShowList.setValue(new ArrayList<TvShow>());
        mRepo = TvShowRepository.getInstance();
        searchPopularTvShow(activity);
    }

    public LiveData<List<TvShow>> getTvShowList() {
        return tvShowList;
    }

    public void setTvShowList(List<TvShow> tvShowList) {
        this.tvShowList.setValue(tvShowList);
    }

    public void searchPopularTvShow(FragmentActivity activity) {
        mRepo.searchPopularTvShow(activity, this);
    }

    public void searchTvShowByName(String title, FragmentActivity activity) {
        mRepo.searchTvShowByName(title, activity, this);
    }
}
