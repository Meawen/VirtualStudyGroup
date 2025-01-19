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


export const getPublicGroups = async () => {
    return apiInstance.get(`/study-groups/public`);
};

export const createGroup = async (groupData) => {
    return apiInstance.post(`/study-groups`, groupData);
};

export const login = async (email, password) => {
    const response = await apiInstance.post(`/auth/login`, { email, password });
    return response.data;
};

