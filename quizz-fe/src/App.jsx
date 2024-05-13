import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";

function App() {
  const [files, setFiles] = useState([]);

  const uploadFiles = async (e) => {
    e.preventDefault();
    const formData = new FormData();

    files.forEach((file) => {
      formData.append("files", file);
    });

    await fetch("http://localhost:8080/api/files", {
      method: "POST",
      body: formData,
    });
  };

  return (
    <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
      </div>
      <div className="card">
        <form onSubmit={uploadFiles}>
          <input type="file" onChange={(e) => setFiles(Array.from(e.target.files))} />
          <button type="submit">Upload file</button>
        </form>
      </div>
    </>
  );
}

export default App;
