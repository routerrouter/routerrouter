package dev.router.sisggar.data.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import dev.router.sisggar.auditor.Auditable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@JsonPropertyOrder({ "id", "designacao", "endereco", "telefone","email","nif","fax","website","logotipo_url", "createdBy", "createdDate", "lastModifiedBy",
		"lastModifiedDate" })
public class EmpresaVO extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;
	private String designacao;
	private String endereco;
	private String telefone;
	private String email;
	private String nif;
	private String fax;
	private String website;
	private String logotipo_url;
	private Date createdDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	private Boolean enabled;

	public EmpresaVO() {
	}

	
}