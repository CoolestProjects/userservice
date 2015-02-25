package controllers;

import dao.UserDao;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Userservice is running"));
    }
    
    public static Result verify() {
        return ok();
        //return F.Promise.promise(() -> verifyDb())
          //      .map((Result result) -> result);
        
    }

    private static Result verifyDb() {
        try {
            final User user = UserDao.find.where().eq("id", 1).findUnique();
            return ok();
        } catch (Exception e) {
            return internalServerError();   
        }
    }
}
