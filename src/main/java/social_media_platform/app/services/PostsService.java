package social_media_platform.app.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import social_media_platform.app.models.Comment;
import social_media_platform.app.models.Friend;
import social_media_platform.app.models.Like;
import social_media_platform.app.models.Post;
import social_media_platform.app.models.User;
import social_media_platform.app.repositories.FriendRepository;
import social_media_platform.app.repositories.PostRepository;
import social_media_platform.app.repositories.UserRepository;

@Service
public class PostsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private PostRepository postRepository;

    private List<Post> getAllFriendsPosts(User user) {
        List<Post> allPosts = new ArrayList<Post>();
        Iterator<Friend> friendsIterator = friendRepository.findByUser1AndIs_pending(user, false).iterator();
        while (friendsIterator.hasNext()) {
            Friend friend = friendsIterator.next();
            allPosts.addAll(postRepository.findByUser(friend.getUser2()));
        }
        friendsIterator = friendRepository.findByUser2AndIs_pending(user, false).iterator();
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

        List<Post> feedPosts = getRandomSetOfPosts(allFriendsPosts, numOfPosts);

        return new ResponseEntity<List<Post>>(feedPosts, HttpStatus.OK);
    }

    public ResponseEntity<?> likePost(int postId, String userEmail){
        Optional<Post> postOptional=postRepository.findById(postId);

        if(postOptional.isEmpty()){
            return new ResponseEntity<String>("Post Not Found", HttpStatus.NOT_FOUND);
        }

        Post post=postOptional.get();

        Optional<User> userOptional=userRepository.findById(userEmail);

        if(userOptional.isEmpty()){
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user=userOptional.get();

        Like like=new Like()
                .user(user)
                .date_created(new Timestamp(Instant.now().toEpochMilli()));

        post.getLikes().add(like);

        postRepository.save(post);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<?> commentPost(int postId, String userEmail, String commentText){
        Optional<Post> postOptional=postRepository.findById(postId);

        if(postOptional.isEmpty()){
            return new ResponseEntity<String>("Post Not Found", HttpStatus.NOT_FOUND);
        }

        Post post=postOptional.get();

        Optional<User> userOptional=userRepository.findById(userEmail);

        if(userOptional.isEmpty()){
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user=userOptional.get();

        if(commentText.isBlank()){
            return new ResponseEntity<String>("Comment is Empty", HttpStatus.BAD_REQUEST);
        }

        Comment comment=new Comment()
                .text_content(commentText)
                .user(user)
                .date_sent(new Timestamp(Instant.now().toEpochMilli()));

        post.getComments().add(comment);

        postRepository.save(post);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
