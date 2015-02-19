package services;

import play.Configuration;
import play.Play;
import play.libs.ws.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.Configuration;
import org.stringtemplate.v4.*;

/**
 * Created by noelking on 2/16/15.
 */
public class EmailService {
    
    private String EMAIL_SERVICE_URL ;
    private String EMAIL_LOSTPASSWORD_TEXT;
    
    public EmailService() {
        EMAIL_SERVICE_URL = Play.application().configuration().getString("emailServiceUrl");
        EMAIL_LOSTPASSWORD_TEXT =  Play.application().configuration().getString("lostPasswordText");
    }
    
    private void sendEmail(final String to, final String cc, final String bcc, final String subject, final String body) {
        final JsonNode json = Json.newObject()
                .put("to", to)
                .put("cc", cc)
                .put("bcc", cc)
                .put("body", body)
                .put("subject", subject);
        WS.url(EMAIL_SERVICE_URL).post(json);
    }
    
    public void sendLostPasswordEmail(final String to, final String name, final String password) {
        sendEmail(to, "", "", "Coolest Projects Awards - Password Rest" , transformLostPasswordEmail(name, password));
    }
    
    private String transformLostPasswordEmail(final String name, final String password) {
        ST body = new ST(EMAIL_LOSTPASSWORD_TEXT);
        body.add("name", name);
        body.add("password", password);
        return body.render();
    }
}
