package social_media_platform.app.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import social_media_platform.app.models.Friend;
import social_media_platform.app.models.User;
import social_media_platform.app.repositories.UserRepository;

@Service
public class UserService {

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

    public ResponseEntity<?> updateUser(String email, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        // Example: update fields (add more as needed)
        user.setFull_name(updatedUser.getFull_name());
        user.setBio(updatedUser.getBio());
        user.setProfile_picture_name(updatedUser.getProfile_picture_name());
        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
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

        // Find all friendships where the user is either user1 or user2 and isPending is false
        List<Friend> friendsAsUser1 = friendRepository.findByUser1AndIsPending(user, false);
        List<Friend> friendsAsUser2 = friendRepository.findByUser2AndIsPending(user, false);

        // Collect the User objects who are friends with the given user
        List<User> friendUsers = new java.util.ArrayList<>();
        for (Friend f : friendsAsUser1) {
            friendUsers.add(f.getUser2());
        }
        for (Friend f : friendsAsUser2) {
            friendUsers.add(f.getUser1());
        }

        return new ResponseEntity<List<User>>(friendUsers, HttpStatus.OK);
    }
      public ResponseEntity<?> isPrivate(String userEmail) {
        Optional<User> userOptional = userRepository.findById(userEmail);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        if (user.getIsPrivate()) {
            return new ResponseEntity<String>("User is private", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User is public", HttpStatus.OK);
        }
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
            return new ResponseEntity<String>("User is now private", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User is now public", HttpStatus.OK);
        }
    }  
}
