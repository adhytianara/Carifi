package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.viewmodel;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.repository.PeopleRepository;

public class PeopleDetailsViewModel extends ViewModel {
    private People people;
    private PeopleRepository mRepo;
    private boolean isSaved;

    public void init(People data, Activity mActivity) {
        if (people != null) {
            return;
        }
        people = data;
        mRepo = PeopleRepository.getInstance();
        setSaved(false);
        getPeopleById(mActivity);
    }

    private void getPeopleById(Activity mActivity) {
        mRepo.getPeopleById(people.getPeopleId(), mActivity, this);
    }

    public void savePeopletoDb(Activity mActivity) {
        mRepo.savePeopletoDb(people, mActivity, this);
    }

    public void deletePeopleByPeopleId(Activity mActivity) {
        mRepo.deletePeopleById(people.getPeopleId(), mActivity, this);
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
