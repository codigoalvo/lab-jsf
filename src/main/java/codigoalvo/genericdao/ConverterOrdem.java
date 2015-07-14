package codigoalvo.genericdao;

import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converterOrdem")
public class ConverterOrdem implements Serializable, Converter {

    private static final long serialVersionUID = -3440496681927261311L;

    private List<Ordem> listaOrdem;

    public ConverterOrdem(){}

    public ConverterOrdem(List<Ordem> listaOrdem) {
	super();
	this.listaOrdem = listaOrdem;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String string) {
	for (Ordem obj : listaOrdem) {
	    if (obj.getAtributo().equals(string)) {
		return obj;
	    }
	}
	return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
	if (object == null) {
	    return null;
	}
	Ordem ordem = (Ordem)object;
	return ordem.getAtributo();
    }


    public List<Ordem> getListaOrdem() {
	return listaOrdem;
    }


    public void setListaOrdem(List<Ordem> listaOrdem) {
	this.listaOrdem = listaOrdem;
    }

}
