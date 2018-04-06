import axios from 'axios'
import store from '../store'

export function get(url){
  return axios({
    method: 'GET',
    url: url,
    headers: {
      'Accept': 'application/json',
      'Authorization': 'Bearer ' + store.state.token,
    },
    withCredentials: false,
  });
}

export function post(url, data){
  let params = new URLSearchParams();
  for(let field in data) {
    if(data.hasOwnProperty(field)){
      params.append(field, data[field]);
      params.append(field, data[field]);
    }
  }

  return axios({
    method: 'POST',
    url: url,
    data: params,
    headers: {
      'Accept': 'application/json',
      'content-type': "application/x-www-form-urlencoded",
      'Authorization': 'Bearer ' + store.state.token,
    },
    withCredentials: false,
  });
}
