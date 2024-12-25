export interface PaginatedResponse<T> {
  success: boolean;
  data: {
    content: T[];
    pageable: {
      pageNumber: number;
      pageSize: number;
      sort: any;
      offset: number;
      unpaged: boolean;
      paged: boolean;
    };
    last: boolean;
    totalPages: number;
    totalElements: number;
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
  error: null | string;
  status: number;
}
