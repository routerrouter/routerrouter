package dev.router.sisggar.data.vo.v1.response;

import lombok.Data;

@Data
public class ResponseReturn {
	private Object data;
	private Paginacao paginacao;

	public ResponseReturn(Object data, int size, long totalElements, int totalPages, int number) {
		this.data = data;
		paginacao = new Paginacao(size, totalElements, totalPages, number);
	}
	
	public ResponseReturn(Object data) {
		this.data = data;
	}


	
	
}
