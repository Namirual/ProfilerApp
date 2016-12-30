package wepa.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

// This is class holds all the questions associated with one profile.
// It also holds information about the owner of the profile as well as of the
// picture associated with this profile. Since one user can only have one active
// profile at a time, it also has a boolean value to indicate if the profile is
// the currently active one.
@Entity
public class Profile extends AbstractPersistable<Long> {

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    private Account ownerAccount;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "profile")
    private List<ProfileQuestion> profileQuestions;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany()
    private List<Account> answeringAccounts;

    private boolean active;

    private String profilePicId;

    private String thumbnailId;

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

    public void addProfileQuestion(ProfileQuestion question) {
        this.profileQuestions.add(question);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getProfilePicId() {
        return profilePicId;
    }

    public void setProfilePicId(String profilePicId) {
        this.profilePicId = profilePicId;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(String thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

}
