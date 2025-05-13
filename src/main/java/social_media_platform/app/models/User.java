package social_media_platform.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class User {
    @Id
    private String email;

    @Column(nullable = false)
    private String full_name;
    @Column(nullable = false)
    private String password;
    private String bio;
    private String profile_picture_name;
    @Column(nullable = false)
    private boolean isPrivate;


    public User() {
    }

    public User(String email, String full_name, String password, String bio, String profile_picture_name, boolean isPrivate) {
        this.email = email;
        this.full_name = full_name;
        this.password = password;
        this.bio = bio;
        this.profile_picture_name = profile_picture_name;
        this.isPrivate = isPrivate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return this.full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean checkPassword(String password) {
        return this.password==password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfile_picture_name() {
        return this.profile_picture_name;
    }

    public void setProfile_picture_name(String profile_picture_name) {
        this.profile_picture_name = profile_picture_name;
    }

    public boolean isIsPrivate() {
        return this.isPrivate;
    }

    public boolean getIsPrivate() {
        return this.isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public User email(String email) {
        setEmail(email);
        return this;
    }

    public User full_name(String full_name) {
        setFull_name(full_name);
        return this;
    }

    public User password(String password) {
        setPassword(password);
        return this;
    }

    public User bio(String bio) {
        setBio(bio);
        return this;
    }

    public User profile_picture_name(String profile_picture_name) {
        setProfile_picture_name(profile_picture_name);
        return this;
    }

    public User isPrivate(boolean isPrivate) {
        setIsPrivate(isPrivate);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(full_name, user.full_name) && Objects.equals(password, user.password) && Objects.equals(bio, user.bio) && Objects.equals(profile_picture_name, user.profile_picture_name) && isPrivate == user.isPrivate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, full_name, password, bio, profile_picture_name, isPrivate);
    }

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", full_name='" + getFull_name() + "'" +
            ", bio='" + getBio() + "'" +
            ", profile_picture_name='" + getProfile_picture_name() + "'" +
            ", isPrivate='" + isIsPrivate() + "'" +
            "}";
    }
    
}
