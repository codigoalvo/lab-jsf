package codigoalvo.genericdao;

import java.io.Serializable;

public class Ordem implements Serializable {

    private static final long serialVersionUID = -7257893853522655785L;

    private String rotulo;
    private String atributo;

    public Ordem() {}

    public Ordem(String rotulo, String atributo) {
	super();
	this.rotulo = rotulo;
	this.atributo = atributo;
    }

    @Override
    public String toString() {
	return rotulo;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((atributo == null) ? 0 : atributo.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Ordem other = (Ordem)obj;
	if (atributo == null) {
	    if (other.atributo != null)
		return false;
	} else if (!atributo.equals(other.atributo))
	    return false;
	return true;
    }

    public String getRotulo() {
	return rotulo;
    }

    public void setRotulo(String rotulo) {
	this.rotulo = rotulo;
    }

    public String getAtributo() {
	return atributo;
    }

    public void setAtributo(String atributo) {
	this.atributo = atributo;
    }

}
