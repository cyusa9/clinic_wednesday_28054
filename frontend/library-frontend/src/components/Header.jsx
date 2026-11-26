
 export default function Header() {
  const books=[{
    code: 'B001',
    title: 'The Great Gatsby',
    price: 374584
  },{
    code: 'B002',
    title: 'The Great Gatsby',
    price: 20000
  },{
    code: 'B003',
    title: 'The Great Gatsby',
    price: 23948
  }
];
  return (
    <header>
      <h1>{libraryName}</h1>
      <div>
        <ul >
            { books.map(book=> 
                <li key={book.code}>{book.code} {book.title} {book.price}</li> )
              }
        </ul> 
        <span>the total pricr for {books.length} books is {books.reduce((acc, book) => acc + book.price, 0)} $</span>
       </div>
    </header>
  )
}