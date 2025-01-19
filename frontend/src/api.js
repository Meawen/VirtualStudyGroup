import axios from "axios";

const API_URL = "http://localhost:8080/api";

const apiInstance = axios.create({
    baseURL: API_URL,
});

apiInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Function to fetch public groups
export const getPublicGroups = async () => {
    try {
        const response = await apiInstance.get("/study-groups/public");
        return response.data;
    } catch (error) {
        throw new Error("Failed to fetch public groups");
    }
};

// Function to create a new study group
export const createGroup = async (groupData) => {
    try {
        const response = await apiInstance.post("/study-groups", groupData);
        return response.data;
    } catch (error) {
        throw new Error("Failed to create study group");
    }
};

// Function to create a new goal
export const createGoal = async (goalData) => {
    try {
        const response = await apiInstance.post("/goals", goalData);
        return response.data;
    } catch (error) {
        throw new Error("Failed to create goal");
    }
};

// Function to log in a user
export const login = async (email, password) => {
    try {
        const response = await apiInstance.post("/auth/login", { email, password });
        return response.data;
    } catch (error) {
        throw new Error("Login failed");
    }
};

