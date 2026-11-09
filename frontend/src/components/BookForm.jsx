import { useState } from 'react';

const EMPTY = { title: '', author: '', isbn: '' };

export default function BookForm({ onSubmit }) {
  const [book, setBook] = useState(EMPTY);

  const handleChange = (field) => (e) => setBook({ ...book, [field]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    const saved = await onSubmit(book);
    if (saved) setBook(EMPTY);
  };

  return (
    <form onSubmit={handleSubmit} className="card">
      <h2>Registrar libro</h2>
      <input placeholder="Título" value={book.title} onChange={handleChange('title')} required />
      <input placeholder="Autor" value={book.author} onChange={handleChange('author')} required />
      <input placeholder="ISBN" value={book.isbn} onChange={handleChange('isbn')} required />
      <button type="submit">Guardar</button>
    </form>
  );
}
