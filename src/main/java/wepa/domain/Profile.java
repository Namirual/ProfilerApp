package wepa.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import wepa.repository.UUIDPersistable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// This is class holds all the questions associated with one profile.
// It also holds information about the owner of the profile as well as of the
// picture associated with this profile. Since one user can only have one active
// profile at a time, it also has a boolean value to indicate if the profile is
// the currently active one.

@Entity
public class Profile extends UUIDPersistable {
    
    @Column(unique = true)
    private final Long creationTimeInMillis = Calendar.getInstance().getTimeInMillis();
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    private Account ownerAccount;
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "profile")
    private List<ProfileQuestion> profileQuestions;
    
    private boolean active;
    
    private ImageObject profilePic;

    
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

    public Long getCreationTimeInMillis() {
        return creationTimeInMillis;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ImageObject getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(ImageObject profilePic) {
        this.profilePic = profilePic;
    }

}
