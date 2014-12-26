package dao;

import play.db.ebean.Model;
import models.User;


/**
 * Created by noelking on 10/19/14.
 */
public class UserDao {

    public static Model.Finder<Long,User> find = new Model.Finder<Long,User>(
            Long.class, User.class
    );
}
