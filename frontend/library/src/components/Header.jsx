import { Link } from "react-router";

export default function Header(){
    const library = "Library Management system";
    const books = [
        {
            code: "SENG01",
            name: "Software Engineering",
            price: 10000
        },{
            code: "SENG02",
            name: "Networking administration",
            price: 10000
        },{
            code: "SENG03",
            name: "Master finance",
            price: 20000
        }
    ];
    return (
        // <header>
        //     <h2 class="text-2xl font-extrabold uppercase">{library}</h2>
        //     <div>
        //         <p>This is the Library management for student</p>
        //     </div>
        //     <div>
        //         <ul class="list-disc">
        //             { books.map(book=> 
        //                 <li key={book.code}>{book.code} {book.name} {book.price}</li> )
        //             }
        //         </ul>
        //     </div>
        //     <div>
        //           <span>the total pricr for {books.length} books is {books.reduce((acc, book) => acc + book.price, 0)} $</span>
        //     </div>
        // </header>

        <nav style={{ display: "flex", gap: "12px" }}>
            <Link to="/">Login</Link>
            <Link to="/office">Office</Link>
            <Link to="/offices">All Offices</Link>
            <Link to="/main">Main</Link>
        </nav>
    );
}