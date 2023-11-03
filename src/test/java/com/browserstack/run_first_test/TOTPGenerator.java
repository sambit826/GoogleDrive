package com.browserstack.run_first_test;
import org.jboss.aerogear.security.otp.Totp;

public class TOTPGenerator {

    public static String getTwoFactorCode(String bnb7m4um4nskpu7k3g44ht7kuimic4hi)
    {
        Totp totp = new Totp(bnb7m4um4nskpu7k3g44ht7kuimic4hi); // 2FA secret key
        String twoFactorCode = totp.now();
        System.out.println("i am otp generator function");
        System.out.println(twoFactorCode);//Generated 2FA code here
        return twoFactorCode;
    }
}