package softuni.exam.instagraphlite.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity{

    @Column
    private String caption;


    @ManyToOne
    private User user;

    @ManyToOne
    private Picture picture;

    public Post() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
