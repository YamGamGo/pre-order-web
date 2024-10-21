import { Link } from "react-router-dom"; // Link 사용
import poo from './image/poo.jpg'; // 이미지 파일을 import

function Homepage() {
    return (
        <div>
            <Link to="/information">
                <img src={poo} alt="Poo" style={{ width: '200px', height: '200px' }} />
            </Link>
            <p>상품명: 푸바오</p> {/* 이미지 아래에 상품명을 추가 */}
        </div>
    );
}

export default Homepage;

