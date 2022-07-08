interface PaginationResult {
  next: {
    page: number;
    limit: number;
  } | null;
  previous: {
    page: number;
    limit: number;
  } | null;
}

export const pagination = (page: any, size: any, count: number) => {
  const pageNumber = page ? parseInt(page.toString()) : 1;
  const limit = size ? parseInt(size.toString()) : 5;
  const skip = (pageNumber - 1) * limit;

  const startIndex = skip;
  const endIndex = pageNumber * limit;

  const result: PaginationResult = {
    next: {
      page: pageNumber + 1,
      limit: limit,
    },
    previous: {
      page: pageNumber - 1,
      limit: limit,
    },
  };

  if (endIndex < count) {
    result.next = {
      page: pageNumber + 1,
      limit: limit,
    };
  } else {
    result.next = null;
  }

  if (startIndex > 0) {
    result.previous = {
      page: pageNumber - 1,
      limit: limit,
    };
  } else {
    result.previous = null;
  }

  return {
    result,
    skip,
    limit,
  };
};
