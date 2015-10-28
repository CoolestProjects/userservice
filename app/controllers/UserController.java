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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noelking on 10/17/14.
 */
public class UserController extends Controller {

    private static ObjectMapper mapper =  new ObjectMapper();
    
    private static UserService userService = new UserService();

    private static DateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");

    public static Result index() {
        return ok(index.render("Userservice is running"));
    }

    public static Promise<Result> createUser() throws IOException, ParseException {
        return Promise.promise(() -> saveUserRequest())
                .map((Result result) -> result);
    }

    public static Promise<Result> updateUserParentDetails(Integer userId) throws IOException, ParseException {
        return Promise.promise(() -> saveParentDetails(userId))
                .map((Result result) -> result);
    }

    private static Result saveParentDetails(Integer userId) throws IOException, ParseException {
        final User newDetails = parseRequest();
        final models.User user = UserDao.find.where().eq("id", userId).findUnique();
        user.parentEmail = newDetails.parentEmail;
        user.parentName = newDetails.parentName;
        user.parentMobile = newDetails.parentMobile;
        user.update();
        return created(Json.toJson(user));
    }

    private static Result saveUserRequest() throws IOException, ParseException {
        final models.User user = parseRequest();
        final User existsUser = getUser(user.email);
        if(existsUser != null && existsUser.id != 0) {
           return created(Json.toJson(existsUser));
        }
        user.active = true;
        return saveUser(user);
    }
    
    private static Result saveUser(final models.User user) {
        try {
            Logger.info("Saving user data {} " + user);
            user.save();
            return updateUserRole(UserRoles.USER, user);
        } catch (PersistenceException e){
            Logger.info("Failed to save user data " + e.getMessage(), e);
            return badRequest("User did not save " + e.getMessage());
        }
    }

    private static Result updateUserRole(UserRoles userRole, models.User user) {
        try {
            final Role role = new Role(userRole.getRoleName());
            role.userId = user.id;
            role.save();
            return created(Json.toJson(user));
        } catch (PersistenceException ex) {
            Logger.info("Failed to save user role " + ex.getMessage(), ex);
            return internalServerError("Error creating user record ");
        }
    }

    public static Promise<Result> getAll() {
        return Promise.promise(() -> getUserAll())
                .map((Result result) -> result);
    }

    public static Promise<Result> get(final String email) {
        return Promise.promise(() -> getUserFromEmail(email))
                .map((Result result) -> result);
    }

    public static Promise<Result> getById(final Integer id) {
        return Promise.promise(() -> getUserFromId(id))
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
        if(user != null && user.doPasswordsMatch(requestDetails.get("password"))
                && user.active) {
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
            user.setHashPassword(password);
            final EmailService emailService = new EmailService();
            emailService.sendLostPasswordEmail(new String[] {email}, user.firstname, password);
            return saveUser(user);
        } else {
            return badRequest("Invalid request");
        }
    }
    
    private static Result getUserFromEmail(final String email) {
        final User user = getUser(email);
        JsonNode userResponse = Json.toJson(user);
        return ok(userResponse);
    }

    private static Result getUserAll() {
        final List<User> user = UserDao.find.all();
        JsonNode userResponse = Json.toJson(user);
        return ok(userResponse);
    }

    private static Result getUserFromId(final Integer id) {
        final User user = UserDao.find.where().eq("id", id).findUnique();
        JsonNode userResponse = Json.toJson(user);
        return ok(userResponse);
    }


    private static User getUser(final String email) {
        return UserDao.find.where().eq("email", email).findUnique();
    }

    public static Promise<Result> update() throws IOException {
        return Promise.promise(() -> updateUser())
                .map((Result result) -> result);
    }

    private static Result updateUser() throws IOException, ParseException {
        final models.User user = parseRequest();
        final User updatedUser = getUpdateUser(user);
        try {
            Logger.info("Saving user data {} " + user);
            updatedUser.update();
            return created();
        } catch (PersistenceException e){
            Logger.info("Failed to save user data " + e.getMessage(), e);
            return badRequest("Invalid user object");
        }
    }
    
    private static User getUpdateUser(final User inputUser) {
        final User user = UserDao.find.where().eq("email", inputUser.email).findUnique();
        user.dateOfBirth = inputUser.dateOfBirth;
        user.firstname = inputUser.firstname;
        user.lastname = inputUser.lastname;
        user.mobileNumber = inputUser.mobileNumber;
        user.parentEmail = inputUser.parentEmail;
        user.parentMobile = inputUser.parentMobile;
        user.parentName = inputUser.parentName;
        user.twitter = inputUser.twitter;
        return user;
    }

    private static models.User parseRequest() throws IOException, ParseException {
        Logger.info("Received request body " + request().body().asText());
        final JsonNode request = request().body().asJson();
        Logger.info("Json object received " + request().body().asJson() + " = " + mapper.toString());
        final User userDetails = new User();
        userDetails.email = request.get("email").asText();
        userDetails.setHashPassword(request.get("password").asText());
        userDetails.firstname = request.get("firstname") != null ? request.get("firstname").asText() : "";
        userDetails.lastname = request.get("lastname") != null ? request.get("lastname").asText() : "";
        userDetails.acceptTerms = request.get("acceptTerms") != null ? request.get("acceptTerms").asText() : "";
        userDetails.coderdojoId = request.get("coderdojoId") != null ? request.get("coderdojoId").asInt() : 0;
        userDetails.dateOfBirth = request.get("dateOfBirth") != null ? new Date(request.get("dateOfBirth").asLong()) : null;
        userDetails.gender = request.get("gender") != null ? request.get("gender").asText() : "";
        userDetails.id = request.get("id") != null ? request.get("id").asLong() : 0l;
        userDetails.mobileNumber = request.get("mobileNumber") != null ? request.get("mobileNumber").asText() : "";
        userDetails.parentEmail = request.get("parentEmail") != null ? request.get("parentEmail").asText() : "";
        userDetails.parentMobile = request.get("parentMobile") != null ? request.get("parentMobile").asText() : "";
        userDetails.parentName = request.get("parentName") != null ? request.get("parentName").asText() : "";
        userDetails.twitter = request.get("twitter") != null ? request.get("twitter").asText() : "";
        userDetails.mailingList = request.get("mailingList") != null ? request.get("mailingList").asText() : "";
        return userDetails;
    }

    private static Map<String, String> parsePasswordRequest() throws IOException, ParseException {
        final JsonNode request = request().body().asJson();
        final Map<String, String> requstDetails = new HashMap<String, String>();
        requstDetails.put("email", request.get("email").asText());
        requstDetails.put("password" , request.get("password").asText());
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
