import  { useEffect, useState } from "react";
import { getPublicGroups } from "../api";

const StudyGroupList = () => {
    const [groups, setGroups] = useState([]);

    useEffect(() => {
        getPublicGroups()
            .then((response) => setGroups(response.data))
            .catch((error) => console.error("Error fetching groups:", error));
    }, []);

    return (
        <div>
            <h2>Public Study Groups</h2>
            <ul>
                {groups.map((group) => (
                    <li key={group.id}>
                        <strong>{group.name}</strong> - {group.description}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default StudyGroupList;
