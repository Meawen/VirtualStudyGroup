import  { useState } from "react";
import { createGroup } from "../api";

const CreateGroupForm = () => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        const groupData = { name, description, visibility: "PUBLIC" };

        try {
            await createGroup(groupData);
            alert("Group created successfully!");
            setName("");
            setDescription("");
        } catch (error) {
            console.error("Error creating group:", error);
            alert("Failed to create group.");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="max-w-lg mx-auto bg-gray-800 p-6 rounded-md shadow-md">
            <h2 className="text-2xl font-bold mb-4 text-white">Create a Study Group</h2>
            <input
                type="text"
                placeholder="Group Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full p-2 mb-4 rounded bg-gray-700 border border-gray-600 text-white"
                required
            />
            <textarea
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="w-full p-2 mb-4 rounded bg-gray-700 border border-gray-600 text-white"
                required
            ></textarea>
            <button
                type="submit"
                className="bg-blue-500 hover:bg-blue-400 text-white py-2 px-4 rounded"
            >
                Create Group
            </button>
        </form>
    );
};

export default CreateGroupForm;
