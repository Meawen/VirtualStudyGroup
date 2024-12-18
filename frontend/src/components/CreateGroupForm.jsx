import  {useState} from "react";
import { createGroup } from "../api.js";

const CreateGroupForm = () => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        const groupData = {
            name,
            description,
            visibility: "PUBLIC", // Set the visibility to PUBLIC by default
        };

        console.log("Request payload:", groupData);

        try {
            const response = await createGroup(groupData);
            console.log("Group created:", response.data);
            alert("Group created successfully!");
            setName("");
            setDescription("");
        } catch (error) {
            console.error("Error creating group:", error.response?.data || error.message);
            alert("Failed to create group. Check the console for details.");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Create a Study Group</h2>
            <input
                type="text"
                placeholder="Group Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />
            <input
                type="text"
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                required
            />
            <button type="submit">Create Group</button>
        </form>
    );
};

export default CreateGroupForm;

