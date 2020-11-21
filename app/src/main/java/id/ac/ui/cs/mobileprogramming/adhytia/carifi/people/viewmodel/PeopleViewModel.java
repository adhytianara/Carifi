package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.viewmodel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.repository.PeopleRepository;

public class PeopleViewModel extends ViewModel {
    private MutableLiveData<List<People>> peopleList;
    private PeopleRepository mRepo;

    public void init(FragmentActivity activity) {
        if (peopleList != null) {
            return;
        }
        peopleList = new MutableLiveData<>();
        peopleList.setValue(new ArrayList<People>());
        mRepo = PeopleRepository.getInstance();
        searchPopularPeople(activity);
    }

    public LiveData<List<People>> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList.setValue(peopleList);
    }

    public void searchPopularPeople(FragmentActivity activity) {
        mRepo.searchPopularPeople(activity, this);
    }

    public void searchPeopleByName(String name, FragmentActivity activity) {
        mRepo.searchPeopleByName(name, activity, this);
    }
}
