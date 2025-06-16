import React, { useState } from 'react';
import { TextField, InputAdornment, IconButton, Paper } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

const SearchBar = ({ onSearch }) => {
  const [query, setQuery] = useState('');

  const handleSearch = () => {
    if (query.length >= 3) {
      onSearch(query);
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <Paper
      elevation={3}
      sx={{
        padding: '4px 8px',
        borderRadius: '24px',
        display: 'flex',
        alignItems: 'center',
        maxWidth: 600,
        margin: '20px auto'
      }}
    >
      <TextField
        fullWidth
        variant="standard"
        placeholder="Search recipes or cuisines..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        onKeyDown={handleKeyPress}
        InputProps={{
          disableUnderline: true,
          sx: { paddingLeft: 2 },
          endAdornment: (
            <InputAdornment position="end">
              <IconButton onClick={handleSearch}>
                <SearchIcon />
              </IconButton>
            </InputAdornment>
          )
        }}
      />
    </Paper>
  );
};

export default SearchBar;
