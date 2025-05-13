package social_media_platform.app.models;

import java.security.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Objects;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Like> likes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    private String text_content;
    @Column(nullable = false)
    private Timestamp date_Posted;


    public Post() {
    }

    public Post(int postId, User user, List<Image> images, List<Like> likes, List<Comment> comments, String text_content, Timestamp date_Posted) {
        this.postId = postId;
        this.user = user;
        this.images = images;
        this.likes = likes;
        this.comments = comments;
        this.text_content = text_content;
        this.date_Posted = date_Posted;
    }

    public int getPostId() {
        return this.postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Like> getLikes() {
        return this.likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getText_content() {
        return this.text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public Timestamp getDate_Posted() {
        return this.date_Posted;
    }

    public void setDate_Posted(Timestamp date_Posted) {
        this.date_Posted = date_Posted;
    }

    public Post postId(int postId) {
        setPostId(postId);
        return this;
    }

    public Post user(User user) {
        setUser(user);
        return this;
    }

    public Post images(List<Image> images) {
        setImages(images);
        return this;
    }

    public Post likes(List<Like> likes) {
        setLikes(likes);
        return this;
    }

    public Post comments(List<Comment> comments) {
        setComments(comments);
        return this;
    }

    public Post text_content(String text_content) {
        setText_content(text_content);
        return this;
    }

    public Post date_Posted(Timestamp date_Posted) {
        setDate_Posted(date_Posted);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Post)) {
            return false;
        }
        Post post = (Post) o;
        return postId == post.postId && Objects.equals(user, post.user) && Objects.equals(images, post.images) && Objects.equals(likes, post.likes) && Objects.equals(comments, post.comments) && Objects.equals(text_content, post.text_content) && Objects.equals(date_Posted, post.date_Posted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, user, images, likes, comments, text_content, date_Posted);
    }

    @Override
    public String toString() {
        return "{" +
            " postId='" + getPostId() + "'" +
            ", user='" + getUser() + "'" +
            ", images='" + getImages() + "'" +
            ", likes='" + getLikes() + "'" +
            ", comments='" + getComments() + "'" +
            ", text_content='" + getText_content() + "'" +
            ", date_Posted='" + getDate_Posted() + "'" +
            "}";
    }

}
