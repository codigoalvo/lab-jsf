package codigoalvo.util;

import javax.faces.context.FacesContext;

public class FacesUtil {

    public static void atualizarComponentes(String... ids) {
	if (ids == null) {
	    return;
	}
	for (String id : ids) {
	    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(id);
	}
    }
}
