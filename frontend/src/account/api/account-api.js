import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    account: '/account'
};

function getAccounts(callback) {
    let request = new Request(HOST.backend_api + endpoint.account, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getAccountById(params, callback){
    let request = new Request(HOST.backend_api + endpoint.account + params.id, {
       method: 'GET'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}
function deleteAccount(params, callback){
    let request = new Request(HOST.backend_api + endpoint.account + "/" + params.id, {
        method: 'DELETE'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}
function postAccount(user, callback){
    let request = new Request(HOST.backend_api + endpoint.account  , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function updateAccount(user, callback){
    let request = new Request(HOST.backend_api + endpoint.account + "/" + user.id , {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}
function login(user, callback){
    let request = new Request(HOST.backend_api + endpoint.account + "/" + user.username + "/" + user.password, {
        method: 'GET',
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function addDeviceToAccount(user, callback){
    let request = new Request(HOST.backend_api + endpoint.account + "/" + user.id + "/" +user.deviceId, {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getAccounts,
    getAccountById,
    postAccount,
    deleteAccount,
    updateAccount,
    addDeviceToAccount,
    login
};
