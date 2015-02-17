package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import models.Role;
import models.User;
import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.UserRoles;
import services.UserService;
import views.html.index;

import javax.persistence.PersistenceException;
import java.io.IOException;

/**
 * Created by noelking on 10/17/14.
 */
public class UserController extends Controller {

    private static ObjectMapper mapper =  new ObjectMapper();
    
    private static UserService userService = new UserService();
    
    public static Result index() {
        return ok(index.render("Userservice is running"));
    }

    public static Promise<Result> createUser() throws IOException {
        return Promise.promise(() -> saveUser())
                .map((Result result) -> result);
    }

    private static Result saveUser() throws IOException {
        final models.User user = parseRequest();
        try {
            Logger.info("Saving user data {} " + user);
            user.save();
            return updateUserRole(UserRoles.USER, user);
        } catch (PersistenceException e){
            Logger.info("Failed to save user data " + e.getMessage(), e);
            return badRequest("Invalid user object");
        }
    }

    private static Result updateUserRole(UserRoles userRole, User user) {
        try {
            final Role role = new Role(user.id, userRole.getRoleName());
            role.save();
            return created();
        } catch (PersistenceException ex) {
            Logger.info("Failed to save user role " + ex.getMessage(), ex);
            return internalServerError("Error creating user record ");
        }
    }

    public static Promise<Result> get(final String email) {
        return Promise.promise(() -> getUserFromEmail(email))
                .map((Result result) -> result);
    }
    
    public static Promise<Result> lostPassword(final String email) {
        return Promise.promise(() -> processLostPassword(email))
                .map((Result result) -> result);
    }

    private static Result processLostPassword(final String email) {
        final User user = UserDao.find.where().eq("email", email).findUnique();
        final String password = userService.resetPassword();
        user.setPassword(password);
        JsonNode userResponse = Json.toJson(user);
        return ok(userResponse);
    }
    
    private static Result getUserFromEmail(final String email) {
        final User user = UserDao.find.where().eq("email", email).findUnique();
        JsonNode userResponse = Json.toJson(user);
        return ok(userResponse);
    }

    public static Promise<Result> update() throws IOException {
        return Promise.promise(() -> updateUser())
                .map((Result result) -> result);
    }

    private static Result updateUser() throws IOException {
        final models.User user = parseRequest();
        try {
            Logger.info("Saving user data {} " + user);
            user.update();
            return created();
        } catch (PersistenceException e){
            Logger.info("Failed to save user data " + e.getMessage(), e);
            return badRequest("Invalid user object");
        }
    }

    private static models.User parseRequest() throws IOException {
        Logger.info("Received request body " + request().body().asText());
        final JsonNode request = request().body().asJson();
        Logger.info("Json object received " + request().body().asJson() + " = " + mapper.toString());
        final models.User userDetails = mapper.readValue(request.toString(),models.User.class);
        Logger.debug("Read user data: {} ", userDetails);
        return userDetails;
    }
}
