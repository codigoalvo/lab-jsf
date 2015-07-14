package codigoalvo.genericdao;

import java.io.Serializable;

public class Filtro implements Serializable {

    private static final long serialVersionUID = -4144711178088406551L;

    private String filtro;
    private Ordem ordemAtual;
    private int maximoObjetos;
    private int posicaoAtual;
    private boolean buscarTodos;

    public Filtro() {
	buscarTodos = false;
	filtro = "";
	maximoObjetos = 5;
	posicaoAtual = 0;
    }

    @Override
    protected Filtro clone() {
	Filtro clone = new Filtro();
	clone.setFiltro(filtro);
	clone.setOrdemAtual(ordemAtual);
	clone.setMaximoObjetos(maximoObjetos);
	clone.setPosicaoAtual(posicaoAtual);
	clone.setBuscarTodos(buscarTodos);
	return clone;
    }

    @Override
    public String toString() {
	return "Filtro [filtro=" + filtro + ", ordemAtual=" + ordemAtual + ", maximoObjetos=" + maximoObjetos + ", posicaoAtual="
	        + posicaoAtual + ", buscarTodos=" + buscarTodos + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (buscarTodos ? 1231 : 1237);
	result = prime * result + ((filtro == null) ? 0 : filtro.hashCode());
	result = prime * result + maximoObjetos;
	result = prime * result + ((ordemAtual == null) ? 0 : ordemAtual.hashCode());
	result = prime * result + posicaoAtual;
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
	Filtro other = (Filtro)obj;
	if (buscarTodos != other.buscarTodos)
	    return false;
	if (filtro == null) {
	    if (other.filtro != null)
		return false;
	} else if (!filtro.equals(other.filtro))
	    return false;
	if (maximoObjetos != other.maximoObjetos)
	    return false;
	if (ordemAtual == null) {
	    if (other.ordemAtual != null)
		return false;
	} else if (!ordemAtual.equals(other.ordemAtual))
	    return false;
	if (posicaoAtual != other.posicaoAtual)
	    return false;
	return true;
    }

    public void paginaPrimeira() {
	posicaoAtual = 0;
    }

    public void paginaAnterior() {
	posicaoAtual -= maximoObjetos;
	if (posicaoAtual < 0) {
	    posicaoAtual = 0;
	}
    }

    public void paginaProxima(Integer totalObjetos) {
	if (posicaoAtual + maximoObjetos < totalObjetos) {
	    posicaoAtual += maximoObjetos;
	}
    }

    public void paginaUltima(Integer totalObjetos) {
	int resto = totalObjetos % maximoObjetos;
	if (resto > 0) {
	    posicaoAtual = totalObjetos - resto;
	} else {
	    posicaoAtual = totalObjetos - maximoObjetos;
	}
    }

    public String getFiltro() {
	return filtro;
    }

    public void setFiltro(String filtro) {
	this.filtro = protegeFiltro(filtro);
    }

    public String protegeFiltro(String filtro) {
	if (filtro == null) {
	    return null;
	}
	filtro = filtro.replaceAll("[';-]", "");
	return filtro;
    }

    public Ordem getOrdemAtual() {
	return ordemAtual;
    }

    public void setOrdemAtual(Ordem ordemAtual) {
	this.ordemAtual = ordemAtual;
    }

    public Integer getMaximoObjetos() {
	return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
	this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
	return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
	this.posicaoAtual = posicaoAtual;
    }

    public Boolean getBuscarTodos() {
	return buscarTodos;
    }

    public void setBuscarTodos(Boolean buscarTodos) {
	this.buscarTodos = buscarTodos;
    }

}
