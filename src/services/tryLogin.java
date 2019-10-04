package services;

import java.util.HashMap;

public class tryLogin {

    public static void main(String[] args) {

        LoginService loginService = new LoginService();
        HashMap<String, String> user = loginService.GetUser("chrisjoohn", "password");
        System.out.println(user.size());





    }
}
