package social_media_platform.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Column(nullable = false)
    private String name;
    

    public Image() {
    }

    public Image(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image imageId(int imageId) {
        setImageId(imageId);
        return this;
    }

    public Image name(String name) {
        setName(name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Image)) {
            return false;
        }
        Image image = (Image) o;
        return imageId == image.imageId && Objects.equals(name, image.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, name);
    }

    @Override
    public String toString() {
        return "{" +
            " imageId='" + getImageId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
    
}
