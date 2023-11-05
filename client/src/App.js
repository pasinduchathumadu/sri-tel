import { Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import Services from "./pages/Services";
import Login from "./pages/Login";
import Home from "./pages/Home";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route path="login" element={<Login/>} />
          <Route path="home" element={<Home />} />
        <Route path="services" element={<Services />} />
      </Route>
    </Routes>
  );
}

export default App;
