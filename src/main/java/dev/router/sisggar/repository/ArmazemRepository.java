package dev.router.sisggar.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.data.model.Armazem;

@Repository
public interface ArmazemRepository extends JpaRepository<Armazem, Long>{

	
	@Modifying
	@Query("UPDATE Armazem a SET a.enabled = :status WHERE a.id =:id")
	void isActiveOrNotArmazem(@Param("id") Long id, @Param("status") Boolean status);
	
	@Query("SELECT  a FROM Armazem a WHERE lower(a.designacao) LIKE LOWER(CONCAT ('%', :designacao, '%'))")
	List<Armazem> findArmazemLikeDesignacao(@Param("designacao") String designacao);
	
	Armazem findArmazemByDesignacao(String designacao);
	
	Armazem findByCodigo(Long codigo);
	
	
	
	
}