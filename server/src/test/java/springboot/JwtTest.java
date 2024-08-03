package springboot;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtTest {

    @Test
    public void secretKey() {
        SecretKey secretKey = Jwts.SIG.HS512.key().build();
        String encodedKey = DatatypeConverter.printHexBinary(secretKey.getEncoded());
        System.out.println(encodedKey);
    }
}
