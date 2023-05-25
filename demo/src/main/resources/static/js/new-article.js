const urlParams = new URLSearchParams(window.location.search);
const articleId = urlParams.get('articleId');

$(document).ready(function() {
    if (articleId) {
        $.ajax({
            url: "http://localhost:8080/api/articles/" + articleId,
            method: "GET",
            dataType: "json"
        })
            .done(function(response) {
                const title = document.getElementById("title");
                title.value = response.title;
                title.readOnly = true;

                const userId = document.getElementById("userId");
                userId.value = response.userId;
                userId.readOnly = true;

                const content = document.getElementById("content");
                content.value = response.content;
            })
            .fail(function(jqXHR, textStatus, errorThrown) {
                console.log("Error getting article: " + errorThrown);
            });
    }
});

$(document).delegate('#save-btn','click', function(event){
    event.preventDefault();

    if (articleId) {
        updateArticle();
    } else {
        createArticle();
    }
});

function updateArticle() {
    var formData = new FormData(document.getElementById("new-article-form"));

    var json = {};
    formData.forEach(function(value, key) {
        json[key] = value;
    });
    json['date'] = new Date().toISOString().slice(0, 10);

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "http://localhost:8080/api/articles/" + articleId,
        data: JSON.stringify(json),
        dataType: 'json',
        success: function(response) {
            alert('Article update successful!')
        },
        error: function(xhr, status, errorThrown) {
            console.log(xhr.responseText);
            console.log(status, errorThrown);

            alert('Article update failed: ' + status + ". "
                + errorThrown);
        }
    });
}

function createArticle() {
    var formData = new FormData(document.getElementById("new-article-form"));

    var json = {};
    formData.forEach(function(value, key) {
        json[key] = value;
    });
    json['date'] = new Date().toISOString().slice(0, 10);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/articles",
        data: JSON.stringify(json),
        dataType: 'json',
        success: function(response) {
            alert('Article create successful!')
        },
        error: function(xhr, status, errorThrown) {
            console.log(xhr.responseText);
            console.log(status, errorThrown);

            alert('Article create failed: ' + status + ". "
                + errorThrown);
        }
    });
}