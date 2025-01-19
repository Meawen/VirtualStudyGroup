import React, { useState } from "react";

const LearningGoalForm = ({ onSubmit }) => {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [status, setStatus] = useState("TO DO");
    const [deadline, setDeadline] = useState(""); // Novo polje za vremenski rok

    const handleSubmit = (e) => {
        e.preventDefault();
        const newGoal = {
            title,
            description,
            status,
            deadline,
        };
        onSubmit(newGoal);
        setTitle("");
        setDescription("");
        setStatus("TO DO");
        setDeadline("");
    };

    return (
        <form onSubmit={handleSubmit} className="learning-goal-form bg-gray-800 p-4 rounded shadow-md">
            <h2 className="text-2xl font-bold text-white mb-4">Define Learning Goal</h2>

            <div className="mb-4">
                <label htmlFor="title" className="block text-white font-bold mb-2">Title</label>
                <input
                    type="text"
                    id="title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    className="w-full p-2 border border-gray-600 rounded bg-gray-700 text-white"
                    placeholder="Enter goal title"
                    required
                />
            </div>

            <div className="mb-4">
                <label htmlFor="description" className="block text-white font-bold mb-2">Description</label>
                <textarea
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    className="w-full p-2 border border-gray-600 rounded bg-gray-700 text-white"
                    placeholder="Enter goal description"
                ></textarea>
            </div>

            <div className="mb-4">
                <label htmlFor="status" className="block text-white font-bold mb-2">Status</label>
                <select
                    id="status"
                    value={status}
                    onChange={(e) => setStatus(e.target.value)}
                    className="w-full p-2 border border-gray-600 rounded bg-gray-700 text-white"
                >
                    <option value="TO DO">TO DO</option>
                    <option value="IN PROGRESS">IN PROGRESS</option>
                    <option value="DONE">DONE</option>
                </select>
            </div>

            <div className="mb-4">
                <label htmlFor="deadline" className="block text-white font-bold mb-2">Deadline</label>
                <input
                    type="datetime-local" // HTML5 input za odabir datuma i vremena
                    id="deadline"
                    value={deadline}
                    onChange={(e) => setDeadline(e.target.value)}
                    className="w-full p-2 border border-gray-600 rounded bg-gray-700 text-white"
                />
            </div>

            <button
                type="submit"
                className="w-full p-2 bg-blue-500 text-white font-bold rounded hover:bg-blue-600"
            >
                Add Goal
            </button>
        </form>
    );
};

export default LearningGoalForm;
