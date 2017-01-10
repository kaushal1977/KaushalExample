import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.util.Base64Utils;




public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int[] DIGITS_POWER = {1,10,100,1000,10000,100000,1000000,10000000,100000000 };
		String token = randomString().substring(0, 6).toUpperCase();
		String session = randomString().substring(0, 16);
		System.out.println("token:"+ token);
		System.out.println("session:"+ session);
		System.out.println("session:"+ new String(generateToken(token)));
		
		byte[] hash = generateToken(token);
		int codeDigits = Integer.decode("6").intValue();

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary =
            ((hash[offset] & 0x7f) << 24) |
            ((hash[offset + 1] & 0xff) << 16) |
            ((hash[offset + 2] & 0xff) << 8) |
            (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

       String  result = Integer.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        System.out.println("OTP:"+ result);
        
        try {
        	byte[] hash2 =getSecurePassword(token,getSalt());
        	
        	int codeDigits1 = Integer.decode("6").intValue();

            // put selected bytes into result int
            int offset1 = hash2[hash2.length - 1] & 0xf;

            int binary1 =
                ((hash2[offset] & 0x7f) << 24) |
                ((hash2[offset + 1] & 0xff) << 16) |
                ((hash2[offset + 2] & 0xff) << 8) |
                (hash2[offset + 3] & 0xff);

            int otp1 = binary1 % DIGITS_POWER[codeDigits];

           String  result1 = Integer.toString(otp1);
            while (result1.length() < codeDigits1) {
                result1 = "0" + result1;
            }
            System.out.println("OTP1:"+ result1);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private static String randomString() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	 private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
	    {
	        //Always use a SecureRandom generator
	        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
	        //Create array for salt
	        byte[] salt = new byte[16];
	        //Get a random salt
	        sr.nextBytes(salt);
	        //return salt
	        return salt;
	    }
	
	 public static byte[] generateToken(String secret)
	 {
	 long minutes = System.currentTimeMillis() / 1000 / 60;
	 String concat = secret + minutes;
	 MessageDigest digest = null;
	 try
	 {
	 digest = MessageDigest.getInstance("MD5");
	 }
	 catch (NoSuchAlgorithmException e)
	 {
	 throw new IllegalArgumentException(e);
	 }
	 byte[] hash = digest.digest(concat.getBytes(Charset.forName("UTF-8")));
	 return Base64Utils.encode(hash);
	 }
	
	 
	 private static byte[] getSecurePassword(String passwordToHash, byte[] salt)
	    {
	        String generatedPassword = null;
	        try {
	            // Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            //Add password bytes to digest
	            md.update(salt);
	            //Get the hash's bytes 
	            byte[] bytes = md.digest(passwordToHash.getBytes());
	            //This bytes[] has bytes in decimal format;
	            //Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            generatedPassword = sb.toString();
	        } 
	        catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return generatedPassword.getBytes();
	    }

}
