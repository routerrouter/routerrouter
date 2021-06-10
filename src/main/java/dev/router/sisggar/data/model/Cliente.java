package dev.router.sisggar.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dev.router.sisggar.auditor.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Table(name="cliente")
@SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_codigo_seq", allocationSize = 1)
@Entity
public class Cliente extends Auditable<String> implements Serializable{

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "cliente_seq", strategy = GenerationType.AUTO)
	private Long codigo;
	
	@NotBlank
	@Column(name = "nome", nullable = false, length = 200)
	private String nome;
	
	@NotBlank
	@Column(name = "endereco", nullable = false, length = 500)
	private String endereco;
	
	@NotBlank
	@Size(max = 12)
	@Column(name = "telefone", nullable = false, length = 40,unique = true)
	private String telefone;
	
	@Email
	@Column(name = "email", nullable = false, length = 180,unique = true)
	private String email;
	
	@NotBlank
	@Column(name = "nif", nullable = false, length = 180,unique = true)
	private String nif;
	
	@Column(nullable = false)
	private Integer posse;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "localidade_id")
	private Localidade localidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user_id;
	

	@Column(nullable = false)
	private Boolean enabled;
	
	
	public Cliente() {
	}

	@JsonBackReference
	public Localidade getLocalidade() {
		return localidade;
	}

}
