import { Routes, Route, Link } from "react-router-dom";
import StudyGroupList from "./components/StudyGroupList";
import CreateGroupForm from "./components/CreateGroupForm";

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
                </nav>
            </header>

            {/* Main Content */}
            <main className="p-4">
                <div className="max-w-screen-md mx-auto">
                    <Routes>
                        {/* Home Page: List of Public Study Groups */}
                        <Route path="/" element={<StudyGroupList/>}/>

                        {/* Create Group Page */}
                        <Route path="/create" element={<CreateGroupForm/>}/>
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

export default App;
