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
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Timestamp date_created;


    public Like() {
    }

    public Like(int likeId, User user, Timestamp date_created) {
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

    public Like likeId(int likeId) {
        setLikeId(likeId);
        return this;
    }

    public Like user(User user) {
        setUser(user);
        return this;
    }

    public Like date_created(Timestamp date_created) {
        setDate_created(date_created);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Like)) {
            return false;
        }
        Like like = (Like) o;
        return likeId == like.likeId && Objects.equals(user, like.user) && Objects.equals(date_created, like.date_created);
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
