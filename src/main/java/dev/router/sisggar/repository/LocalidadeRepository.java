package dev.router.sisggar.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.data.model.Localidade;



@Repository
public interface LocalidadeRepository extends JpaRepository<Localidade, Long>{

	
	@Modifying
	@Query("UPDATE Localidade l SET l.enabled = :status WHERE l.id=:id")
	void isActiveOrNotLocalidade(@Param("id") Long id, @Param("status") Boolean status);
	
	@Query("SELECT  l FROM Localidade l WHERE lower(l.descricao) LIKE LOWER(CONCAT ('%',:descricao, '%'))")
	Page<Localidade> findLocalidadeLikeDescricao(@Param("descricao") String descricao, Pageable pageable);
	

	//@Query("SELECT  l FROM Localidade l WHERE lower(l.descricao) LIKE LOWER(CONCAT ('%',:descricao, '%'))")
	Optional<Localidade> findByDescricao(@Param("descricao") String descricao);
	
	
	Page<Localidade> findAll(Pageable pageable);
	
	@Query("SELECT  l FROM Localidade l WHERE lower(l.descricao) LIKE LOWER(CONCAT (',:descricao,')) and l.armazem =:armazem")
	Page<Localidade> findLocalidadesByDescricaoAndArmazem(@Param("descricao") String descricao,@Param("armazem") Integer armazem, Pageable pageable);
	
	Page<Localidade> findLocalidadesByArmazem(@Param("armazem") Integer armazem, Pageable pageable);
	
	//@Query("SELECT l FROM Localidade l WHERE l.armazem_id=:armazem_id")
	Page<Localidade> findByArmazemCodigo(@Param("armazem_id") Integer armazem_id, Pageable pageable);
	
	
	Optional<Localidade> findByCodigoAndArmazemCodigo(Long id, Long armazemId);
	
	Localidade findByCodigo(Long codigo);
	
}