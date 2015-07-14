package codigoalvo.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(indexes = {@Index(name="usuario_login_unique", columnList="login", unique=true)})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 4860641136563274996L;

    @Id
    @SequenceGenerator(name = "SEQ_GRUPO", sequenceName = "SEQ_GRUPO_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRUPO")
    private Integer id;

    @NotNull
    @Length(min = 3, max = 50)
    private String login;

    @NotNull
    @Length(min = 4, max = 30)
    private String senha;

    @NotNull
    @NotBlank
    @Length(max = 150)
    private String nome;

    @NotNull
    @NotBlank
    @Length(max = 250)
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @Lob
    private byte[] imagem;

    private Date dataInativo;

    private Date dataUltimaAlteracaoSenha;

    private Date dataUltimoLogin;

    private Date dataUltimaFalhaLogin;

    private Integer tentativasLoginInvalido;

    public Usuario() {}

    @Override
    public String toString() {
	return login;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	Usuario other = (Usuario)obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public TipoUsuario getTipo() {
	return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
	this.tipo = tipo;
    }

    public byte[] getImagem() {
	return imagem;
    }

    public void setImagem(byte[] imagem) {
	this.imagem = imagem;
    }

    public Boolean getAtivo() {
	return dataInativo == null || dataInativo.after(new Date());
    }

    public void setAtivo(Boolean ativo) {
	if (ativo) {
	    this.dataInativo = null;
	} else {
	    this.dataInativo = new Date();
	}
    }

    public Date getDataInativo() {
	return dataInativo;
    }

    public void setDataInativo(Date dataInativo) {
	this.dataInativo = dataInativo;
    }

    public Date getDataUltimaAlteracaoSenha() {
	return dataUltimaAlteracaoSenha;
    }

    public void setDataUltimaAlteracaoSenha(Date dataUltimaAlteracaoSenha) {
	this.dataUltimaAlteracaoSenha = dataUltimaAlteracaoSenha;
    }

    public Date getDataUltimoLogin() {
	return dataUltimoLogin;
    }

    public void setDataUltimoLogin(Date dataUltimoLogin) {
	this.dataUltimoLogin = dataUltimoLogin;
    }

    public Date getDataUltimaFalhaLogin() {
	return dataUltimaFalhaLogin;
    }

    public void setDataUltimaFalhaLogin(Date dataUltimaFalhaLogin) {
	this.dataUltimaFalhaLogin = dataUltimaFalhaLogin;
    }

    public Integer getTentativasLoginInvalido() {
	return tentativasLoginInvalido;
    }

    public void setTentativasLoginInvalido(Integer tentativasLoginInvalido) {
	this.tentativasLoginInvalido = tentativasLoginInvalido;
    }

}
