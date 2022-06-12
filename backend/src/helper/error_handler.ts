class HttpError {
  constructor(public status: number, public message: string) {
    this.status = status;
    this.message = message;
  }

  toJSON() {
    return {
      status: this.status,
      message: this.message,
    };
  }

  static badRequest(message: string) {
    return new HttpError(400, message);
  }

  static unauthorized(message: string) {
    return new HttpError(401, message);
  }

  static notFound(message: string) {
    return new HttpError(404, message);
  }

  static internalServerError(message: string) {
    return new HttpError(500, message);
  }

  static unporcessableEntity(message: string) {
    return new HttpError(422, message);
  }
}

export default HttpError;
