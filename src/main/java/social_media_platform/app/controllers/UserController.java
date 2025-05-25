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
    
    @PostMapping("/postAPost")
    public ResponseEntity<?> postAPost(@RequestBody Map<String, String> postDetails, @RequestParam List<MultipartFile> images) {
        return postsService.postAPost(postDetails, images);
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
}
