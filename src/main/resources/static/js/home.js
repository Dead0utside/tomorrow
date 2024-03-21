window.onload = function () {
    const usernameString = document.querySelector('#username');

    const username = 'jsbot@mail.com';
    const password = '2ma0SKDFn2as!las?snS';

    const credentials = `${username}:${password}`;
    const base64Credentials = btoa(credentials);
    fetch('/api/v1/users/get-authorized-username', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/javascript'
        },
    }).then(response => {
        console.log(response)
    });
}