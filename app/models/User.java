package models;

import org.mindrot.jbcrypt.BCrypt;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.annotate.JsonIgnore;
/**
 * Created by noelking on 10/17/14.
 */
@Entity
public class User extends Model {

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

    @Constraints.Required
    public Long coderdojoId;

    @Constraints.Required
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date dateOfBirth;

    @Constraints.MinLength(5)
    private String password;

    public String acceptTerms;
    public String parentName;
    public String parentEmail;
    public String parentMobile;

    public String twitter;
    public String mobileNumber;
    public String profileImage;
    
    public String salt;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    public Set<Role> roles;

    public User() {}

    public User(final String email, final String firstname, final String lastname,
                final Long coderdojoId,final Date dateOfBirth,
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
        if(password.equals(hashPasswordValue(enteredPassword)))
            return true;
        return false;
    }
    
    public void setHashPassword(final String password) {
        setPassword(hashPasswordValue(password));
    }
    
    private String hashPasswordValue(final String password) {
       if(salt == null)
           salt =  BCrypt.gensalt();
       return  BCrypt.hashpw(password,salt);
    }

    public String getPassword() {
        return password;
    }
}
