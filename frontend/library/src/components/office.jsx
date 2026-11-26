import { useState } from "react";
import LibraryButton from "./LibraryButton";
import axios from "axios";


export default function Office() {
    const [officeName, setOfficeName] = useState("");
    const [officeNumber, setOfficeNumber] = useState(0);
    const [deleteOfficeNumber, setDeleteOfficeNumber] = useState("");

    async function saveOffice(){
        const response = await axios.post("http://localhost:8082/api/offices/save",{
            name:officeName,
            officeNumber:officeNumber
        });
        if(response.status == 200){
            alert("Save successfully");
        } else {
            alert("Office is not saved");
        }
    }

    async function deleteOffice() {
        const lookupResponse = await axios.get(
            `http://localhost:8082/api/offices/room/${deleteOfficeNumber}`
        );
        const response = await axios.delete(
            `http://localhost:8082/api/offices/delete/${lookupResponse.data.id}`
        );

        if (response.status === 200) {
            alert("Office deleted successfully");
            setDeleteOfficeNumber("");
        } else {
            alert("Office is not deleted");
        }
    }

    return (
        <div>
            <Header />
                <span>Save Office</span>
                <div>
                    <span>Office Name: </span>
                    <input type="text" onChange={e=>setOfficeName(e.target.value)}  className="p-0.5 rounded-md px-2 border border-black" />
                </div>
                <div>
                    <span>Office Number </span>
                    <input type="number" onChange={e=>setOfficeNumber(e.target.value)} className="p-0.5 rounded-md px-2 border border-black" />
                </div>
                <LibraryButton buttonLabel={'SAVE'} buttonAction={saveOffice} />

                <div>
                    <span>Delete Office</span>
                    <div>
                        <span>Office Number </span>
                        <input
                            type="number"
                            value={deleteOfficeNumber}
                            onChange={e => setDeleteOfficeNumber(e.target.value)}
                        />
                    </div>
                    <LibraryButton buttonLabel={'DELETE'} buttonAction={deleteOffice} />
                </div>
            </div>
    )
}