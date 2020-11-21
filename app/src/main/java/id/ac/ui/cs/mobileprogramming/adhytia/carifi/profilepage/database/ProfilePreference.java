package id.ac.ui.cs.mobileprogramming.adhytia.carifi.profilepage.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ProfilePreference {
    static final String KEY_USER_NAME = "user_name";
    static final String KEY_AVATAR_URI = "avatar";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveUserName(Context context, String userName){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_NAME, userName);
        editor.apply();
    }

    public static String getUserName(Context context){
        return getSharedPreference(context).getString(KEY_USER_NAME,"");
    }

    public static void saveAvatar(Context context, String avatarBase64){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_AVATAR_URI, avatarBase64);
        editor.apply();
    }

    public static String getAvatar(Context context){
        return getSharedPreference(context).getString(KEY_AVATAR_URI,"");
    }
}
