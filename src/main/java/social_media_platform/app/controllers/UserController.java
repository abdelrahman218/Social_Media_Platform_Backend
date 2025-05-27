package social_media_platform.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import social_media_platform.app.services.PostsService;
import social_media_platform.app.services.UserService;
import social_media_platform.app.models.User;

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

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestParam String email, @RequestBody User updatedUser) {
        return userService.updateUser(email, updatedUser);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/getUserPosts")
    public ResponseEntity<?> getUserPosts(@RequestParam String email) {
        return ResponseEntity.ok(postsService.getUserPosts(email));
    }
    @GetMapping("/getFriends")
    public ResponseEntity<?> getFriends(@RequestParam String email) {
        return userService.getFriendsByUserEmail(email);
    }
}
