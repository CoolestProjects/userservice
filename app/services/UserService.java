package services;

import java.util.Random;

/**
 * Created by noelking on 2/16/15.
 */
public class UserService {
    
    private final String [] characters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "x", "y", "z", "#", "$", "%", "&", "0","1","2","3","4","5","6","7","8","9", "?"};

    private final int [] numeric = {0,1,2,3,4,5,6,7,8,9};

    public String resetPassword() {
        String password = "";
        final Random rand = new Random();
        for(int i = 0; i < 8; i++) {
            int charNumber = rand.nextInt(characters.length);
            password += characters[charNumber];
        }
        return password;
    }

}
