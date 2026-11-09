import { useCallback, useEffect, useState } from 'react';
import { libraryApi } from '../infrastructure/libraryApi.js';

// Casos de uso del frontend: la UI llama acciones, no sabe nada de HTTP.
export function useLibrary(api = libraryApi) {
  const [books, setBooks] = useState([]);
  const [loans, setLoans] = useState([]);
  const [error, setError] = useState('');

  const refresh = useCallback(async () => {
    const [bookList, loanList] = await Promise.all([api.listBooks(), api.listLoans()]);
    setBooks(bookList);
    setLoans(loanList);
  }, [api]);

  const run = useCallback(async (action) => {
    try {
      setError('');
      await action();
      await refresh();
      return true;
    } catch (e) {
      setError(e.message);
      return false;
    }
  }, [refresh]);

  useEffect(() => { run(() => Promise.resolve()); }, [run]);

  return {
    books,
    loans,
    error,
    registerBook: (book) => run(() => api.registerBook(book)),
    lendBook: (bookId, member) => run(() => api.lendBook(bookId, member)),
    returnBook: (loanId) => run(() => api.returnBook(loanId)),
  };
}
