export interface ApiResponseDTO<T> {
  success: boolean;
  data: T;
  error: string | null;
  status: number;
}
