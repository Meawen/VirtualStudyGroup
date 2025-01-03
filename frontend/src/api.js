import axios from "axios";

const API_URL = "http://localhost:8080/api";

export const getPublicGroups = async () => {
    return axios.get(`${API_URL}/study-groups/public`);
};
export const createGroup = async (groupData) => {
    return axios.post(`${API_URL}/study-groups`, groupData);
};

export const createGoal = async (goalData) => {
    const response = await fetch(`${API_URL}/goals`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(goalData),
    });
    if (!response.ok) {
        throw new Error("Failed to create goal");
    }
    return await response.json();
};
