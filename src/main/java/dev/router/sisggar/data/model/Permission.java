package dev.router.sisggar.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "permission_seq", sequenceName = "permission_codigo_seq", allocationSize = 1)
@Table(name = "permission")
public class Permission implements GrantedAuthority, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "armazem_seq", strategy = GenerationType.AUTO)
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "description")
	private String description;

	@Override
	public String getAuthority() {
		return this.description;
	}

	
}
