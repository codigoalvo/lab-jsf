package codigoalvo.controller;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@SessionScoped
@ManagedBean(name="controleLocale")
public class ControleLocale implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale currentLocale = new Locale("pt", "BR");

    public ControleLocale() {
	Logger.getLogger(ControleLocale.class).debug("construct");
    }

    public void englishLocale() {
	UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
	currentLocale = Locale.US;
	viewRoot.setLocale(currentLocale);
    }

    public void portugueseLocale() {
	UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
	currentLocale = new Locale("pt", "BR");
	viewRoot.setLocale(currentLocale);
    }

    public Locale getCurrentLocale() {
	return currentLocale;
    }

    public void setCurrentLocale(Locale currentLocale) {
	this.currentLocale = currentLocale;
    }

}
