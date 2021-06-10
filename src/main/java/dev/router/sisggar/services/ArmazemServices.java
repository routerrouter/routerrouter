package dev.router.sisggar.services;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.router.sisggar.converter.DozerConverter;
import dev.router.sisggar.data.dto.ArmazemDTO;
import dev.router.sisggar.data.model.Armazem;
import dev.router.sisggar.exception.NegocioException;
import dev.router.sisggar.repository.ArmazemRepository;

@Service
public class ArmazemServices {

	@Autowired
	ArmazemRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	private ArmazemDTO convertModel(Armazem armazem) {
		return modelMapper.map(armazem, ArmazemDTO.class);
	}

	public ArmazemDTO create(ArmazemDTO armazemDto) {
		Armazem armazemEntity = convertToEntity(armazemDto);
		Armazem armazemSave = repository.save(armazemEntity);
		return convertModel(armazemSave);

	}
	
	public ArmazemDTO update(ArmazemDTO armazemDto) {
		System.err.print(armazemDto);
		Armazem armazemEntity = convertToEntity(armazemDto);
		Armazem armazemSave = repository.save(armazemEntity);
		return convertModel(armazemSave);
	}

	public List<Armazem> getArmazemList(Integer pageNo, Integer pageSize, String sortBy) {
		PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		 
	        Page<Armazem> pagedResult = repository.findAll(paging);
	         
	        if(pagedResult.hasContent()) {
	        	System.err.println("content");
	        	System.err.println("content 2" + pagedResult);
	            return pagedResult.getContent();
	        } else {
	        	System.out.println("no content");
	            return new ArrayList<Armazem>();
	        }
	}

	public List<ArmazemDTO> findAll() {
		var list = repository.findAll().stream().map(this::convertModel).collect(Collectors.toList());
		if (list.isEmpty()) {
			throw new NegocioException("Não existem items para listar");
		}
		return list;
	}

	public ArmazemDTO findById(Long id) {
		Armazem entity = repository.findByCodigo(id);
		if (entity.equals(null)) {
			throw new NegocioException("Não existem dados para este ID");
		}
		return convertModel(entity);
	}

	public ArmazemDTO findArmazemByDesignacao(String designacao) {
		Armazem armazem = repository.findArmazemByDesignacao(designacao);
		if (armazem == null) {
			new NegocioException("Não existe armazem com esta designação");
		}
		var obj = convertModel(armazem);

		return obj;
	}

	public List<ArmazemDTO> findArmazemLikeDesignacao(String designacao) {
		return repository.findArmazemLikeDesignacao(designacao).stream().map(this::convertModel)
				.collect(Collectors.toList());
	}

	public void delete(Long id) {
		Armazem entity = repository.findByCodigo(id);
	}

	@Transactional
	public ArmazemDTO isActiveOrNotArmazem(Long id, Boolean status) {
		repository.isActiveOrNotArmazem(id, status);
		var entity = repository.findById(id).orElseThrow(() -> new NegocioException("Não existe armazem com este ID"));
		return DozerConverter.parseObject(entity, ArmazemDTO.class);
	}

	private Armazem convertToEntity(ArmazemDTO armazemDto) throws ParseException {
		Armazem armazem = modelMapper.map(armazemDto, Armazem.class);
		return armazem;
	}

}
