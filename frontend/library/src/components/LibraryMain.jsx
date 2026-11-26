import { useState } from "react";
import LbraryButton from "./LibraryButton";
import Header from "./Header";


export default function LibraryMain() {
    const [count, setCount] = useState(0);
    //let count =0

    function incrementNumbers() {
        setCount(count + 1);
    }
    return(
        <>
        <Header />
        <h2>WELCOME PAGE</h2>
        <LbraryButton  buttonLabel={"Increment"} buttonAction={incrementNumbers} />
        <p>the current count is {count}</p>
        </>
    )
}