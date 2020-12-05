package softuni.exam.instagraphlite.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column
    private String username;

    @Column
    private String password;

    @ManyToOne()
    @JoinColumn(name = "profile_picture_id",referencedColumnName = "id")
    private Picture picture;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Post> post;

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
