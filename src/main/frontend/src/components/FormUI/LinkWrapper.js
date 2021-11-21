import React from "react";
import { Link } from "@mui/material";

const LinkWrapper = ({ children, ...otherProps }) => {
  const handleClick = () => {
    console.info("I'm a button.");
  };

  const configLink = {
    component: "a",
    variant: "body2",
    onClick: handleClick,
  };

  return <Link {...configLink}>{children}</Link>;
};

export default LinkWrapper;
