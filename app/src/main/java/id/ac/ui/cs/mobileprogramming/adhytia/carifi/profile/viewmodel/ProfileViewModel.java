package id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.viewmodel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.model.User;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.repository.ProfileRepository;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> user;
    private ProfileRepository mRepo;

    public void init(Activity mActivity) {
        if (user != null) {
            return;
        }
        user = new MutableLiveData<>();
        mRepo = ProfileRepository.getInstance();
        getUserDataSharedPreference(mActivity, this);
    }

    private void getUserDataSharedPreference(Activity mActivity, ProfileViewModel profileViewModel) {
        User userData = mRepo.getUserData(mActivity);
        user.setValue(userData);
    }

    public LiveData<User> getuser() {
        return user;
    }

    public void saveUserName(Activity mActivity, String userName) {
        mRepo.saveUserName(mActivity, userName);
    }

    public void saveAvatar(Activity mActivity, String avatarBase64) {
        mRepo.saveAvatar(mActivity, avatarBase64);
    }
}
