package dev.router.sisggar.data.vo.v1.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Paginacao {
	 private int size;
	 private long totalElements;
	 private int totalPages;
	 private int number;
}
