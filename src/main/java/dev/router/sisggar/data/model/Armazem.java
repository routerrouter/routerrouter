package dev.router.sisggar.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;

import dev.router.sisggar.auditor.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@SequenceGenerator(name = "armazem_seq", sequenceName = "armazem_codigo_seq", allocationSize = 1)
@Table(name = "armazem")
public class Armazem extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "armazem_seq", strategy = GenerationType.AUTO)
	private Long codigo;

	@NotBlank
	@Column(name = "designacao", length = 180)
	private String designacao;

	@Column(nullable = false)
	private Integer capacidade;

	@Column(nullable = false)
	private Boolean enabled;

	@OneToMany(mappedBy = "armazem", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Localidade> localidades;

	public Armazem() {
	}


	@JsonBackReference
	public List<Localidade> getLocalidades() {
		return localidades;
	}
	

}