package codigoalvo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

@Named
@RequestScoped
public class SegurancaUtilMd5 implements SegurancaUtil {

    public SegurancaUtilMd5() {
	Logger.getLogger(SegurancaUtilMd5.class).debug("####################  construct  ####################");
    }

    @Override
    public String criptografar(String conteudo) {
	if (conteudo == null) {
	    return null;
	}
	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] bytes = md.digest(conteudo.trim().toUpperCase().getBytes("UTF-8"));
	    return new String(Base64.encodeBase64(bytes));
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException exc) {
	    exc.printStackTrace();
	    throw new RuntimeException("Erro ao criptografar conteúdo. " + exc.getMessage());
	}
    }

    @Override
    public boolean criptografado(String conteudo) {
	return conteudo != null  &&  conteudo.endsWith("==");
    }

}
