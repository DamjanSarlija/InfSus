// src/App.jsx
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HotelsPage from './pages/HotelsPage';
import HotelDetailsPage from './pages/HotelDetailsPage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HotelsPage />} />
                <Route path="/hotels/:id" element={<HotelDetailsPage />} />
            </Routes>
        </Router>
    );
}

export default App;
