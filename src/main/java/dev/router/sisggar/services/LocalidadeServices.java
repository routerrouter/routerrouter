package dev.router.sisggar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.router.sisggar.converter.DozerConverter;
import dev.router.sisggar.data.dto.LocalidadeVO;
import dev.router.sisggar.data.model.Localidade;
import dev.router.sisggar.exception.NegocioException;
import dev.router.sisggar.exception.ResourceNotFoundException;
import dev.router.sisggar.repository.LocalidadeRepository;

@Service
public class LocalidadeServices {

	@Autowired
	LocalidadeRepository repository;
	

	private LocalidadeVO convertToLocalidadeVO(Localidade entity) {
		return DozerConverter.parseObject(entity, LocalidadeVO.class);
	}
	
	public LocalidadeVO create(LocalidadeVO request) {
		var entity = DozerConverter.parseObject(request, Localidade.class);
		boolean localidade_existente = repository.findByDescricao(request.getDescricao())
				.stream()
				.anyMatch(empresa_existente -> !empresa_existente.equals(request));

		if (localidade_existente) {
			throw new NegocioException("Já existe uma localidade com esta descrição");
		}
		var vo = DozerConverter.parseObject(repository.save(entity), LocalidadeVO.class);
		return vo;
	}

	public Page<Localidade> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	//	return page.map(this::convertToLocalidadeVO);
	}


	public LocalidadeVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new NegocioException("Não existe localidade com este ID"));

		return DozerConverter.parseObject(entity, LocalidadeVO.class);
	}

	public Page<LocalidadeVO> findLocalidadeByDescricao(String descricao, Pageable pageable) {
		var page = repository.findLocalidadeLikeDescricao(descricao, pageable);
		return page.map(this::convertToLocalidadeVO);
	}

	public Page<Localidade> findLocalidadesByDescricaoAndArmazem(String descricao, Integer armazem, Pageable pageable) {
		return repository.findLocalidadesByDescricaoAndArmazem(descricao, armazem, pageable);
	}

	public Page<Localidade> findLocalidadesByArmazem(Integer armazem, Pageable pageable) {
		return repository.findLocalidadesByArmazem(armazem, pageable);
	}

	/*public Page<LocalidadeVO> findByArmazemId(Integer armazem_id, Pageable pageable) {
		var page = repository.findByArmazemCodigo(armazem_id, pageable);
		return page.map(this::convertToLocalidadeVO);
	}*/

	public Localidade update(Localidade localidade) {
		var entity = repository.findById(localidade.getCodigo())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		return repository.save(entity);
	}

	public void delete(Long id) {
		Localidade entity = repository.findByCodigo(id);
				//.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

	@Transactional
	public LocalidadeVO isActiveOrNotLocalidade(Long id, Boolean status) {
		repository.isActiveOrNotLocalidade(id, status);
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe localidade com este ID"));
		return DozerConverter.parseObject(entity, LocalidadeVO.class);
	}

}
