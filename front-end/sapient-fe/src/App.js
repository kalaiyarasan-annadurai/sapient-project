import { useEffect, useState } from 'react';
import axios from 'axios';
import SearchBar from './components/SearchBar';

// MUI components
import { Box, Container, Typography } from '@mui/material';
import RecipeGrid from './components/ReceipeGrid';

function App() {
  const [recipes, setRecipes] = useState([]);
  const [allList, setAllList] = useState([]);

  const loadAll = async () => {
    try {
      await axios.post('http://localhost:8080/api/recipes/load');
    } catch (err) {
      console.error('Error loading data:', err);
    }
  };

  const getAll = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/recipes/get-all');
      setAllList(response?.data);
      setRecipes(response?.data);
    } catch (err) {
      console.error('Error fetching all recipes:', err);
    }
  };

  useEffect(() => {
    loadAll().then(() => getAll());
  }, []);

  const handleSearch = (query) => {
    const filteredData = allList.filter((obj) =>
      obj.name?.toLowerCase().startsWith(query.toLowerCase())
    );
    setRecipes(filteredData);
  };

  return (
    <Box sx={{ bgcolor: '#beed8e', minHeight: '100vh', py: 4 }}>
      <Container maxWidth="md">
        <Typography
          variant="h3"
          align="center"
          gutterBottom
          sx={{ fontWeight: 700,fontFamily:'Montserrat, sans-serfi', color: '#333', mb: 3 }}
        >
          Recipe Finder
        </Typography>
        <SearchBar onSearch={handleSearch} />
        <RecipeGrid recipes={recipes} />
      </Container>
    </Box>
  );
}

export default App;
