package com.midaschallenge.midasitchallenge2017;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by bo on 2017. 5. 27..
 */

public class PropertyManager {
    private static PropertyManager instance;

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MidasApplication.getContext());
        mEditor = mPrefs.edit();
    }
    private static final String FIELD_UUID = "uuid";
    private static final String FIELD_USER_NAME = "userName";

    public void setUuid(String uuid){
        mEditor.putString(FIELD_UUID, uuid);
        mEditor.commit();
    }

    public String getUuid(){
        return mPrefs.getString(FIELD_UUID, "");
    }

    public void setUserName(String userName){
        mEditor.putString(FIELD_USER_NAME, userName);
        mEditor.commit();
    }

    public String getUserName(){
        return mPrefs.getString(FIELD_USER_NAME, "");
    }
}
