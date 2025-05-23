// src/App.jsx
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HotelsPage from './pages/HotelsPage';
import HotelDetailsPage from './pages/HotelDetailsPage';
import AdministratorsPage from './pages/AdministratorsPage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HotelsPage />} />
                <Route path="/hotels/:id" element={<HotelDetailsPage />} />
                <Route path="/administrators" element={<AdministratorsPage />} />
            </Routes>
        </Router>
    );
}

export default App;
