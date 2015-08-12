package codigoalvo.controller;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@SessionScoped
@ManagedBean(name="localeController")
public class LocaleController extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LocaleController.class);

    private Locale currentLocale = new Locale("pt", "BR");

    public LocaleController() {
	LOGGER.debug("####################  construct  ####################");
    }

    public void englishLocale() {
	LOGGER.debug("englishLocale");
	UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
	currentLocale = Locale.US;
	viewRoot.setLocale(currentLocale);
    }

    public void portugueseLocale() {
	LOGGER.debug("portugueseLocale");
	UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
	currentLocale = new Locale("pt", "BR");
	viewRoot.setLocale(currentLocale);
    }

    public Locale getCurrentLocale() {
	LOGGER.debug("getCurrentLocale");
	return currentLocale;
    }

    public void setCurrentLocale(Locale currentLocale) {
	LOGGER.debug("setCurrentLocale");
	this.currentLocale = currentLocale;
    }

}
