package social_media_platform.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Timestamp date_created;


    public PostLike() {
    }

    public PostLike(int likeId, User user, Timestamp date_created) {
        this.likeId = likeId;
        this.user = user;
        this.date_created = date_created;
    }

    public int getLikeId() {
        return this.likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDate_created() {
        return this.date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public PostLike likeId(int likeId) {
        setLikeId(likeId);
        return this;
    }

    public PostLike user(User user) {
        setUser(user);
        return this;
    }

    public PostLike date_created(Timestamp date_created) {
        setDate_created(date_created);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PostLike)) {
            return false;
        }
        PostLike postLike = (PostLike) o;
        return likeId == postLike.likeId && Objects.equals(user, postLike.user) && Objects.equals(date_created, postLike.date_created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeId, user, date_created);
    }

    @Override
    public String toString() {
        return "{" +
            " likeId='" + getLikeId() + "'" +
            ", user='" + getUser() + "'" +
            ", date_created='" + getDate_created() + "'" +
            "}";
    }

}
