import { Box } from "@mui/system";
import React from "react";
import NavBar from "./NavBar";
import SideBar from "./SideBar";

export default function Layout() {
  return (
    <div>
      <Box sx={{ display: "flex" }}>
        {/* <CssBaseline /> */}
        <NavBar />
        <SideBar />
      </Box>
    </div>
  );
}
