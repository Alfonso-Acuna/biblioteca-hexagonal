import BookForm from './components/BookForm.jsx';
import BookList from './components/BookList.jsx';
import LoanList from './components/LoanList.jsx';
import { useLibrary } from './application/useLibrary.js';

export default function App() {
  const { books, loans, error, registerBook, lendBook, returnBook } = useLibrary();

  return (
    <main>
      <h1>📚 BiblioHex</h1>
      {error && <p className="error">{error}</p>}
      <BookForm onSubmit={registerBook} />
      <BookList books={books} onLend={lendBook} />
      <LoanList loans={loans} onReturn={returnBook} />
    </main>
  );
}
