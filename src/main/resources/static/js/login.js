window.onload = function () {
    document.querySelector("#credentials-form").addEventListener('submit', function (e) {
        e.preventDefault();

        let formData = new FormData(this);
        let jsonObject = {};

        for (const [key, value] of formData.entries()) {
            jsonObject[key] = value;
        }

        fetch('/api/v1/authentication/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body:
                JSON.stringify(jsonObject)
        }).then(response => {
            // window.location.href = "/home"; // TODO response contains a freshly generated JWT token. Use it to authorize the user
        })
    })
}