package wepa.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import wepa.repository.UUIDPersistable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile extends UUIDPersistable {
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(mappedBy = "profile")
    private Account ownerAccount;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "profile")
    private List<ProfileQuestion> profileQuestions;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "reviewedProfile")
    private List<Review> reviews;

    public Account getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(Account ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public List<ProfileQuestion> getProfileQuestions() {
        if (this.profileQuestions == null) {
            this.profileQuestions = new ArrayList<>();
        }
        return profileQuestions;
    }

    public void setProfileQuestions(List<ProfileQuestion> profileQuestions) {
        this.profileQuestions = profileQuestions;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
