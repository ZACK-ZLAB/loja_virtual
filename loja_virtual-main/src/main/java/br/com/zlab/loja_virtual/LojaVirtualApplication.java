package br.com.zlab.loja_virtual;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LojaVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualApplication.class, args);
		
		 // Generate a random key
        byte[] keyBytes = generateRandomKey(32); // 32 bytes for a 256-bit key

        // Encode the key in base64
        String base64Key = encodeBase64(keyBytes);

        // Print the base64-encoded key
        System.out.println("Base64-encoded Key: " + base64Key);
	}
	
	private static byte[] generateRandomKey(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[length];
        secureRandom.nextBytes(key);
        return key;
    }

    private static String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

}
