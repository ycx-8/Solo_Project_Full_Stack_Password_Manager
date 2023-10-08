import React, { useState } from "react";
import { BASE_URL } from "../auth/Connection";
import Sidebar from "../Components/Sidebar";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import pmicon from "../resources/pmicon.jpg";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const defaultTheme = createTheme({
  palette: {
    type: "dark",
    primary: {
      main: "#555555",
    },
    background: {
      default: "#000000",
      paper: "#1a1a1a",
    },
  },
  typography: {
    h3: {
      fontFamily: '"Orbitron", sans-serif',
      color: "#ffffff",
    },
    p: {
      fontFamily: "monospace",
      fontSize: "20px",
      color: "#ffffff",
    },
  },
});

export default function SignIn(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const setMe = props.me;
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const endpoint = BASE_URL + "users/mp";
    console.log("endpoint: " + endpoint);
    const requestOptions = {
      headers: {
        Authorization: "Basic " + btoa(email + ":" + password),
      },
    };

    axios
      .post(endpoint, {}, requestOptions)
      .then((response) => {
        setMe(response.data);
        localStorage.setItem("me", response.data);
        navigate("/vault/access");
      })
      .catch((error) => {
        if (
          ((error || { response: null }).response || { status: null })
            .status === 401
        ) {
          setErrorMsg("Invalid username or password");
        } else {
          setErrorMsg("Error: " + error);
        }
      });
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth={false}>
        <CssBaseline />
        <div style={{ display: "flex", marginRight: "16px" }}>
          <div style={{ flex: 1 }}>
            <Sidebar />
          </div>
          <div style={{ flex: 5 }}>
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

              <Typography
                component="h1"
                variant="h5"
                style={{ color: "#ffffff", fontFamily: "monospace" }}
              >
                Vault Locked
              </Typography>

              <Box
                component="form"
                onSubmit={handleSubmit}
                Validate
                sx={{ mt: 1, color: "#ffffff" }}
              >
                {/* Email text field */}
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
                  variant="filled"
                />

                {/* Password text field */}
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
                  variant="filled"
                />

                {/* Show error messages when authentication fails */}
                <div
                  style={{
                    fontSize: "16px",
                    color: "red",
                    fontFamily: "monospace",
                  }}
                >
                  {errorMsg}
                </div>

                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{
                    mt: 3,
                    mb: 2,
                    backgroundColor: "#777777",
                    color: "#000000",
                    fontWeight: "650",
                    fontFamily: "monospace",
                    "&:hover": {
                      color: "#ffffff",
                      backgroundColor: "#333333",
                    },
                  }}
                >
                  Access vault
                </Button>
              </Box>
            </Box>
          </div>
        </div>
      </Container>
    </ThemeProvider>
  );
}