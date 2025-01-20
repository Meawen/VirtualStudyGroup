import { Routes, Route, Link } from "react-router-dom";
import StudyGroupList from "./components/StudyGroupList";
import CreateGroupForm from "./components/CreateGroupForm";
import LearningGoalForm from "./components/LearningGoalForm.jsx";
import Login from "./components/Login.jsx";
import { getUsernameFromToken } from "./utils/auth";
import {useEffect, useState} from "react";

const App = () => {
    const [username, setUsername] = useState(getUsernameFromToken());

    useEffect(() => {
        const handleStorageChange = () => {
            setUsername(getUsernameFromToken());
        };

        window.addEventListener("storage", handleStorageChange);

        return () => {
            window.removeEventListener("storage", handleStorageChange);
        };
    }, []);
    const handleLoginSuccess = () => {
        setUsername(getUsernameFromToken());  // Update the username state
    };
    const handleLogout = () => {
        localStorage.removeItem("token");
        setUsername(null);
        window.location.href = "/login";  // Redirect to login page
    };

    return (
        <div className="min-h-screen bg-gray-900 text-white">
            {/* Header */}
            <header className="p-4 bg-gray-800 shadow-md flex justify-between items-center">
                <h1 className="text-3xl font-bold">Virtual Study Group Platform</h1>
                <nav className="flex gap-6">
                    <Link to="/" className="text-blue-400 hover:text-blue-300 text-lg">Home</Link>
                    <Link to="/create" className="text-blue-400 hover:text-blue-300 text-lg">Create Group</Link>
                    <Link to="/learning-goal" className="text-blue-400 hover:text-blue-300 text-lg">Learning Goals</Link>
                </nav>
                {username ? (
                    <div className="flex items-center gap-4">
                        <span className="text-lg text-green-400">Welcome, {username}</span>
                        <button
                            onClick={handleLogout}
                            className="bg-red-500 px-4 py-2 rounded-lg text-white"
                        >
                            Logout
                        </button>
                    </div>
                ) : (
                    <Link to="/login" className="text-blue-400 hover:text-blue-300 text-lg">Login</Link>
                )}
            </header>

            {/* Main Content */}
            <main className="p-4">
                <div className="max-w-screen-md mx-auto">
                    <Routes>
                        <Route path="/" element={<StudyGroupList />} />
                        <Route path="/create" element={<CreateGroupForm />} />
                        <Route path="/learning-goal" element={<LearningGoalForm onSubmit={handleAddGoal}/>} />
                        <Route path="/login" element={<Login onLogin={handleLoginSuccess} />} />
                    </Routes>
                </div>
            </main>

            {/* Footer */}
            <footer className="p-4 bg-gray-800 text-center text-gray-400">
                © {new Date().getFullYear()} Virtual Study Group Platform
            </footer>
        </div>
    );
};

const handleAddGoal = (goal) => {
    console.log("New Learning Goal:", goal);
    // Ovdje logika za spremanje cilja putem API-ja !!!!!!
};

export default App;
