package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.stringtemplate.v4.ST;
import play.Logger;
import play.Play;
import play.libs.F.Promise;
import play.libs.ws.WS;

import java.util.HashMap;
import java.util.Map;

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
    
    private void sendEmail(final String [] to, final String [] cc, final String [] bcc, final String subject, final String body) {
        final JsonNode json = createEmailJson(to,cc,bcc,subject,body);
        Logger.info("Sending " + json.asText());
        Promise<JsonNode> jsonPromise = WS.url(EMAIL_SERVICE_URL).post(json).map(response -> {
            Logger.info(response.asJson().asText());
            return response.asJson();
        });
    }
    
    private JsonNode createEmailJson(final String [] to, final String [] cc, final String [] bcc, final String subject, final String body) {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, JsonNode> senderMap = new HashMap<String, JsonNode>();
        senderMap.put("to",  mapper.valueToTree(to));
        senderMap.put("cc", mapper.valueToTree(cc));
        senderMap.put("bcc",mapper.valueToTree(bcc));
        ObjectNode json = mapper.createObjectNode();
        json.put("body", body)
            .put("subject", subject)
            .putAll(senderMap);
        return json;
    }
    
    public void sendLostPasswordEmail(final String [] to, final String name, final String password) {
        sendEmail(to, new String[]{}, new String[]{}, "Coolest Projects Awards - Password Rest" , transformLostPasswordEmail(name, password));
    }
    
    private String transformLostPasswordEmail(final String name, final String password) {
        ST body = new ST(EMAIL_LOSTPASSWORD_TEXT);
        body.add("name", name);
        body.add("password", password);
        return body.render();
    }
}
