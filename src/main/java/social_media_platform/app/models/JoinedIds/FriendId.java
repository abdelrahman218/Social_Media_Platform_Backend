package social_media_platform.app.models.JoinedIds;

import java.io.Serializable;
import java.util.Objects;

public class FriendId implements Serializable{
    private String user1;
    private String user2;
    

    public FriendId() {
    }

    public FriendId(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getUser1() {
        return this.user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return this.user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public FriendId user1(String user1) {
        setUser1(user1);
        return this;
    }

    public FriendId user2(String user2) {
        setUser2(user2);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FriendId)) {
            return false;
        }
        FriendId friendId = (FriendId) o;
        return Objects.equals(user1, friendId.user1) && Objects.equals(user2, friendId.user2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }

    @Override
    public String toString() {
        return "{" +
            " user1='" + getUser1() + "'" +
            ", user2='" + getUser2() + "'" +
            "}";
    }

}
