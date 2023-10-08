import React, { useState } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import pmicon from "../resources/pmicon.jpg";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://www.github.com/ycx-8">
        Chenxi Yang
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const defaultTheme = createTheme({
  palette: {
    type: "dark",
    primary: {
      main: "#555555",
    },
    background: {
      default: "#000110",
      paper: "#1a1a1a",
    },
  },
});

export default function SignIn() {

  const { setToken } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { setIsAuthenticated, setUsername } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const credentials = btoa(`${email}:${password}`);
    const headers = {
      Authorization: `Basic ${credentials}`,
    };
    try {
      const response = await axios.post(
        "http://localhost:8080/api/login",
        {},
        {
          headers: headers,
        }
      );
      if (response.status === 200) {
        setErrorMessage("");
        setIsAuthenticated(true);
        setToken(response.data.token);
        navigate("/dashboard");
      }
    } catch (error) {
      console.error("Error occurred", error);
      setErrorMessage("Incorrect email or password");
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar
            sx={{ m: 3, width: 60, height: 60, bgcolor: "#555555" }}
            src={pmicon}
          />
          <Typography component="h1" variant="h5" style={{ color: "#ffffff" }}>
            Sign in
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1, color: "#ffffff" }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              InputLabelProps={{
                style: { color: "#ffffff" },
              }}
              sx={{
                "& .MuiInputBase-input": {
                  color: "#ffffff",
                },
                "& .MuiOutlinedInput-root": {
                  "& fieldset": {
                    borderColor: "#777777",
                  },
                },
              }}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              InputLabelProps={{
                style: { color: "#ffffff" },
              }}
              sx={{
                "& .MuiInputBase-input": {
                  color: "#ffffff",
                },
                "& .MuiOutlinedInput-root": {
                  "& fieldset": {
                    borderColor: "#777777",
                  },
                },
              }}
              variant="outlined"
            />
            <div style={{ fontSize: "16px", color: "red" }}>{errorMessage}</div>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{
                mt: 3,
                mb: 2,
                backgroundColor: "#777777",
                color: "#000000",
                fontWeight: "550",
                "&:hover": {
                  color: "#ffffff",
                  backgroundColor: "#333333",
                },
              }}
            >
              Sign In
            </Button>
          </Box>
        </Box>
        <Copyright sx={{ mt: 4, mb: 4, color: "#ffffff" }} />
      </Container>
    </ThemeProvider>
  );
}
