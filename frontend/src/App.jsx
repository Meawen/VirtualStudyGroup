import { Routes, Route } from "react-router-dom";
import StudyGroupList from "./components/StudyGroupList";
import CreateGroupForm from "./components/CreateGroupForm";
import Login from "./components/Login";
import ProtectedRoute from "./components/ProtectedRoute";

const App = () => {
    return (
        <div className="min-h-screen bg-gray-900 text-white">
            <header className="p-4 bg-gray-800 shadow-md">
                <h1 className="text-3xl font-bold text-center">Virtual Study Group Platform</h1>
            </header>

            <main className="p-4">
                <Routes>
                    {/* Public Route */}
                    <Route path="/login" element={<Login />} />

                    {/* Protected Routes */}
                    <Route path="/" element={<ProtectedRoute><StudyGroupList /></ProtectedRoute>} />
                    <Route path="/create" element={<ProtectedRoute><CreateGroupForm /></ProtectedRoute>} />
                </Routes>
            </main>
        </div>
    );
};

export default App;
