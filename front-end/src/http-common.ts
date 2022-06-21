import axios from "axios";
import qs from 'qs';
export default axios.create({
  baseURL: "http://localhost:8887",
  headers: {
    "Content-type": "application/json",
  },
  paramsSerializer: (params) => {
    return qs.stringify(params, {arrayFormat: 'repeat'});
},
});
