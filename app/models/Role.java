package models;

import play.db.ebean.Model;

import javax.persistence.*;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * Created by noelking on 10/17/14.
 */
@Entity
public class Role extends Model {

    @Id
    public Long id;

    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    public User user;

    public Role(
    	final String name) {
    	this.name = name;
    }

    public String getName() {
    	return name;
    }

}