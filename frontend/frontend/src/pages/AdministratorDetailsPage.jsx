import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const AdministratorDetailsPage = () => {
    const { id } = useParams();
    const [admin, setAdmin] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchAdmin = async () => {
            try {
                const res = await axios.get(`/users/${id}`);
                setAdmin(res.data);
            } catch (err) {
                console.error("Greška pri dohvaćanju administratora:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchAdmin();
    }, [id]);

    if (loading) return <p>Učitavanje...</p>;
    if (!admin) return <p>Administrator nije pronađen.</p>;

    return (
        <div className="container">
            <button onClick={() => navigate("/administrators")}>← Natrag</button>
            <h1>Detalji administratora</h1>
            <p><strong>Ime:</strong> {admin.fullName}</p>
            <p><strong>Email:</strong> {admin.email}</p>
            <p><strong>Telefon:</strong> {admin.phoneNumber}</p>

            <h2>Hoteli</h2>
            {admin.hotels.length === 0 ? (
                <p>Nema hotela.</p>
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Naziv</th>
                        <th>Adresa</th>
                        <th>Opis</th>
                        <th>Broj soba</th>
                    </tr>
                    </thead>
                    <tbody>
                    {admin.hotels.map((hotel) => (
                        <tr key={hotel.id}>
                            <td>{hotel.id}</td>
                            <td>{hotel.name}</td>
                            <td>{hotel.address}</td>
                            <td>{hotel.description}</td>
                            <td>{hotel.rooms.length}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default AdministratorDetailsPage;
