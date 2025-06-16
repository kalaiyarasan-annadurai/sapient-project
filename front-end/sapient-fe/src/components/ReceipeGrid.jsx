import React, { useState } from "react";
import {
  Box,
  Grid,
  Card,
  CardContent,
  Typography,
  TextField,
  MenuItem,
  InputAdornment,
} from "@mui/material";
import SortIcon from '@mui/icons-material/Sort';
import LocalDiningIcon from '@mui/icons-material/LocalDining';

const RecipeGrid = ({ recipes }) => {
  const [sortOrder, setSortOrder] = useState("asc");
  const [tagFilter, setTagFilter] = useState("");

  const sorted = [...recipes].sort((a, b) =>
    sortOrder === "asc"
      ? a.cookTimeMinutes - b.cookTimeMinutes
      : b.cookTimeMinutes - a.cookTimeMinutes
  );

  const filtered = tagFilter
    ? sorted.filter((r) =>
        r.tags.some((tag) =>
          tag.toLowerCase().includes(tagFilter.toLowerCase())
        )
      )
    : sorted;

  return (
    <Box sx={{ maxWidth: 1000, mx: "auto", mt: 3, px: 2,ml:20 }}>
      <Grid container spacing={2} sx={{ mb: 3 }} alignItems="center">
        <Grid item xs={12} sm={6}>
          <TextField
            select
            label="Sort by Cook Time"
            value={sortOrder}
            onChange={(e) => setSortOrder(e.target.value)}
            fullWidth
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <SortIcon />
                </InputAdornment>
              )
            }}
          >
            <MenuItem value="asc">Cook Time ↑ (Low to High)</MenuItem>
            <MenuItem value="desc">Cook Time ↓ (High to Low)</MenuItem>
          </TextField>
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            label="Filter by Tag"
            placeholder="e.g., Vegan, Quick, Dessert"
            value={tagFilter}
            onChange={(e) => setTagFilter(e.target.value)}
            fullWidth
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <LocalDiningIcon />
                </InputAdornment>
              )
            }}
          />
        </Grid>
      </Grid>

      <Grid container spacing={3}>
        {filtered.map((r) => (
          <Grid item xs={12} sm={6} md={4} key={r.id}>
            <Card elevation={3} sx={{ borderRadius: 3}}>
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  {r.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Cuisine: {r.cuisine}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Cook Time: {r.cookTimeMinutes} mins
                </Typography>
                <Typography variant="body2" sx={{ mt: 1 }}>
                  <strong>Tags:</strong> {r.tags.join(", ")}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default RecipeGrid;
