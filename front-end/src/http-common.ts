import axios from "axios";
export default axios.create({
  baseURL: "http://localhost:8887/equipment",
  headers: {
    "Content-type": "application/json",
  },
});
