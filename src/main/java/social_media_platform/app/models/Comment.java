package social_media_platform.app.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String text_content;
    @Column(nullable = false)
    private Timestamp date_sent;



    public Comment() {
    }

    public Comment(int commentId, User user, String text_content, Timestamp date_sent) {
        this.commentId = commentId;
        this.user = user;
        this.text_content = text_content;
        this.date_sent = date_sent;
    }

    public int getCommentId() {
        return this.commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText_content() {
        return this.text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public Timestamp getDate_sent() {
        return this.date_sent;
    }

    public void setDate_sent(Timestamp date_sent) {
        this.date_sent = date_sent;
    }

    public Comment commentId(int commentId) {
        setCommentId(commentId);
        return this;
    }

    public Comment user(User user) {
        setUser(user);
        return this;
    }

    public Comment text_content(String text_content) {
        setText_content(text_content);
        return this;
    }

    public Comment date_sent(Timestamp date_sent) {
        setDate_sent(date_sent);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Comment)) {
            return false;
        }
        Comment comment = (Comment) o;
        return commentId == comment.commentId && Objects.equals(user, comment.user) && Objects.equals(text_content, comment.text_content) && Objects.equals(date_sent, comment.date_sent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, user, text_content, date_sent);
    }

    @Override
    public String toString() {
        return "{" +
            " commentId='" + getCommentId() + "'" +
            ", user='" + getUser() + "'" +
            ", text_content='" + getText_content() + "'" +
            ", date_sent='" + getDate_sent() + "'" +
            "}";
    }
    
}
