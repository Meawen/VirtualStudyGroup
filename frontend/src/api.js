import axios from "axios";

const API_URL = "http://localhost:8080/api";

export const getPublicGroups = async () => {
    return axios.get(`${API_URL}/study-groups/public`);
};
export const createGroup = async (groupData) => {
    return axios.post(`${API_URL}/study-groups`, groupData);
};