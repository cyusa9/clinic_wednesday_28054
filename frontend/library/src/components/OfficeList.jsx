import { useEffect, useState } from "react";
import axios from "axios";
import LibraryButton from "./LibraryButton";

export default function OfficeList() {
    const [offices, setOffices] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        async function loadOffices() {
            const response = await axios.get("http://localhost:8082/api/offices");
            if (response.status === 200 && Array.isArray(response.data)) {
                setOffices(response.data);
            } else {
                setOffices([]);
            }
        }
        loadOffices();
    }, []);

    async function deleteOffice(id) {
        const response = await axios.delete(
            `http://localhost:8082/api/offices/delete/${id}`
        );
        if (response.status === 200) {
            alert("Office deleted successfully");
            setOffices(offices.filter((office) => office.id !== id));
        } else {
            alert("Office not found");
        }
    }

    const query = searchTerm.trim().toLowerCase();
    const filteredOffices = offices.filter((office) => {
        if (!query) return true;
        const nameMatch = (office.name || "").toLowerCase().includes(query);
        const numberMatch = String(office.officeNumber).includes(query);
        return nameMatch || numberMatch;
    });

    return (
        <div style={{ border: "3px solid indigo", marginTop: "40px", padding: "16px" }}>
            <div>
                <h1>All Available Offices</h1>
            </div>

            <div>
                <label>Search: </label>
                <input
                    type="text"
                    placeholder="Search by office name or number"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </div>

            <table border="1" cellPadding="8" style={{ marginTop: "12px", borderCollapse: "collapse" }}>
                <thead>
                    <tr>
                        <th>Office Name</th>
                        <th>Office Number</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredOffices.length > 0 ? (
                        filteredOffices.map((office) => (
                            <tr key={office.id}>
                                <td>{office.name}</td>
                                <td>{office.officeNumber}</td>
                                <td style={{ display: "flex", gap: "8px" }}>
                                    <LibraryButton
                                        buttonLabel="Delete"
                                        buttonAction={() => deleteOffice(office.id)}
                                    />
                                    <LibraryButton
                                        buttonLabel="Update"
                                        buttonAction={() => {}}
                                    />
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="3">No offices found.</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}
