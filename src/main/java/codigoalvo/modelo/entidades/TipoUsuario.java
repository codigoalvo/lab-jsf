package codigoalvo.modelo.entidades;

public enum TipoUsuario {

    ADMIN("Administrador"), USER("Usuário");

    private String descricao;

    TipoUsuario(String descricao) {
	this.descricao = descricao;
    }

    public String getDescricao() {
	return descricao;
    }
}
