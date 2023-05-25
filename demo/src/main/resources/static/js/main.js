$(document).ready(function() {
    loadUsers();
});

$(document).delegate('#registerButton','click', function(event){
    event.preventDefault();

    registerUser();
});

function loadUsers() {
    $("#user-table tbody").empty();

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/users",
        success: function (users) {
            // Loop through each user in the array and display their details
            var userTable = $("#user-table tbody");
            $.each(users, function (index, user) {
                userTable.append("<tr><td>" + user.id + "</td><td>" + user.username + "</td><td>" + user.email + "</td></tr>");
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }
    });
}

function registerUser() {

    var formData = new FormData(document.getElementById("registration-form"));

    // Convert FormData to JSON object
    var json = {};
    formData.forEach(function(value, key) {
        json[key] = value;
    });

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/users",
        data: JSON.stringify(json),
        dataType: 'json',
        success: function(response) {
            alert('User registration successful!')

            loadUsers();
        },
        error: function(xhr, status, errorThrown) {

            console.log(xhr.responseText);
            console.log(status, errorThrown);

            alert('User registration failed: ' + status + ". "
                + errorThrown);
        }
    });
}