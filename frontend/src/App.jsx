import { Routes, Route, Link } from "react-router-dom";
import StudyGroupList from "./components/StudyGroupList";
import CreateGroupForm from "./components/CreateGroupForm";
import LearningGoalForm from "./components/LearningGoalForm.jsx";

const App = () => {
    return (
        <div className="min-h-screen bg-gray-900 text-white">
            {/* Header and Navigation */}
            <header className="p-4 bg-gray-800 shadow-md">
                <h1 className="text-3xl font-bold text-center">Virtual Study Group Platform</h1>
                <nav className="flex justify-center gap-6 mt-4">
                    <Link to="/" className="text-blue-400 hover:text-blue-300 text-lg">
                        Home
                    </Link>
                    <Link to="/create" className="text-blue-400 hover:text-blue-300 text-lg">
                        Create Group
                    </Link>
                    <Link to="/learning-goal" className="text-blue-400 hover:text-blue-300 text-lg">
                        Learning Goals
                    </Link>
                </nav>
            </header>

            {/* Main Content */}
            <main className="p-4">
                <div className="max-w-screen-md mx-auto">
                    <Routes>
                        <Route path="/" element={<StudyGroupList />} />
                        <Route path="/create" element={<CreateGroupForm />} />
                        <Route path="/learning-goal" element={<LearningGoalForm onSubmit={handleAddGoal} />} />
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
