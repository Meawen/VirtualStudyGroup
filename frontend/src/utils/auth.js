export const getUsernameFromToken = () => {
    const token = localStorage.getItem("token");
    if (!token) return null;

    try {
        const base64Url = token.split(".")[1];
        const decoded = JSON.parse(atob(base64Url));
        return decoded.sub;
    } catch (error) {
        console.error("Error decoding token:", error);
        return null;
    }
};