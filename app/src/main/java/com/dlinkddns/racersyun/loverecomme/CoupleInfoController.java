package com.dlinkddns.racersyun.loverecomme;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CoupleInfoController {
    private static final String TAG = UserController.class.getSimpleName();

    public static void createInfo(String firebaseUid, String userName, String userBirthDay, String userPictureUrl,
                                  String coupleName, String coupleBirthDay, String couplePictureUrl,
                                  String meetDate, String rememberWord) {

        CoupleInfo coupleInfo = new CoupleInfo(firebaseUid, userName, userBirthDay, userPictureUrl, coupleName, coupleBirthDay, couplePictureUrl, meetDate, rememberWord);

        Map<String, Object> coupleInfoValues = coupleInfo.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/coupleInfos/" + firebaseUid, coupleInfoValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void editInfo(String firebaseUid, Map<String, Object> updateValues) {
        FirebaseDatabase.getInstance().getReference().child("coupleInfos").child(firebaseUid).updateChildren(updateValues);
    }

    public static void editInfo(String firebaseUid, CoupleInfo coupleInfo) {
        Map<String, Object> coupleInfoValues = coupleInfo.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/coupleInfos/" + firebaseUid, coupleInfoValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void deleteInfo(String firebaseUid, CoupleInfo coupleInfo) {
        coupleInfo.isDeleted = true;
        editInfo(firebaseUid, coupleInfo);
    }
}