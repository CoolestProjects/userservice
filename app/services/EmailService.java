package services;

import play.Configuration;
import play.Play;
import play.libs.ws.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.Configuration;


/**
 * Created by noelking on 2/16/15.
 */
public class EmailService {
    
    private String EMAIL_SERVICE_URL ;
    
    public EmailService() {
        EMAIL_SERVICE_URL = Play.application().configuration().getString("emailServiceUrl");
    }
    
    public void sendEmail(final String to, final String cc, final String subject, final String body) {
        final JsonNode json = Json.newObject()
                .put("to", to)
                .put("cc", cc)
                .put("body", body)
                .put("subject", subject);
        WS.url(EMAIL_SERVICE_URL).post(json);
        
    }
}
