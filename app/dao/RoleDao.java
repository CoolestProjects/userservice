package dao;

import models.Role;
import play.db.ebean.Model;

/**
 * Created by noelking on 2/21/15.
 */
public class RoleDao {

    public static Model.Finder<Long, Role> find = new Model.Finder<Long,Role>(
            Long.class, Role.class
    );
}
