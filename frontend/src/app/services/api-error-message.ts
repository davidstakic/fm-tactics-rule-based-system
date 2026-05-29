export function extractApiErrorMessage(error: unknown, fallbackMessage: string): string {
  if (typeof error === 'object' && error !== null && 'error' in error) {
    const response = error as { error?: unknown; message?: unknown };

    if (typeof response.error === 'string' && response.error.trim()) {
      return response.error;
    }

    if (typeof response.error === 'object' && response.error !== null && 'message' in response.error) {
      const body = response.error as { message?: unknown };
      if (typeof body.message === 'string' && body.message.trim()) {
        return body.message;
      }
    }

    if (typeof response.message === 'string' && response.message.trim()) {
      return response.message;
    }
  }

  return fallbackMessage;
}
