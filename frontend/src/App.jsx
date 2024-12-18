import React from "react";
import CreateGroupForm from "./components/CreateGroupForm";
import StudyGroupList from "./components/StudyGroupList";

const App = () => {
    return (
        <div>
            <h1>Virtual Study Group Platform</h1>
            <CreateGroupForm />
            <StudyGroupList />
        </div>
    );
};

export default App;
