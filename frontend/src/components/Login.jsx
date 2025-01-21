import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login, register } from "../api";
import PropTypes from "prop-types";

const Login = ({ onLogin }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [isRegistering, setIsRegistering] = useState(false);
    const navigate = useNavigate();

    const handleAuth = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const payload = { email, password };
            console.log("Sending payload:", payload);  // Log the payload

            let data;
            if (isRegistering) {
                data = await register(email, password);
            } else {
                data = await login(email, password);
            }

            localStorage.setItem("token", data.token);
            onLogin();
            navigate("/");
        } catch (err) {
            console.error("Authentication Error:", err.response || err.message);
            setError(err.response?.data?.message || "Authentication failed");
        }
    };


    return (
        <div className="flex justify-center items-center h-screen bg-gray-900 text-white">
            <div className="bg-gray-800 p-6 rounded-md shadow-md max-w-md w-full">
                <h2 className="text-2xl font-bold mb-4 text-center">
                    {isRegistering ? "Register" : "Login"}
                </h2>
                {error && <p className="text-red-500 text-center mb-4">{error}</p>}
                <form onSubmit={handleAuth}>
                    <div className="mb-4">
                        <label className="block text-gray-300 text-sm font-bold mb-2">Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="w-full px-4 py-2 border rounded-md bg-gray-700 text-white"
                            placeholder="Enter your email"
                            required
                        />
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-300 text-sm font-bold mb-2">Password</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="w-full px-4 py-2 border rounded-md bg-gray-700 text-white"
                            placeholder="Enter your password"
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-md"
                    >
                        {isRegistering ? "Register" : "Login"}
                    </button>
                </form>
                <p
                    className="text-center text-gray-400 mt-4 cursor-pointer"
                    onClick={() => setIsRegistering(!isRegistering)}
                >
                    {isRegistering ? "Already have an account? Login" : "Don't have an account? Register"}
                </p>
            </div>
        </div>
    );
};

Login.propTypes = {
    onLogin: PropTypes.func.isRequired,
};

export default Login;
