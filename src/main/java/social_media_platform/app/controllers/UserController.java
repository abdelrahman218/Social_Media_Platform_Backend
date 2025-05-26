package social_media_platform.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import social_media_platform.app.services.PostsService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PostsService postsService;

    @GetMapping("/getFeedPosts")
    public ResponseEntity<?> getFeedPosts(@RequestParam String userEmail, @RequestParam String numOfPosts) {
        return postsService.getFeedPosts(userEmail, Integer.parseInt(numOfPosts));
    }
    
    @PostMapping(value = "/postAPost", consumes = "multipart/form-data")
    public ResponseEntity<?> postAPost(@RequestPart(required = false) String textContent, @RequestPart String userEmail, @RequestPart(required = false) List<MultipartFile> images) {
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
        return postsService.commentPost(Integer.parseInt(body.get("postId")), body.get("userEmail"), body.get("commentText"));
    }
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

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

