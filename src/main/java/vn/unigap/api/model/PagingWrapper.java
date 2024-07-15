package vn.unigap.api.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingWrapper<T> {
  private int page;
  private int pageSize;
  private long totalElements;
  private long totalPages;
  private List<T> data;
}
