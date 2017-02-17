package com.dlinkddns.racersyun.loverecomme;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by racer on 2017-02-17.
 */

public class CoupleInfo {
    public String firebaseUid;

    public String userName;
    public String userBirthDay;
    public String userPictureUrl;

    public String coupleName;
    public String coupleBirthDay;
    public String couplePictureUrl;

    public String meetDate;
    public String rememberWord;

    public Boolean isDeleted;

    public CoupleInfo() {
    }

    public CoupleInfo(String firebaseUid, String userName, String userBirthDay, String userPictureUrl,
                      String coupleName, String coupleBirthDay, String couplePictureUrl,
                      String meetDate, String rememberWord) {
        this.firebaseUid = firebaseUid;
        this.userName = userName;
        this.userBirthDay = userBirthDay;
        this.userPictureUrl = userPictureUrl;

        this.coupleName = coupleName;
        this.coupleBirthDay = coupleBirthDay;
        this.couplePictureUrl = couplePictureUrl;

        this.meetDate = meetDate;
        this.rememberWord = rememberWord;

        this.isDeleted = false;
    }

    public CoupleInfo(String firebaseUid, String userName, String userBirthDay, String userPictureUrl,
                      String coupleName, String coupleBirthDay, String couplePictureUrl,
                      String meetDate, String rememberWord, Boolean isDeleted) {
        this.firebaseUid = firebaseUid;
        this.userName = userName;
        this.userBirthDay = userBirthDay;
        this.userPictureUrl = userPictureUrl;

        this.coupleName = coupleName;
        this.coupleBirthDay = coupleBirthDay;
        this.couplePictureUrl = couplePictureUrl;

        this.meetDate = meetDate;
        this.rememberWord = rememberWord;

        this.isDeleted = isDeleted;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firebaseUid", firebaseUid);
        result.put("userName", userName);
        result.put("userBirthDay", userBirthDay);
        result.put("userPictureUrl", userPictureUrl);

        result.put("coupleName", coupleName);
        result.put("coupleBirthDay", coupleBirthDay);
        result.put("couplePictureUrl", couplePictureUrl);

        result.put("meetDate", meetDate);
        result.put("rememberWord", rememberWord);
        result.put("isDeleted", isDeleted);
        return result;
    }

    @Exclude
    public static CoupleInfo parseSnapshot(DataSnapshot snapshot) {
        CoupleInfo coupleInfo = new CoupleInfo();

        coupleInfo.firebaseUid = (String) snapshot.child("firebaseUid").getValue();

        coupleInfo.userName = (String) snapshot.child("userName").getValue();
        coupleInfo.userBirthDay = (String) snapshot.child("userBirthDay").getValue();
        coupleInfo.userPictureUrl = (String) snapshot.child("userPictureUrl").getValue();

        coupleInfo.coupleName = (String) snapshot.child("coupleName").getValue();
        coupleInfo.coupleBirthDay = (String) snapshot.child("coupleBirthDay").getValue();
        coupleInfo.couplePictureUrl = (String) snapshot.child("couplePictureUrl").getValue();

        coupleInfo.meetDate = (String) snapshot.child("meetDate").getValue();
        coupleInfo.rememberWord = (String) snapshot.child("rememberWord").getValue();
        coupleInfo.isDeleted = (Boolean) snapshot.child("isDeleted").getValue();

        return coupleInfo;
    }
}
