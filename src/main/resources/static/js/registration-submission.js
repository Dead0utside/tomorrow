window.onload=function() {
    document.getElementById('registrationForm').addEventListener('submit',
        function (e) {
            e.preventDefault();

            // console.log("call");
            let formData = new FormData(this);
            let jsonObject = {};

            for (const [key, value] of formData.entries()) {
                jsonObject[key] = value;
            }

            fetch('/api/v1/registration/register', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body:
                    JSON.stringify(jsonObject)
            }).then(response => {
                window.location.href = "/login"; // TODO check if any request with unencrypted data is used at any point (specifically when submitting the registration)
            })
        }
    )
}