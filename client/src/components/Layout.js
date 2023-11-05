import { Outlet } from "react-router-dom";
import { Box } from "@chakra-ui/react";
import Navbar from "./Navbar";

const Layout = () => {
  return (
    <main className="App">
      <Box>
        <Navbar />
        <Outlet />
      </Box>
    </main>
  );
};

export default Layout;
