package models;

import dao.RoleDao;
import org.mindrot.jbcrypt.BCrypt;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created by noelking on 10/17/14.
 */
@Entity
public class User extends Model implements java.io.Serializable {

    @Id
    public Long id;

    @Constraints.Required
    @Constraints.MinLength(5)
    @Constraints.Email
    public String email;

    @Constraints.Required(message="validation.required.firstname")
    @Constraints.MinLength(2)
    public String firstname;

    @Constraints.Required
    @Constraints.MinLength(2)
    public String lastname;

    public Integer coderdojoId;

    public String coderdojoUUId;

    public Date dateOfBirth;

    @Constraints.MinLength(5)
    private String password;

    public String acceptTerms;
    public String parentName;
    public String parentEmail;
    public String parentMobile;
    public String mailingList;
    
    public String twitter;
    public String mobileNumber;
    public String profileImage;

    public boolean active;

    public String city;
    public String country;
    public String role;

    public String gender;

    public List<Role> roles;

    public Set<String> authorities;

    public User() {}

    public User(final String email, final String firstname, final String lastname,
                final Integer coderdojoId,final Date dateOfBirth,
                final String twitter, final String mobileNumber, final String profileImage) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobileNumber = mobileNumber;
        this.coderdojoId = coderdojoId;
        this.twitter = twitter;
        this.dateOfBirth = dateOfBirth;
        this.profileImage = profileImage;
    }

    public void setPassword(final String password) {
        this.password = password; 
    }
    
    public boolean doPasswordsMatch(final String enteredPassword) {
        return enteredPassword.equals(password);
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
       return RoleDao.find.where().eq("user_id", id).findList();
    }

    public String getMailingList() {
        return mailingList;
    }

    public void setMailingList(String mailingList) {
        this.mailingList = mailingList;
    }
}
