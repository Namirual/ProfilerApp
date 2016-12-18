package wepa.domain;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import wepa.repository.UUIDPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Account extends UUIDPersistable {

    @NotNull
    @Column(unique=true)
    private String username;
    private final Long creationTimeInMillis = Calendar.getInstance().getTimeInMillis();
    @NotNull
    @NotBlank
    private String password;
    @OneToOne(fetch = FetchType.EAGER)
    private Profile profile;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "reviewerAccount")
    private List<Review> reviews;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Long getCreationTimeInMillis() {
        return creationTimeInMillis;
    }


}