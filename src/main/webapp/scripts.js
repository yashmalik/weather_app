function validate() {
    var zip = document.getElementById("zipCode").value;

    var errorString = document.getElementById("errorString");
    var errorDiv = document.getElementById("errorMessage");
    errorDiv.style.visibility = 'hidden';

    var responseDiv = document.getElementById("responseMessage");
    responseDiv.style.visibility = 'hidden';

    var fetchButton = document.getElementById("fetchButton");
    fetchButton.value = "Fetching...";
    fetchButton.disabled = true;

    $.ajax({
        method: "post",
        url: "/weather/api/service",
        data: {"zip": zip},
        success: function (data) {
            fetchButton.disabled = false;
            fetchButton.value = "Submit";
            var jsonData = $.parseJSON(data);
            if (jsonData.hasOwnProperty("error")) {
                errorString.innerHTML = jsonData["error"];
                errorDiv.style.visibility = 'visible';
                errorDiv.style.display = "block";
                responseDiv.style.visibility = 'hidden';
            } else {
                responseDiv.style.visibility = 'visible';
                errorDiv.style.visibility = 'hidden';
                errorDiv.style.display = 'none';

                responseDiv.innerHTML = "<br/>Weather Details<br/>City: " + jsonData["data"]["city"] + "<br/>" + "State: " + jsonData["data"]["state"] + "<br/>" + "Temp (F): " + jsonData["data"]["temperatureFahrenheit"];
            }
        },
        error: function (data) {
            fetchButton.disabled = false;
            fetchButton.value = "Submit";
            errorString.innerHTML = data.statusText;
            errorDiv.style.visibility = 'visible';
            responseDiv.style.visibility = 'hidden';
        }
    })
}