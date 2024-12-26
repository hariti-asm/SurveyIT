export interface ApiResponseDTO<T> {
  data: T;
  message?: string;
  status?: string;
  success : boolean,
}

