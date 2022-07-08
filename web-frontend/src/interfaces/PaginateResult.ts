export interface PaginationResult {
  next: {
    page: number;
    limit: number;
  } | null;
  previous: {
    page: number;
    limit: number;
  } | null;
}
