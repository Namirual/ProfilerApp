package wepa.domain;

import org.hibernate.validator.constraints.NotBlank;
import wepa.repository.UUIDPersistable;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.Email;

// This class holds the users account information, personal information and 
// also points to all the profiles created by this account. Note that the account
// doesn't have direct knowledge of the profile pictures, only through the
// profile class, since different profiles of one user can have different pictures.

@Entity
public class Account extends UUIDPersistable {
    
    @NotNull
    @NotBlank
    private String name;
    
    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Column(unique=true)
    private String username;
    
    @NotNull
    @NotBlank
    private String password;
    
    private final Long creationTimeInMillis = Calendar.getInstance().getTimeInMillis();
    
    @OneToMany(mappedBy = "ownerAccount")
    private List<Profile> profiles;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;
    

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

    public List<Profile> getProfile() {
        return profiles;
    }

    public void setProfile(List<Profile> profile) {
        this.profiles = profile;
    }
    
    public void addProfile(Profile profile) {
        if (!this.profiles.contains(profile) && profile != null) {
            this.profiles.add(profile);
        }
    }

    public Long getCreationTimeInMillis() {
        return creationTimeInMillis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }


}