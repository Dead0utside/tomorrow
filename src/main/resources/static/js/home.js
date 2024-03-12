window.onload = function () {
    const usernameString = document.querySelector('#username');

    let headers = new Headers();
    headers.set('Authorization', 'Basic ' + btoa(username + ":" + password))
    fetch('/api/v1/users/get-authorized-username', hea);
    // TODO authorize javascript requests in SecurityConfig

    // usernameString.innerHTML = ('Username: ' + )

}