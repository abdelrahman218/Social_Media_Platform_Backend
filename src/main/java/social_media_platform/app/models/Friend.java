package social_media_platform.app.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import social_media_platform.app.models.JoinedIds.FriendId;

import java.util.Objects;

@Entity
@IdClass(FriendId.class)
public class Friend {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_1_email", referencedColumnName = "email")
    private User user1;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_2_email", referencedColumnName = "email")
    private User user2;

    @Column(nullable = false)
    private boolean is_pending;
    @Column(nullable = false)
    private Timestamp date_created;
    

    public Friend() {
    }

    public Friend(User user1, User user2, boolean is_pending, Timestamp date_created) {
        this.user1 = user1;
        this.user2 = user2;
        this.is_pending = is_pending;
        this.date_created = date_created;
    }

    public User getUser1() {
        return this.user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return this.user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public boolean isIs_pending() {
        return this.is_pending;
    }

    public boolean getIs_pending() {
        return this.is_pending;
    }

    public void setIs_pending(boolean is_pending) {
        this.is_pending = is_pending;
    }

    public Timestamp getDate_created() {
        return this.date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public Friend user1(User user1) {
        setUser1(user1);
        return this;
    }

    public Friend user2(User user2) {
        setUser2(user2);
        return this;
    }

    public Friend is_pending(boolean is_pending) {
        setIs_pending(is_pending);
        return this;
    }

    public Friend date_created(Timestamp date_created) {
        setDate_created(date_created);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Friend)) {
            return false;
        }
        Friend friend = (Friend) o;
        return Objects.equals(user1, friend.user1) && Objects.equals(user2, friend.user2) && is_pending == friend.is_pending && Objects.equals(date_created, friend.date_created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2, is_pending, date_created);
    }

    @Override
    public String toString() {
        return "{" +
            " user1='" + getUser1() + "'" +
            ", user2='" + getUser2() + "'" +
            ", is_pending='" + isIs_pending() + "'" +
            ", date_created='" + getDate_created() + "'" +
            "}";
    }
    
}
