import { ThemeProvider, Typography, Box } from "@mui/material";
import { createTheme } from "@mui/material/styles";
import Sidebar from "../Components/Sidebar";
import CssBaseline from "@mui/material/CssBaseline";
import React, { useState } from "react";
import "./PasswordStrengthChecker.css";

const theme = createTheme({
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

export default function PasswordStrengthChecker() {
  const [password, setPassword] = useState("");
  const [strength, setStrength] = useState("Weak");

  const evaluateStrength = (password) => {
    let strengthCounter = 0;
    const hasLength = password.length >= 10;
    const hasNumber = /[0-9]/.test(password);
    const hasLowercase = /[a-z]/.test(password);
    const hasUppercase = /[A-Z]/.test(password);
    const hasSpecialCharacter = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]+/.test(password);
    const hasDictionaryWord = /(password|1234|qwerty)/.test(password);  // basic example
    const hasRepeatCharacters = /(.)\1/.test(password); 
    const hasSequence = /(123|abc|def)/.test(password);
    
    if (hasLength) strengthCounter += 20;
    if (hasNumber) strengthCounter += 20;
    if (hasLowercase) strengthCounter += 20;
    if (hasUppercase) strengthCounter += 20;
    if (hasSpecialCharacter) strengthCounter += 20;
    
    // Deduct for bad patterns
    if (hasDictionaryWord) strengthCounter -= 20;
    if (hasRepeatCharacters) strengthCounter -= 10;
    if (hasSequence) strengthCounter -= 10;
    
    let strengthValue = "Very Weak";
    if (strengthCounter >= 90) {
      strengthValue = "Very Strong";
    } else if (strengthCounter >= 70) {
      strengthValue = "Strong";
    } else if (strengthCounter >= 50) {
      strengthValue = "Moderate";
    } else if (strengthCounter >= 30) {
      strengthValue = "Weak";
    }
    setStrength(strengthValue);
  };
  
  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);
    evaluateStrength(newPassword);
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
        <div style={{ flex: 1, marginRight: "16px" }}>
          <Sidebar />
        </div>
        <div style={{ flex: 6, marginRight: "16px" }}>
          <Typography variant="h3" sx={{ marginTop: 5 }}>
            Password Strength Checker
          </Typography>

          <Box>
            <div className="password-strength-checker">
              <h1>Password Strength Checker</h1>
              <input
                type="text"
                placeholder="Enter your password"
                value={password}
                onChange={handlePasswordChange}
              />
              <div className={`strength-display ${strength.toLowerCase()}`}>
                {strength}
              </div>
            </div>
          </Box>
        </div>
    </ThemeProvider>
  );
}