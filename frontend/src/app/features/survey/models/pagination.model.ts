export interface PaginatedResponse<T> {
  data: {
    content: T[];
    pageable: {
      pageNumber: number;
      pageSize: number;
      sort: any;
      offset: number;
      paged: boolean;
      unpaged: boolean;
    };
    last: boolean;
    totalElements: number;
    totalPages: number;
    first: boolean;
    size: number;
    number: number;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    numberOfElements: number;
    empty: boolean;
  };
}
