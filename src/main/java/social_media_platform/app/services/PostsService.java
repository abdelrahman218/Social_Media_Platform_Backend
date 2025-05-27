package social_media_platform.app.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import social_media_platform.app.models.Comment;
import social_media_platform.app.models.Friend;
import social_media_platform.app.models.Image;
import social_media_platform.app.models.PostLike;
import social_media_platform.app.models.Post;
import social_media_platform.app.models.User;
import social_media_platform.app.repositories.FriendRepository;
import social_media_platform.app.repositories.ImageRepository;
import social_media_platform.app.repositories.LikeRepository;
import social_media_platform.app.repositories.PostRepository;
import social_media_platform.app.repositories.UserRepository;

@Service
public class PostsService {
    private final String imagesDir = "postsImages";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private LikeRepository likeRepository;

    private List<Post> getAllFriendsPosts(User user) {
        List<Post> allPosts = new ArrayList<Post>();
        Iterator<Friend> friendsIterator = friendRepository.findByUser1AndIsPending(user, false).iterator();
        while (friendsIterator.hasNext()) {
            Friend friend = friendsIterator.next();
            allPosts.addAll(postRepository.findByUser(friend.getUser2()));
        }
        friendsIterator = friendRepository.findByUser2AndIsPending(user, false).iterator();
        while (friendsIterator.hasNext()) {
            Friend friend = friendsIterator.next();
            allPosts.addAll(postRepository.findByUser(friend.getUser1()));
        }
        return allPosts;
    }

    private List<Post> getRandomSetOfPosts(List<Post> allPosts, int numOfPosts) {
        Collections.shuffle(allPosts);
        return allPosts.subList(0, numOfPosts);
    }

    public ResponseEntity<?> getFeedPosts(String userEmail, int numOfPosts) {
        if (numOfPosts <= 0) {
            return new ResponseEntity<String>("Number of posts must be a positive number", HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOptional = userRepository.findById(userEmail);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        List<Post> allFriendsPosts = getAllFriendsPosts(user);

        List<Post> feedPosts = getRandomSetOfPosts(allFriendsPosts, Math.min(numOfPosts, allFriendsPosts.size()));

        return new ResponseEntity<List<Post>>(feedPosts, HttpStatus.OK);
    }

    private void addPostText(Post post, String textContent) {
        if (textContent == null) {
            return;
        }

        post.text_content(textContent);
    }

    private void addPostImages(Post post, List<MultipartFile> images) throws IOException {
        if (images == null) {
            return;
        }

        Iterator<MultipartFile> imagesIter = images.iterator();
        List<Image> postImagesObjects = new ArrayList<Image>(images.size());

        // Check if directory exists, if not it creates it
        File dir = new File(imagesDir);
        if (!dir.exists())
            dir.mkdirs();

        while (imagesIter.hasNext()) {
            MultipartFile currentImage = imagesIter.next();
            currentImage.transferTo(dir.toPath().resolve(currentImage.getOriginalFilename()));
            postImagesObjects.add(
                    new Image()
                            .name(currentImage.getOriginalFilename()));
        }

        post.setImages(postImagesObjects);
    }

    public ResponseEntity<?> postAPost(String textContent, String userEmail, List<MultipartFile> images) {
        if (textContent == null && images == null) {
            return new ResponseEntity<String>("Post must contain images or text", HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOptional = userRepository.findById(userEmail);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        Post newPost = new Post()
                .user(userOptional.get())
                .date_Posted(new Timestamp(Instant.now().toEpochMilli()));

        addPostText(newPost, textContent);
        try {
            addPostImages(newPost, images);            
        } catch (Exception e) {
            return new ResponseEntity<String>("Error happened while saving images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        postRepository.save(newPost);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<?> getPostImage(int imageId) {
        Optional<Image> image = imageRepository.findById(imageId);

        if (image.isEmpty()) {
            return new ResponseEntity<String>("Image Not Found", HttpStatus.NOT_FOUND);
        }

        Resource imageResource;
        try {
            imageResource = new UrlResource(Path.of(imagesDir, image.get().getName()).toUri());
        } catch (MalformedURLException e) {
            return new ResponseEntity<String>("Error while retrieving the image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageResource);
    }

    public ResponseEntity<?> likePost(int postId, String userEmail) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return new ResponseEntity<String>("Post Not Found", HttpStatus.NOT_FOUND);
        }

        Post post = postOptional.get();

        Optional<User> userOptional = userRepository.findById(userEmail);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        PostLike like = new PostLike()
                .user(user)
                .date_created(new Timestamp(Instant.now().toEpochMilli()));

        post.getLikes().add(like);

        postRepository.save(post);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<?> unLikePost(int postId, String userEmail) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return new ResponseEntity<String>("Post Not Found", HttpStatus.NOT_FOUND);
        }

        Post post = postOptional.get();

        Optional<User> userOptional = userRepository.findById(userEmail);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        Iterator<PostLike> likeIterator = post.getLikes().iterator();

        while (likeIterator.hasNext()) {
            PostLike like = likeIterator.next();

            if (like.getUser() == user) {
                post.getLikes().remove(like);
                likeRepository.delete(like);
                break;
            }
        }

        postRepository.save(post);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<?> commentPost(int postId, String userEmail, String commentText) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return new ResponseEntity<String>("Post Not Found", HttpStatus.NOT_FOUND);
        }

        Post post = postOptional.get();

        Optional<User> userOptional = userRepository.findById(userEmail);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        if (commentText.isBlank()) {
            return new ResponseEntity<String>("Comment is Empty", HttpStatus.BAD_REQUEST);
        }

        Comment comment = new Comment()
                .text_content(commentText)
                .user(user)
                .date_sent(new Timestamp(Instant.now().toEpochMilli()));

        post.getComments().add(comment);

        postRepository.save(post);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
     public List<Post> getUserPosts(String email) {
        List<Post> allPosts = new ArrayList<Post>();
        User user = userRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        allPosts.addAll(postRepository.findByUser(user));
        return allPosts;
    }
}
