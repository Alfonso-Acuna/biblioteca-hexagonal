export default function BookList({ books, onLend }) {
  const askMemberAndLend = (bookId) => {
    const member = window.prompt('Nombre del socio');
    if (member) onLend(bookId, member);
  };

  return (
    <section className="card">
      <h2>Libros</h2>
      <ul>
        {books.map((book) => (
          <li key={book.id}>
            <span>{book.title} — {book.author}</span>
            {book.available
              ? <button onClick={() => askMemberAndLend(book.id)}>Prestar</button>
              : <em>Prestado</em>}
          </li>
        ))}
      </ul>
    </section>
  );
}
