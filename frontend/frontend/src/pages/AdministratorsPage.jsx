// src/pages/AdministratorsPage.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AdministratorsPage = () => {
    const [admins, setAdmins] = useState([]);
    const [newAdmin, setNewAdmin] = useState({ fullName: "", email: "", phoneNumber: "" });
    const [editingId, setEditingId] = useState(null);
    const [editAdmin, setEditAdmin] = useState({ fullName: "", email: "", phoneNumber: "" });
    const [errorMessage, setErrorMessage] = useState("");
    const [searchParams, setSearchParams] = useState({ id: "", fullName: "", email: "", phoneNumber: "" });

    const navigate = useNavigate();

    useEffect(() => {
        fetchAdmins();
    }, []);

    const fetchAdmins = async () => {
        const query = new URLSearchParams();
        Object.entries(searchParams).forEach(([key, val]) => {
            if (val) query.append(key, val);
        });
        const res = await axios.get(`/users/search?${query.toString()}`);
        const sorted = res.data.sort((a, b) => a.fullName.localeCompare(b.fullName));
        setAdmins(sorted);
    };

    const handleNewChange = (e) => {
        const { name, value } = e.target;
        setNewAdmin((prev) => ({ ...prev, [name]: value }));
    };

    const handleEditChange = (e) => {
        const { name, value } = e.target;
        setEditAdmin((prev) => ({ ...prev, [name]: value }));
    };

    const handleSearchChange = (e) => {
        const { name, value } = e.target;
        setSearchParams((prev) => ({ ...prev, [name]: value }));
    };

    const addAdmin = async () => {
        try {
            await axios.post("/users", newAdmin);
            fetchAdmins();
            setNewAdmin({ fullName: "", email: "", phoneNumber: "" });
            setErrorMessage("");
        } catch (err) {
            setErrorMessage(err.response?.data || "Greška prilikom dodavanja administratora.");
        }
    };

    const deleteAdmin = async (id) => {
        await axios.delete(`/users/${id}`);
        fetchAdmins();
    };

    const startEdit = (admin) => {
        setEditingId(admin.id);
        setEditAdmin({ fullName: admin.fullName, email: admin.email, phoneNumber: admin.phoneNumber });
    };

    const saveEdit = async (id) => {
        try {
            await axios.put(`/users/${id}`, editAdmin);
            fetchAdmins();
            setEditingId(null);
            setErrorMessage("");
        } catch (err) {
            setErrorMessage(err.response?.data || "Greška prilikom spremanja promjena.");
        }
    };

    return (
        <div className="container">
            <div style={{ display: "flex", gap: "1rem", marginBottom: "1rem" }}>
                <button onClick={() => navigate("/")}>Hoteli</button>
                <button onClick={() => navigate("/administrators")}>Administratori</button>
            </div>

            <h1>Administratori</h1>

            <div style={{ marginBottom: "1rem" }}>
                <input name="id" placeholder="ID" value={searchParams.id} onChange={handleSearchChange} />
                <input name="fullName" placeholder="Ime i prezime" value={searchParams.fullName} onChange={handleSearchChange} />
                <input name="email" placeholder="Email" value={searchParams.email} onChange={handleSearchChange} />
                <input name="phoneNumber" placeholder="Telefon" value={searchParams.phoneNumber} onChange={handleSearchChange} />
                <button onClick={fetchAdmins}>Pretraži</button>
            </div>

            <h2>Popis</h2>
            {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
            <table>
                <thead>
                <tr>
                    <th>Ime i prezime</th>
                    <th>Email</th>
                    <th>Telefon</th>
                    <th>Akcije</th>
                </tr>
                </thead>
                <tbody>
                {admins.map((admin) => (
                    <tr key={admin.id}>
                        {editingId === admin.id ? (
                            <>
                                <td><input name="fullName" value={editAdmin.fullName} onChange={handleEditChange} /></td>
                                <td><input name="email" value={editAdmin.email} onChange={handleEditChange} /></td>
                                <td><input name="phoneNumber" value={editAdmin.phoneNumber} onChange={handleEditChange} /></td>
                                <td>
                                    <button onClick={() => saveEdit(admin.id)}>Spremi</button>
                                    <button onClick={() => setEditingId(null)}>Odustani</button>
                                </td>
                            </>
                        ) : (
                            <>
                                <td>{admin.fullName}</td>
                                <td>{admin.email}</td>
                                <td>{admin.phoneNumber}</td>
                                <td>
                                    <button onClick={() => startEdit(admin)}>Uredi</button>
                                    <button onClick={() => deleteAdmin(admin.id)}>Obriši</button>
                                </td>
                            </>
                        )}
                    </tr>
                ))}
                </tbody>
            </table>

            <h2>Dodaj novog administratora</h2>
            <input name="fullName" placeholder="Ime i prezime" value={newAdmin.fullName} onChange={handleNewChange} />
            <input name="email" placeholder="Email" value={newAdmin.email} onChange={handleNewChange} />
            <input name="phoneNumber" placeholder="Broj telefona" value={newAdmin.phoneNumber} onChange={handleNewChange} />
            <button onClick={addAdmin}>Dodaj</button>
        </div>
    );
};

export default AdministratorsPage;
