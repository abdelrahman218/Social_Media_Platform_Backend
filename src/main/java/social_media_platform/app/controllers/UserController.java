package social_media_platform.app.controllers;

import org.springframework.web.multipart.MultipartFile;

import social_media_platform.app.services.PostsService;
import social_media_platform.app.services.UserService;
import social_media_platform.app.models.User;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import social_media_platform.app.models.LoginRequest;
import social_media_platform.app.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PostsService postsService;
    @Autowired
    private UserService userService;

    @GetMapping("/getFeedPosts")
    public ResponseEntity<?> getFeedPosts(@RequestParam String userEmail, @RequestParam String numOfPosts) {
        return postsService.getFeedPosts(userEmail, Integer.parseInt(numOfPosts));
    }

    @PostMapping(value = "/postAPost", consumes = "multipart/form-data")
    public ResponseEntity<?> postAPost(@RequestPart(required = false) String textContent, @RequestPart String userEmail,
            @RequestPart(required = false) List<MultipartFile> images) {
        return postsService.postAPost(textContent, userEmail, images);
    }

    @GetMapping("/getPostImage")
    public ResponseEntity<?> getPostImage(@RequestParam String imageId) {
        return postsService.getPostImage(Integer.parseInt(imageId));
    }

    @PostMapping("/likePost")
    public ResponseEntity<?> likePost(@RequestBody Map<String, String> body) {
        return postsService.likePost(Integer.parseInt(body.get("postId")), body.get("userEmail"));
    }

    @PostMapping("/unlikePost")
    public ResponseEntity<?> unlikePost(@RequestBody Map<String, String> body) {
        return postsService.unLikePost(Integer.parseInt(body.get("postId")), body.get("userEmail"));
    }

    @PostMapping("/commentPost")
    public ResponseEntity<?> commentPost(@RequestBody Map<String, String> body) {
        return postsService.commentPost(Integer.parseInt(body.get("postId")), body.get("userEmail"),
                body.get("commentText"));
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam String email) {
        return userService.getUserById(email);
    }

    @PostMapping("/{email}")
    public ResponseEntity<?> updateUser(
            @PathVariable String email,
            @RequestPart("full_name")String full_name,
            @RequestPart("bio") String bio,
            @RequestPart("password") String password,
            @RequestPart(value = "images",required = false) MultipartFile images) {
        return userService.updateUserWithImage(email, bio,password,full_name, images);
    }
    
    @GetMapping("/getUserPosts")
    public ResponseEntity<?> getUserPosts(@RequestParam String email) {
        return ResponseEntity.ok(postsService.getUserPosts(email));
    }
    @GetMapping("/getFriends")
    public ResponseEntity<?> getFriends(@RequestParam String email) {
        return userService.getFriendsByUserEmail(email);
    }
    @GetMapping("/deletePost")
    public ResponseEntity<?> deletePost(@RequestParam int postId, @RequestParam String userEmail) {
        return postsService.deletePost(postId, userEmail);
    }
    @PostMapping(value = "/editPost", consumes = "multipart/form-data")
    public ResponseEntity<?> editPost(
            @RequestPart String postId,
            @RequestPart(required = false) String textContent,
            @RequestPart(required = false) List<MultipartFile> images,@RequestPart String userEmail) {
        return postsService.editPost(Integer.parseInt(postId),userEmail, textContent, images);
    }
    @GetMapping("/togglePrivate")
    public ResponseEntity<?> togglePrivate(@RequestParam String userEmail) {
        return userService.togglePrivate(userEmail);
    }
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        System.out.println(user);
        if (user != null && user.checkPassword(loginRequest.getPassword())) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
}

