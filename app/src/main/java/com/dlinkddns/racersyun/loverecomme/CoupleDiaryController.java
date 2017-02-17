package com.dlinkddns.racersyun.loverecomme;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CoupleDiaryController {
    private static final String TAG = UserController.class.getSimpleName();

    public static void createDiary(String firebaseUid, String userEmail, String userName, String profilePictureUrl) {
        User user = new User(firebaseUid, userEmail, userName, profilePictureUrl);

        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + firebaseUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void editDiary(String firebaseUid, Map<String, Object> updateValues) {
        FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUid).updateChildren(updateValues);
    }

    public static void editDiary(String firebaseUid, User user) {
        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + firebaseUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void deleteDiary(String firebaseUid, User user) {
        user.isDeleted = true;
        editDiary(firebaseUid, user);
    }
}