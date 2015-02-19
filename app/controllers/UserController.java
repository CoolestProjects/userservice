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
import services.EmailService;
import services.UserService;
import views.html.index;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

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
        return Promise.promise(() -> saveUserRequest())
                .map((Result result) -> result);
    }

    private static Result saveUserRequest() throws IOException {
        final models.User user = parseRequest();
        return saveUser(user);
    }
    
    private static Result saveUser(final models.User user) {
        try {
            Logger.info("Saving user data {} " + user);
            user.save();
            return updateUserRole(UserRoles.USER, user);
        } catch (PersistenceException e){
            Logger.info("Failed to save user data " + e.getMessage(), e);
            return badRequest("Invalid user object");
        }
    }

    private static Result updateUserRole(UserRoles userRole, models.User user) {
        try {
            final Role role = new Role(userRole.getRoleName());
            user.roles.add(role);
            user.save();
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

    public static Promise<Result> authenticate() {
        return Promise.promise(() -> processAuthRequest())
                .map((Result result) -> result);
    }

    public static Promise<Result> updatePassword() {
        return Promise.promise(() -> updateUserPassword())
                .map((Result result) -> result);
    }

    private static Result updateUserPassword() throws IOException, ParseException {
        final Map<String, String> requestDetails = parsePasswordRequest();
        final User user = UserDao.find.where().eq("email", requestDetails.get("email")).findUnique();
        user.setPassword(requestDetails.get("password"));
        try {
            Logger.info("Saving user data {} " + user);
            user.save();
            return returnUserRecord(user);
        } catch (PersistenceException e){
            Logger.info("Failed to save user data " + e.getMessage(), e);
            return badRequest("Invalid user object");
        }
    }

    private static Result processAuthRequest() throws IOException, ParseException {
        final Map<String, String> requestDetails = parseAuthRequest();
        final User user = UserDao.find.where().eq("email", requestDetails.get("email")).findUnique();
        Logger.info("Verifying user " + user.email);
        if(user != null && user.doPasswordsMatch(requestDetails.get("password"))) {
            return returnUserRecord(user);
        }
        return badRequest("Invalid user credentials");
    }
    
    private static Result returnUserRecord(final User user) {
        user.setPassword("");
        return ok(Json.toJson(user));
    }
    

    private static Result processLostPassword(final String email) {
        final User user = UserDao.find.where().eq("email", email).findUnique();
        if(user != null) {
            final String password = userService.resetPassword();
            user.setPassword(password);
            final EmailService emailService = new EmailService();
            emailService.sendLostPasswordEmail(email, user.firstname, password);
            return saveUser(user);
        } else {
            return badRequest("Invalid request");
        }
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

    private static Map<String, String> parsePasswordRequest() throws IOException, ParseException {
        final JsonNode request = request().body().asJson();
        final Map<String, String> requstDetails = new HashMap<String, String>();
        requstDetails.put("email", request.get("email").asText());
        requstDetails.put("password" , request.get("description").asText());
        return requstDetails;
    }

    private static Map<String, String> parseAuthRequest() throws IOException, ParseException {
        final JsonNode request = request().body().asJson();
        final Map<String, String> requstDetails = new HashMap<String, String>();
        requstDetails.put("email", request.get("email").asText());
        requstDetails.put("password" , request.get("password").asText());
        return requstDetails;
    }

}
