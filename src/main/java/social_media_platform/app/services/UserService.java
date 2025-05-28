package social_media_platform.app.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import social_media_platform.app.models.Friend;
import social_media_platform.app.models.User;
import social_media_platform.app.repositories.UserRepository;

@Service
public class UserService {
 private final String imagesDir = "usersImages";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private social_media_platform.app.repositories.FriendRepository friendRepository;

    public ResponseEntity<?> getUserById(String email) {
        Optional<User> userOptional = userRepository.findById(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(userOptional.get(), HttpStatus.OK);
    }

public ResponseEntity<?> updateUserWithImage(String email, String bio, String password, String fullName, MultipartFile profilePicture) {
    Optional<User> userOptional = userRepository.findById(email);
    if (userOptional.isEmpty()) {
        return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
    }

    User user = userOptional.get();
    try {
        String filePath = user.getProfile_picture_name(); 
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String uploadDir = imagesDir;
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            filePath = uploadDir + "/image_" + email + "_" + profilePicture.getOriginalFilename();
            File savedFile = new File(filePath);
            try (FileOutputStream fos = new FileOutputStream(savedFile)) {
                fos.write(profilePicture.getBytes());
            }
        }

        user.setProfile_picture_name(filePath);
        user.setEmail(email);
        user.setFull_name(fullName);
        user.setBio(bio);
        user.setPassword(password);

        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
    }
}


    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    public ResponseEntity<?> getFriendsByUserEmail(String email) {
        Optional<User> userOptional = userRepository.findById(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();

        List<Friend> friendsAsUser1 = friendRepository.findByUser1AndIsPending(user, false);
        List<Friend> friendsAsUser2 = friendRepository.findByUser2AndIsPending(user, false);

        List<User> friendUsers = new java.util.ArrayList<>();
        for (Friend f : friendsAsUser1) {
            friendUsers.add(f.getUser2());
        }
        for (Friend f : friendsAsUser2) {
            friendUsers.add(f.getUser1());
        }

        return new ResponseEntity<List<User>>(friendUsers, HttpStatus.OK);
    }


    public ResponseEntity<?> togglePrivate(String userEmail) {
        Optional<User> userOptional = userRepository.findById(userEmail);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        user.setIsPrivate(!user.getIsPrivate());
        userRepository.save(user);

        if (user.getIsPrivate()) {
           return ResponseEntity.ok(Map.of("message", "User is now private"));
        } else {
           return ResponseEntity.ok(Map.of("message", "User is now public"));

        }
    }
}
