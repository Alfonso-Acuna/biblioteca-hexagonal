// Adaptador de salida del frontend: único lugar que conoce HTTP y las URLs del backend.
async function request(path, options = {}) {
  const response = await fetch(path, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });
  const body = await response.json().catch(() => null);
  if (!response.ok) {
    throw new Error(body?.message ?? 'Error inesperado');
  }
  return body;
}

export const libraryApi = {
  listBooks: () => request('/api/books'),
  registerBook: (book) => request('/api/books', { method: 'POST', body: JSON.stringify(book) }),
  listLoans: () => request('/api/loans'),
  lendBook: (bookId, memberName) =>
    request('/api/loans', { method: 'POST', body: JSON.stringify({ bookId, memberName }) }),
  returnBook: (loanId) => request(`/api/loans/${loanId}/return`, { method: 'PUT' }),
};
