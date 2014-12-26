package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by noelking on 10/17/14.
 */
@Entity
public class Role extends Model {

    @Id
    public Long id;

    public Long userId;
    public String name;

    @ManyToOne
    private User user;

    public Role(final Long userId,
    	final String name) {
    	this.userId = userId;
    	this.name = name;
    }

    public String getName() {
    	return name;
    }

    public Long getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}