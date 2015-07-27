package codigoalvo.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

public class MsgUtil {

    private static final Logger LOGGER = Logger.getLogger(MsgUtil.class);
    private static final boolean DETAIN_IN_SUMARY = true;

    public static void enviarMsgInfo(String message) {
	enviarMsgInfo(message, "");
    }

    public static void enviarMsgAviso(String message) {
	enviarMsgAviso(message, "");
    }

    public static void enviarMsgErro(String message) {
	enviarMsgErro(message, "");
    }

    public static void enviarMsgInfo(String summary, String detail) {
	enviarMsg(FacesMessage.SEVERITY_INFO, summary, detail);
    }

    public static void enviarMsgAviso(String summary, String detail) {
	enviarMsg(FacesMessage.SEVERITY_WARN, summary, detail);
    }

    public static void enviarMsgErro(String summary, String detail) {
	enviarMsg(FacesMessage.SEVERITY_ERROR, summary, detail);
    }

    private static void enviarMsg(FacesMessage.Severity severity, String summary, String detail) {
	Object[] paramArray = MsgParamUtil.getParamArray(summary);
	if (paramArray.length > 0) {
	    summary = MsgParamUtil.getMessageId(summary);
	}
	summary = i18nMsg(summary, paramArray);
	if (DETAIN_IN_SUMARY && detail != null && !detail.isEmpty()) {
	    summary += " (" + detail + ")";
	    detail = null;
	}
	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	FacesContext context = FacesContext.getCurrentInstance();
	if (context != null) {
	    context.addMessage(null, msg);
	} else {
	    LOGGER.debug("[null context]: " + summary + "(" + detail + ")");
	}
    }

    public static String i18nMsg(String messageId, Object... arguments) {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	String msg = messageId;
	Locale locale = facesContext.getViewRoot().getLocale();
	ResourceBundle bundle = ResourceBundle.getBundle("codigoalvo.msg.messages", locale);
	try {
	    msg = bundle.getString(messageId);
	    if (arguments != null && arguments.length > 0) {
		msg = MessageFormat.format(msg, arguments);
	    }
	} catch (Exception exc) {
	    LOGGER.debug(ErrosUtil.getMensagemErro(exc));
	}
	return msg;
    }
}
