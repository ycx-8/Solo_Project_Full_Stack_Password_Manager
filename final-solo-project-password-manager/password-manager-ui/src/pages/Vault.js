import {
  ThemeProvider,
  Typography,
  Paper,
  Box,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import { createTheme } from "@mui/material/styles";
import Sidebar from "../Components/Sidebar";
import CssBaseline from "@mui/material/CssBaseline";
import axios from "axios";
import { useState, useEffect } from "react";
import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import jwtDecode from "jwt-decode";
import "./Vault.css";
import { BASE_URL } from "../auth/Connection";

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

const initializeMaskedArray = (dataLength) => {
  return new Array(dataLength).fill(true);
};

export default function Vault(props) {
  const [email, setEmail] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [data, setData] = useState("");
  const [currentUserID, setCurrentUserID] = useState("");
  const [serviceName, setServiceName] = useState("");
  const [username, setUserName] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [anchorEl, setAnchorEl] = useState(null);
  const [selectedIndex, setSelectedIndex] = useState(null);
  const [id, setId] = useState("");
  const [openUpdateModal, setOpenUpdateModal] = useState(false);

  const handleClick = (event, index) => {
    setAnchorEl(event.currentTarget);
    setSelectedIndex(index);
    const currentItem = data[index];
    setServiceName(currentItem.serviceName);
    setUserName(currentItem.username);
    setNewPassword(currentItem.password);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  /*
    localstorage: a browser storage mechanism to store key-value pairs persistently on my device.
    Persistent as in I can close and reopen the browser and the values can be retrieved and used.
  */

  // GET USER SPECIFIC PASSWORD LIST (GET)
  useEffect(() => {
    console.log("properties injected: " + props.me);
    const token = localStorage.getItem("me");
    console.log("Token: " + token);
    // const token = props.me;
    if (token) {
      const decodedToken = jwtDecode(token);
      console.log("Decoded Token: ", decodedToken); // Debugging line
      const currentUser = decodedToken.sub;
      setCurrentUserID(currentUser);
      console.log("current user: " + currentUser);
    } else {
      setCurrentUserID("fallback@gmail.com");
      console.log("Token not found, using fallback user ID.");
    }
  }, [props.me]);

  const fetchData = async () => {
    try {
      const result = await axios.get(
        `http://localhost:8080/api/vault/username/${currentUserID}/passwords`
      );
      setData(result.data);
    } catch (error) {
      console.error("There was a problem fetching the data:", error);
    }
  };
  useEffect(() => {
    fetchData();
  }, [currentUserID]);

  // ADD A NEW PASSWORD (POST)
  const addPassword = async (event) => {
    if (!serviceName || !username || !newPassword || !currentUserID) {
      alert("All fields must be filled out.");
      return;
    }
    const payload = {
      serviceName,
      username,
      password: newPassword,
      userId: currentUserID,
    };

    const endpointAdd = BASE_URL + "vault";
    console.log("endpoint: " + endpointAdd);
    const requestOptions = {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("me")}`,
      },
    };

    axios
      .post(`${BASE_URL}vault`, payload, requestOptions)
      .then((response) => {
        console.log("Response:", response.status);
        if (response.status == 200) {
          console.log("Password added successfully:", response.data);
          fetchData();
        }
      })
      .catch((error) => {
        console.log("Error: " + error);
        setErrorMsg("Could not add password. Please try again.");
      });
  };

  // DELETE A PASSWORD (DELETE)
  const deletePassword = async (id) => {
    const requestOptions = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("me")}`,
      },
    };
    axios
      .delete(`${BASE_URL}vault/${id}`, {}, requestOptions)
      .then((response) => {
        console.log("Response:", response.status);
        if (response.status == 200) {
          console.log("Password deleted successfully");
          fetchData();
        }
      })
      .catch((error) => {
        console.log("Error: " + error);
        setErrorMsg("Could not delete password. Please try again.");
      });
  };

  // UPDATE A PASSWORD (PUT)
  const updatePassword = async (id) => {
    if (!serviceName || !username || !newPassword || !currentUserID) {
      alert("All fields must be filled out to update.");
      return;
    }
    const updatedPayload = {
      serviceName,
      username,
      password: newPassword,
      userId: currentUserID,
    };
    const requestOptions = {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("me")}`,
      },
    };
    console.log("email: " + email);
    try {
      console.log("Payload before update: ", updatedPayload);
      const response = await axios.put(
        `${BASE_URL}vault/${id}/${currentUserID}`,
        updatedPayload,
        requestOptions
      );

      if (response.status === 200) {
        console.log("Password updated successfully");
        fetchData();
      }
    } catch (error) {
      console.log("Error: " + error);
      setErrorMsg("Could not update password. Please try again.");
    }
  };

  const [masked, setMasked] = useState(
    initializeMaskedArray(data ? data.length : 0)
  );
  useEffect(() => {
    setMasked(initializeMaskedArray(data ? data.length : 0));
  }, [data]);

  const toggleMask = (index) => {
    const newMasked = [...masked];
    newMasked[index] = !newMasked[index];
    setMasked(newMasked);
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <div style={{ flex: 1 }}>
          <Sidebar />
        </div>
        <div style={{ flex: 4 }}>
          <Typography variant="h3" sx={{ marginTop: 5, marginBottom: 5 }}>
            Vault
          </Typography>
          <div
            style={{
              display: "flex",
              flexDirection: "column",
              alignItems: "flex-start",
            }}
          >
            <div>
              <input
                type="text"
                value={serviceName}
                onChange={(e) => setServiceName(e.target.value)}
                placeholder="Service Name"
              />
              <br />
              <input
                type="text"
                value={username}
                onChange={(e) => setUserName(e.target.value)}
                placeholder="Username"
              />
              <br />
              <input
                type="password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                placeholder="Password"
              />
            </div>
            <Box sx={{ marginBottom: 5, marginTop: 1 }}>
              <Button
                variant="contained"
                color="primary"
                onClick={() => {
                  addPassword();
                }}
              >
                Add Password
              </Button>
            </Box>

            <TableContainer component={Paper}>
              <Table>
                <TableHead>
                  <TableRow className="table-cell">
                    <TableCell sx={{ fontWeight: "700", color: "#ffffff" }}>
                      Service Name
                    </TableCell>
                    <TableCell sx={{ fontWeight: "700", color: "#ffffff" }}>
                      Username
                    </TableCell>
                    <TableCell sx={{ fontWeight: "700", color: "#ffffff" }}>
                      Passwords
                    </TableCell>
                    <TableCell></TableCell>
                    <TableCell sx={{ fontWeight: "700", color: "#ffffff" }}>
                      Actions
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {data ? (
                    data.map((item, index) => (
                      <TableRow key={index}>
                        <TableCell className="data">
                          {item.serviceName}
                        </TableCell>
                        <TableCell className="data">{item.username}</TableCell>
                        <TableCell className="data">
                          {masked[index]
                            ? "*********************"
                            : item.password}
                        </TableCell>

                        <TableCell>
                          <Button
                            onClick={() => toggleMask(index)}
                            variant="Filled"
                            className="mask-button"
                          >
                            {masked[index] ? "Show" : "Hide"}
                          </Button>
                        </TableCell>
                        <TableCell>
                          <IconButton
                            className="tripleDot"
                            aria-label="more"
                            aria-controls="long-menu"
                            aria-haspopup="true"
                            onClick={(event) => {
                              handleClick(event, index);
                            }}
                          >
                            <MoreVertIcon />
                          </IconButton>
                          <Menu
                            id="long-menu"
                            anchorEl={anchorEl}
                            keepMounted
                            open={Boolean(anchorEl) && selectedIndex === index}
                            onClose={handleClose}
                          >
                            <MenuItem
                              className="tripleDot"
                              onClick={() => {
                                setId(item.id);
                                setOpenUpdateModal(true);
                              }}
                            >
                              Modify
                            </MenuItem>
                            <MenuItem
                              className="tripleDot"
                              onClick={() => deletePassword(item.id)}
                            >
                              Delete
                            </MenuItem>
                          </Menu>
                        </TableCell>
                      </TableRow>
                    ))
                  ) : (
                    <TableRow>
                      <TableCell colSpan={3}>Loading data...</TableCell>
                    </TableRow>
                  )}
                </TableBody>
              </Table>
            </TableContainer>
          </div>
          {/* <Typography variant="p">
            {data ? JSON.stringify(data, null, 0) : "Loading data..."}
          </Typography> */}
        </div>
      </div>
      <Dialog open={openUpdateModal} onClose={() => setOpenUpdateModal(false)}>
        <DialogTitle sx={{ fontWeight: "700", color: "#ffffff" }}>
          Update Password
        </DialogTitle>
        <DialogContent>
          <div>
            <input
              type="text"
              value={serviceName}
              onChange={(e) => setServiceName(e.target.value)}
              placeholder="Service Name"
            />
            <br />
            <input
              type="text"
              value={username}
              onChange={(e) => setUserName(e.target.value)}
              placeholder="Username"
            />
            <br />
            <input
              type="password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              placeholder="Password"
            />
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => {
              setOpenUpdateModal(false);
            }}
            className="tripleDot"
          >
            Cancel
          </Button>
          <Button
            onClick={() => {
              console.log("Updating password for ID: ", id);
              updatePassword(id);
              setOpenUpdateModal(false);
            }}
            className="tripleDot"
          >
            Update
          </Button>
        </DialogActions>
      </Dialog>
    </ThemeProvider>
  );
}
