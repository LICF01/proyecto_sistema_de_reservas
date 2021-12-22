import React from 'react';
import { Link } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const LinkWrapper = ({ children, url, ...otherProps }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(url);
  };

  const configLink = {
    component: 'button',
    variant: 'body2',
    onClick: handleClick,
  };

  return <Link {...configLink}>{children}</Link>;
};

export default LinkWrapper;
