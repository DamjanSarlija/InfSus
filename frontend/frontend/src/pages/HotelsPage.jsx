// src/pages/HotelsPage.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

const HotelsPage = () => {
    const [hotels, setHotels] = useState([]);
    const [searchParams, setSearchParams] = useState({
        id: "",
        name: "",
        address: "",
        administratorId: "",
    });
    const [showAddForm, setShowAddForm] = useState(false);
    const [newHotel, setNewHotel] = useState({
        name: "",
        address: "",
        description: "",
        verified: false,
        administratorId: "",
        rooms: [],
    });
    const [administrators, setAdministrators] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        fetchHotels();
        fetchAdministrators();
    }, []);

    const fetchHotels = async () => {
        const res = await axios.get("/hotels/getAll/minimal");
        const sortedHotels = res.data.sort((a, b) => a.name.localeCompare(b.name));
        setHotels(sortedHotels);
    };

    const fetchAdministrators = async () => {
        const res = await axios.get("/users/getAll");
        setAdministrators(res.data);
    };

    const deleteHotel = async (id) => {
        await axios.delete(`/hotels/${id}`);
        fetchHotels();
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setSearchParams((prev) => ({ ...prev, [name]: value }));
    };

    const searchHotels = async () => {
        const query = new URLSearchParams();
        Object.entries(searchParams).forEach(([key, val]) => {
            if (val) query.append(key, val);
        });
        const res = await axios.get(`/hotels/search/minimal?${query.toString()}`);
        const sortedResults = res.data.sort((a, b) => a.name.localeCompare(b.name));
        setHotels(sortedResults);
    };

    const handleNewHotelChange = (e) => {
        const { name, value, type, checked } = e.target;
        setNewHotel((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleRoomChange = (index, e) => {
        const { name, value, type, checked } = e.target;
        const updatedRooms = [...newHotel.rooms];
        updatedRooms[index][name] = type === "checkbox" ? checked : value;
        setNewHotel((prev) => ({ ...prev, rooms: updatedRooms }));
    };

    const addRoom = () => {
        setNewHotel((prev) => ({
            ...prev,
            rooms: [...prev.rooms, { number: "", capacity: "", pricePerNight: "", available: true }],
        }));
    };

    const removeRoom = (index) => {
        setNewHotel((prev) => ({
            ...prev,
            rooms: prev.rooms.filter((_, i) => i !== index),
        }));
    };

    const submitNewHotel = async () => {
        const { name, address, description, administratorId, rooms } = newHotel;

        // Validacija općih podataka
        if (!name || !address || !description || !administratorId) {
            setErrorMessage("Svi podaci o hotelu moraju biti uneseni.");
            return;
        }

        // Validacija soba
        if (rooms.length === 0) {
            setErrorMessage("Hotel mora imati barem jednu sobu.");
            return;
        }

        for (const room of rooms) {
            if (
                room.number === "" ||
                room.capacity === "" ||
                room.pricePerNight === "" ||
                isNaN(room.number) ||
                isNaN(room.capacity) ||
                isNaN(room.pricePerNight)
            ) {
                setErrorMessage("Sva polja za sobe moraju biti ispravno popunjena brojevima.");
                return;
            }
        }

        const numbers = rooms.map((r) => Number(r.number));
        const hasDuplicate = numbers.some((num, i) => numbers.indexOf(num) !== i);
        if (hasDuplicate) {
            setErrorMessage("Dvije sobe ne mogu imati isti broj.");
            return;
        }

        // Ako je sve validno, šalji podatke
        try {
            await axios.post("/hotels", {
                ...newHotel,
                administratorId: parseInt(administratorId),
                rooms: rooms.map((r) => ({
                    ...r,
                    number: parseInt(r.number),
                    capacity: parseInt(r.capacity),
                    pricePerNight: parseInt(r.pricePerNight),
                })),
            });
            setShowAddForm(false);
            fetchHotels();
            setNewHotel({ name: "", address: "", description: "", verified: false, administratorId: "", rooms: [] });
            setErrorMessage("");
        } catch (err) {
            setErrorMessage(err.response?.data || "Greška kod spremanja hotela.");
        }
    };


    return (
        <div className="container">
            <div style={{display: "flex", gap: "1rem", marginBottom: "1rem"}}>
                <button onClick={() => navigate("/")}>Hoteli</button>
                <button onClick={() => navigate("/administrators")}>Administratori</button>
            </div>

            <h1>Hoteli</h1>

            <div style={{marginBottom: "1rem"}}>
                <input name="id" placeholder="ID hotela" value={searchParams.id} onChange={handleInputChange}/>
                <input name="name" placeholder="Naziv hotela" value={searchParams.name} onChange={handleInputChange} />
                <input name="address" placeholder="Adresa hotela" value={searchParams.address} onChange={handleInputChange} />
                <input name="administratorId" placeholder="ID administratora" value={searchParams.administratorId} onChange={handleInputChange} />
                <button onClick={searchHotels}>Pretraži</button>
            </div>

            <table>
                <thead>
                <tr>
                    <th>ID</th><th>Naziv</th><th>Adresa</th><th>Akcije</th>
                </tr>
                </thead>
                <tbody>
                {hotels.map((hotel) => (
                    <tr key={hotel.id}>
                        <td>{hotel.id}</td>
                        <td>{hotel.name}</td>
                        <td>{hotel.address}</td>
                        <td>
                            <button onClick={() => navigate(`/hotels/${hotel.id}`)}>Detalji</button>
                            <button onClick={() => deleteHotel(hotel.id)}>Obriši</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            <button onClick={() => setShowAddForm(!showAddForm)}>{showAddForm ? "Zatvori" : "Dodaj novi hotel"}</button>

            {showAddForm && (
                <div>
                    <h2>Novi Hotel</h2>
                    <input name="name" placeholder="Naziv" value={newHotel.name} onChange={handleNewHotelChange} />
                    <input name="address" placeholder="Adresa" value={newHotel.address} onChange={handleNewHotelChange} />
                    <input name="description" placeholder="Opis" value={newHotel.description} onChange={handleNewHotelChange} />
                    <label>
                        Verificiran: <input type="checkbox" name="verified" checked={newHotel.verified} onChange={handleNewHotelChange} />
                    </label>
                    <select name="administratorId" value={newHotel.administratorId} onChange={handleNewHotelChange}>
                        <option value="">Odaberi administratora</option>
                        {administrators.map((admin) => (
                            <option key={admin.id} value={admin.id}>{admin.fullName || `Admin #${admin.id}`}</option>
                        ))}
                    </select>

                    <h3>Sobe</h3>
                    {newHotel.rooms.map((room, index) => (
                        <div key={index}>
                            <input name="number" placeholder="Broj sobe" value={room.number} onChange={(e) => handleRoomChange(index, e)} />
                            <input name="capacity" placeholder="Kapacitet" value={room.capacity} onChange={(e) => handleRoomChange(index, e)} />
                            <input name="pricePerNight" placeholder="Cijena po noći" value={room.pricePerNight} onChange={(e) => handleRoomChange(index, e)} />
                            <label>
                                Dostupna: <input type="checkbox" name="available" checked={room.available} onChange={(e) => handleRoomChange(index, e)} />
                            </label>
                            <button onClick={() => removeRoom(index)}>Ukloni</button>
                        </div>
                    ))}

                    <button onClick={addRoom}>Dodaj sobu</button>
                    {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
                    <br /><br />
                    <button onClick={submitNewHotel}>Spremi hotel</button>
                </div>
            )}
        </div>
    );
};

export default HotelsPage;
