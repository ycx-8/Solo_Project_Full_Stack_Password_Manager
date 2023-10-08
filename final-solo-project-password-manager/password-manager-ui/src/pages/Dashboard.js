import { ThemeProvider, Typography, Paper, Box, CssBaseline } from "@mui/material";
import { createTheme } from "@mui/material/styles";
import Sidebar from "../Components/Sidebar";

const theme = createTheme({
  palette: {
    type: "light",
    primary: {
      main: "#ffffff",
    },
    background: {
      default: "#ffffff",
      paper: "#1a1a1a",
    },
  },
  typography: {
    h3: {
      fontFamily: '"Orbitron", sans-serif'
    },
    p: {
      fontFamily: "monospace", fontSize: "20px", color: "#ffffff"
    },
  },
});

export default function Dashboard() {

  const listItems = [
    { title: 'Dashboard', description: "You're on it now." },
    { title: 'Vault', description: 'Where you can create, read, update and delete your passwords' },
    { title: 'Password Strength Checker', description: 'An algorithm checking if your passsword is strong, moderate or weak' },
  ];

  return (
    <ThemeProvider theme={theme}>
      {/* <CssBaseline /> */}
      <div style={{ display: "flex" }}>
        <div style={{ flex: 1, marginRight: "16px" }}>  
          <Sidebar />
        </div>
        <div style={{ flex: 5, marginRight: "16px" }}>  
          <Typography variant="h3" sx={{ marginTop: 5 }}>
            How can you use this site?
          </Typography>
          
          <Box sx={{ paddingLeft: 0 }}>
            <Typography variant="p">
              <ul style={{ listStyleType: "none" }}>
               {listItems.map((item, index) => 
               (
                <Paper elevation={20} sx={{ margin: 3, padding: 7, backgroundColor: 'white', color: 'black'}} key={index}>
                  <li><strong>{item.title}:</strong> {item.description}</li>
                </Paper>
              ))}
              </ul>
              </Typography>
          </Box>
        </div>
      </div>
    </ThemeProvider>
  );
};