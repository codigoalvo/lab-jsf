package codigoalvo.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensagemUtil {

    public static void enviarMensagemInfo(String message) {
	enviarMensagemInfo(message, "");
    }

    public static void enviarMensagemAviso(String message) {
	enviarMensagemAviso(message, "");
    }

    public static void enviarMensagemErro(String message) {
	enviarMensagemErro(message, "");
    }

    public static void enviarMensagemInfo(String summary, String detail) {
	enviarMensagem(FacesMessage.SEVERITY_INFO, summary, detail);
    }

    public static void enviarMensagemAviso(String summary, String detail) {
	enviarMensagem(FacesMessage.SEVERITY_WARN, summary, detail);
    }

    public static void enviarMensagemErro(String summary, String detail) {
	enviarMensagem(FacesMessage.SEVERITY_ERROR, summary, detail);
    }

    private static void enviarMensagem(FacesMessage.Severity severity, String summary, String detail) {
	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	FacesContext context = FacesContext.getCurrentInstance();
	if (context != null) {
	    context.addMessage(null, msg);
	} else {
	    System.out.println(summary + "("+detail+")");
	}
    }
}
