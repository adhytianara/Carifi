package id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.repository;

import android.app.Activity;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.database.ProfilePreference;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.model.User;

public class ProfileRepository {
    private static ProfileRepository instance;

    public static ProfileRepository getInstance() {
        if (instance == null) {
            instance = new ProfileRepository();
        }
        return instance;
    }

    public User getUserData(Activity mActivity) {
        String name = ProfilePreference.getUserName(mActivity);
        String encoded = ProfilePreference.getAvatar(mActivity);
        User userData = new User();
        userData.setName(name);
        userData.setAvatarBase64(encoded);
        return userData;
    }

    public void saveUserName(Activity mActivity, String userName) {
        ProfilePreference.saveUserName(mActivity, userName);
    }

    public void saveAvatar(Activity mActivity, String avatarBase64) {
        ProfilePreference.saveAvatar(mActivity, avatarBase64);
    }
}
