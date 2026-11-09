export default function LoanList({ loans, onReturn }) {
  return (
    <section className="card">
      <h2>Préstamos</h2>
      <ul>
        {loans.map((loan) => (
          <li key={loan.id}>
            <span>Libro #{loan.bookId} · {loan.memberName} · vence {loan.dueDate}</span>
            {loan.returned
              ? <em>Devuelto</em>
              : <button onClick={() => onReturn(loan.id)}>Devolver</button>}
          </li>
        ))}
      </ul>
    </section>
  );
}
