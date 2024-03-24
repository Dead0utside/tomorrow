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
        })
            .then(response => response.json()) // TODO fix error occurring when the password is wrong
            .then(data => {
                const accessToken = data.accessToken;
                const authorizationString = 'Bearer ' + accessToken;
                console.log(authorizationString);
                // fetch('/home', {
                //     method: 'GET',
                //     headers: {'Authorization': authorizationString}
                // })
            })
            .catch(error => {
                console.error("Error fetching data:", error);
            });
    });
}