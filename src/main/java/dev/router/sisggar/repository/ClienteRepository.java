package dev.router.sisggar.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.data.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
	
	Page<Cliente> findAll(Pageable pageable);
	
	@Modifying
	@Query("UPDATE Cliente c SET c.enabled = :status WHERE c.id =:id")
	void isActiveOrNotCliente(@Param("id") Long id, @Param("status") Boolean status);
	
	@Query("SELECT  c FROM Cliente c WHERE lower(c.nome) LIKE LOWER(CONCAT ('%', :nome, '%'))")
	Page<Cliente> findClienteLikeNome(@Param("nome") String nome, Pageable pageable);
	
	@Query("SELECT  c FROM Cliente c WHERE c.enabled = :status")
	Page<Cliente> findClienteByStatus(@Param("status") boolean status, Pageable pageable);
	
	//Page<Cliente> findByLocalidade(@Param("localidade") Localidade localidade, Pageable pageable);
	
	@Query("SELECT  c FROM Cliente c WHERE c.posse = 0")
	Page<Cliente> findClienteSemPosse(Pageable pageable);
	
	@Query("SELECT  c FROM Cliente c WHERE c.telefone LIKE LOWER(CONCAT ('%', :telefone, '%'))")
	Page<Cliente> findClienteLikeTelefone(@Param("telefone") String telefone, Pageable pageable);
	
	@Query("SELECT  c FROM Cliente c WHERE c.nif LIKE LOWER(CONCAT ('%', :nif, '%'))")
	Page<Cliente> findClienteLikeNif(@Param("nif") String nif, Pageable pageable);
	
	Optional<Cliente> findByNome(String nome);
	


}
