package dev.router.sisggar.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.data.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	Optional<Empresa> findByDesignacao(String designacao);
	
	@Modifying
	@Query("UPDATE Empresa e SET e.enabled = :status WHERE e.id =:id")
	void isActiveOrNotEmpresa(@Param("id") Long id, @Param("status") Boolean status);
	
	public Page<Empresa> findByDesignacaoContaining(String nome, Pageable pageable);
	
	public Page<Empresa> findByEnabled(Boolean enabled,Pageable pageable);
}
