import  { useEffect, useState } from "react";
import { getPublicGroups } from "../api";

const StudyGroupList = () => {
    const [groups, setGroups] = useState([]);

    useEffect(() => {
        getPublicGroups()
            .then((data) => {
                console.log("Fetched groups:", data);
                if (Array.isArray(data)) {
                    setGroups(data);
                } else {
                    console.error("Unexpected response format:", data);
                    setGroups([]);
                }
            })
            .catch((error) => {
                console.error("Error fetching groups:", error);
                setGroups([]);
            });
    }, []);


    return (
        <div className="max-w-3xl mx-auto">
            <h2 className="text-2xl font-bold mb-4">Public Study Groups</h2>
            {Array.isArray(groups) && groups.length > 0 ? (
                <ul className="space-y-4">
                    {groups.map((group) => (
                        <li
                            key={group.id}
                            className="p-4 bg-gray-800 rounded-lg shadow-md hover:bg-gray-700"
                        >
                            <h3 className="text-xl font-semibold">{group.name}</h3>
                            <p className="break-words whitespace-normal">{group.description}</p>
                            <small className="text-gray-400">Created at: {new Date(group.createdAt).toLocaleString()}</small>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>Loading groups...</p>
            )}
        </div>
    );


};

export default StudyGroupList;
