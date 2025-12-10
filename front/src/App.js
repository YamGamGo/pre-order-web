import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./page/HomePage";
import PurchasePage from "./page/PurchasePage";
import SuccessPage from "./page/SuccessPage";
import FailPage from './page/FailPage';


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/reservation/:id" element={<PurchasePage />} />
                <Route path="/success" element={<SuccessPage />} />
                <Route path="/fail" element={<FailPage />} />
            </Routes>
        </Router>
    );
}

export default App;



