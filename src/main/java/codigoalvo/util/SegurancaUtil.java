package codigoalvo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

public class SegurancaUtil {

    public static String criptografar(String conteudo) {
	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] bytes = md.digest(conteudo.trim().toUpperCase().getBytes("UTF-8"));
	    return new String(Base64.encodeBase64(bytes));
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException exc) {
	    exc.printStackTrace();
	    throw new RuntimeException("Erro ao criptografar conteúdo. " + exc.getMessage());
	}
    }
}
