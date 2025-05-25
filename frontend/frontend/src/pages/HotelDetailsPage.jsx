import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const HotelDetailsPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [hotel, setHotel] = useState(null);
    const [administrators, setAdministrators] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [sortedHotels, setSortedHotels] = useState([]);

    useEffect(() => {
        fetchHotel();
        fetchAdministrators();
        fetchAllHotels();
    }, [id]);

    const fetchHotel = async () => {
        const res = await axios.get(`/hotels/${id}`);
        setHotel(res.data);
    };

    const fetchAdministrators = async () => {
        const res = await axios.get("/users/getAll");
        setAdministrators(res.data);
    };

    const fetchAllHotels = async () => {
        const res = await axios.get("/hotels/getAll/minimal");
        const sorted = res.data.sort((a, b) => a.name.localeCompare(b.name));
        setSortedHotels(sorted);
    };

    const handleHotelChange = (e) => {
        const { name, value, type, checked } = e.target;
        setHotel((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleRoomChange = (index, e) => {
        const { name, value, type, checked } = e.target;
        const updatedRooms = [...hotel.rooms];
        updatedRooms[index][name] = type === "checkbox" ? checked : value;
        setHotel((prev) => ({ ...prev, rooms: updatedRooms }));
    };

    const addRoom = () => {
        setHotel((prev) => ({
            ...prev,
            rooms: [...prev.rooms, { number: "", capacity: "", pricePerNight: "", available: true }],
        }));
    };

    const removeRoom = (index) => {
        setHotel((prev) => ({
            ...prev,
            rooms: prev.rooms.filter((_, i) => i !== index),
        }));
    };

    const updateHotelInfo = async () => {
        const { name, address, description, administratorId, rooms } = hotel;


        if (!name || !address || !description || !administratorId) {
            setErrorMessage("Svi podaci o hotelu moraju biti uneseni.");
            setSuccessMessage("");
            return;
        }


        if (rooms.length === 0) {
            setErrorMessage("Hotel mora imati barem jednu sobu.");
            setSuccessMessage("");
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
                setErrorMessage("Sva polja za sobe moraju biti popunjena valjanim brojevima.");
                setSuccessMessage("");
                return;
            }
        }


        const numbers = rooms.map((r) => Number(r.number));
        const hasDuplicate = numbers.some((num, i) => numbers.indexOf(num) !== i);
        if (hasDuplicate) {
            setErrorMessage("Dvije sobe ne mogu imati isti broj.");
            setSuccessMessage("");
            return;
        }

        try {
            await axios.put(`/hotels/${id}`, {
                name,
                address,
                description,
                verified: hotel.verified,
                administratorId: parseInt(administratorId),
                rooms: rooms.map((r) => ({
                    id: r.id,
                    number: parseInt(r.number),
                    capacity: parseInt(r.capacity),
                    pricePerNight: parseInt(r.pricePerNight),
                    available: r.available,
                })),
            });
            setErrorMessage("");
            setSuccessMessage("Podaci hotela i sobe su uspješno spremljeni.");
            fetchHotel();
        } catch (err) {
            setSuccessMessage("");
            const backendError = err.response?.data;
            setErrorMessage(
                typeof backendError === "string"
                    ? backendError
                    : "Greška kod spremanja hotela."
            );
        }
    };




    const navigateToAdjacent = (direction) => {
        const currentIndex = sortedHotels.findIndex(h => h.id === parseInt(id));
        if (currentIndex === -1) return;
        const newIndex = direction === "prev" ? currentIndex - 1 : currentIndex + 1;
        if (newIndex >= 0 && newIndex < sortedHotels.length) {
            navigate(`/hotels/${sortedHotels[newIndex].id}`);
        }
    };

    if (!hotel) return <div>Učitavanje...</div>;

    return (
        <div className="container">
            <button onClick={() => navigate("/")}>Povratak na početnu</button>
            <button onClick={() => navigateToAdjacent("prev")} disabled={sortedHotels.findIndex(h => h.id === parseInt(id)) === 0}>
                ← Prethodni
            </button>
            <button onClick={() => navigateToAdjacent("next")} disabled={sortedHotels.findIndex(h => h.id === parseInt(id)) === sortedHotels.length - 1}>
                Sljedeći →
            </button>

            <h1>Detalji hotela</h1>
            <input name="name" placeholder="Naziv" value={hotel.name} onChange={handleHotelChange} />
            <input name="address" placeholder="Adresa" value={hotel.address} onChange={handleHotelChange} />
            <input name="description" placeholder="Opis" value={hotel.description} onChange={handleHotelChange} />
            <label>
                Verificiran: <input type="checkbox" name="verified" checked={hotel.verified} onChange={handleHotelChange} />
            </label>
            <select name="administratorId" value={hotel.administratorId} onChange={handleHotelChange}>
                <option value="">Odaberi administratora</option>
                {administrators.map((admin) => (
                    <option key={admin.id} value={admin.id}>{admin.fullName || `Admin #${admin.id}`}</option>
                ))}
            </select>

            <h3>Sobe</h3>
            {hotel.rooms.map((room, index) => (
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
            {successMessage && <p style={{ color: "green" }}>{successMessage}</p>}

            <br /><br />
            <button onClick={updateHotelInfo}>Spremi podatke hotela i sobe</button>
        </div>
    );
};

export default HotelDetailsPage;
