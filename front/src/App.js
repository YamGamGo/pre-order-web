import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Homepage from './page/Homepage';
import PurchasePage from "./page/PurchasePage"; // 구매 페이지 임포트
import SuccessPage from "./page/SuccessPage"; // 성공 페이지 임포트
import FailPage from './page/FailPage'; // 실패 페이지 임포트

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Homepage />} />
                <Route path="/reservation/:id" element={<PurchasePage />} /> {/* 구매 페이지 경로 */}
                <Route path="/success" element={<SuccessPage />} /> {/* 성공 페이지 경로 */}
                <Route path="/fail" element={<FailPage />} /> {/* 실패 페이지 경로 */}
            </Routes>
        </Router>
    );
}

export default App;


