package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.viewmodel;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.repository.TvShowRepository;

public class TvShowDetailsViewModel extends ViewModel {
    private TvShow tvShow;
    private TvShowRepository mRepo;
    private boolean isSaved;

    public void init(TvShow data, Activity mActivity) {
        if (tvShow != null) {
            return;
        }
        tvShow = data;
        mRepo = TvShowRepository.getInstance();
        setSaved(false);
        getTvShowById(mActivity);
    }

    private void getTvShowById(Activity mActivity) {
        mRepo.getTvShowById(tvShow.getTvShowId(), mActivity, this);
    }

    public void saveTvShowtoDb(Activity mActivity) {
        mRepo.saveTvShowtoDb(tvShow, mActivity, this);
    }

    public void deleteTvShowByTvShowId(Activity mActivity) {
        mRepo.deleteTvShowById(tvShow.getTvShowId(), mActivity, this);
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
