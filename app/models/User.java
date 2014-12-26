package models;

import org.mindrot.jbcrypt.BCrypt;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    public String twitter;
    public String mobileNumber;
    public String profileImage;

    @OneToMany
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
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getPassword() {
        return password;
    }
}
