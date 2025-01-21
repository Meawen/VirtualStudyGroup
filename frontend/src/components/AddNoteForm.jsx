import React, { useState } from "react";

const AddNoteForm = () => {
    const [content, setContent] = useState("");
    const [groupId, setGroupId] = useState("");
    const [authorId, setAuthorId] = useState("");


    const handleSubmit = async (e) => {
        e.preventDefault();




    };

    return (
        <div className="max-w-md mx-auto bg-gray-800 p-6 rounded-lg shadow-md">
            <h2 className="text-2xl font-bold mb-4 text-center">Add a New Note</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label htmlFor="content" className="block text-sm font-medium mb-1">
                        Note Content:
                    </label>
                    <textarea
                        id="content"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                        className="w-full p-2 bg-gray-700 text-white rounded-lg"
                    />
                </div>
                <div>
                    <label htmlFor="groupId" className="block text-sm font-medium mb-1">
                        Group ID:
                    </label>
                    <input
                        id="groupId"
                        type="number"
                        value={groupId}
                        onChange={(e) => setGroupId(e.target.value)}
                        required
                        className="w-full p-2 bg-gray-700 text-white rounded-lg"
                    />
                </div>
                <div>
                    <label htmlFor="authorId" className="block text-sm font-medium mb-1">
                        Author ID:
                    </label>
                    <input
                        id="authorId"
                        type="number"
                        value={authorId}
                        onChange={(e) => setAuthorId(e.target.value)}
                        required
                        className="w-full p-2 bg-gray-700 text-white rounded-lg"
                    />
                </div>
                <button
                    type="submit"
                    className="w-full py-2 px-4 bg-blue-600 hover:bg-blue-500 text-white font-bold rounded-lg"
                >
                    Add Note
                </button>
            </form>
        </div>
    );
};

export default AddNoteForm;
