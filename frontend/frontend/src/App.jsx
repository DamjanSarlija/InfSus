
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HotelsPage from './pages/HotelsPage';
import HotelDetailsPage from './pages/HotelDetailsPage';
import AdministratorsPage from './pages/AdministratorsPage';
import AdministratorDetailsPage from "./pages/AdministratorDetailsPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HotelsPage />} />
                <Route path="/hotels/:id" element={<HotelDetailsPage />} />
                <Route path="/administrators" element={<AdministratorsPage />} />
                <Route path="/administrators/:id" element={<AdministratorDetailsPage />} />
            </Routes>
        </Router>
    );
}

export default App;
