package dev.router.sisggar.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import dev.router.sisggar.auditor.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonPropertyOrder({ "id", "designacao", "endereco", "telefone","email","nif","fax","website","logotipo_url"})
@Entity
@SequenceGenerator(name = "empresa_seq", sequenceName = "empresa_codigo_seq", allocationSize = 1)
@Table(name="empresa")
public class Empresa extends Auditable<String> implements Serializable{

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "categoria_seq", strategy = GenerationType.AUTO)
	private Long codigo;
	
	@NotBlank
	@Column(name = "designacao", length = 200)
	private String designacao;
	
	@NotBlank
	@Column(name = "endereco", length = 500)
	private String endereco;
	
	@NotBlank
	@Size(max = 15)
	@Column(name = "telefone")
	private String telefone;
	
	@Email
	@Column(name = "email", length = 180)
	private String email;
	
	@NotBlank
	@Column(name = "nif", length = 15)
	private String nif;
	
	
	@Column(name = "website", length = 180)
	private String website;
	
	@Column(name = "logotipo_url", length = 1500)
	private String logotipo_url;

	private Boolean enabled;
	
	
	public Empresa() {
	}
	
}