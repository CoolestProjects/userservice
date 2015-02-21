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

    public Long userId;
    
    public Role() {}

    public Role(
    	final String name) {
    	this.name = name;
    }

    public String getName() {
    	return name;
    }


}