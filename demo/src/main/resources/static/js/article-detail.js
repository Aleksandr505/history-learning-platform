const urlParams = new URLSearchParams(window.location.search);
const articleId = urlParams.get('articleId');

$(document).ready(function() {
    loadArticle();
});

$(document).delegate('#createCommentButton','click', function(event){
    event.preventDefault();

    createComment();
});

function createComment() {
    var formData = new FormData(document.getElementById("comment-form"));

    // Convert FormData to JSON object
    var json = {};
    formData.forEach(function(value, key) {
        json[key] = value;
    });
    json['date'] = new Date().toISOString().slice(0, 10);
    json['articleId'] = articleId;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/comments",
        data: JSON.stringify(json),
        dataType: 'json',
        success: function(comment) {
            showComment(comment);
        },
        error: function(xhr, status, errorThrown) {
            console.log(xhr.responseText);
            console.log(status, errorThrown);
        }
    });
}

// Функция для добавления комментария на страницу
function showComment(comment) {
    var html = '<div>';
    html += '<p>(' + comment.id + ')  ' + comment.content + '</p>';
    html += '</div>';

    $('#comments-container').append(html);
}

function loadArticle() {
    $.ajax({
        url: "http://localhost:8080/api/articles/" + articleId,
        method: "GET",
        dataType: "json"
    })
        .done(function(article) {
            getAuthorInfo(article.userId)
                .done(function(author) {
                    $('#article-title').text(article.title);
                    $('#article-author').text(author.username);
                    $('#article-date').text(article.date);
                    $('#article-content').text(article.content);
                })
                .fail(function(jqXHR, textStatus, errorThrown) {
                    console.log("Error getting author: " + errorThrown);
                });

            var commentList = article.comments;
            $.each(commentList, function (index, comment) {
                showComment(comment);
            });
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.log("Error getting article: " + errorThrown);
        });
}

function getAuthorInfo(userId) {
    return $.ajax({
        url: "http://localhost:8080/api/users/" + userId,
        method: "GET",
        dataType: "json"
    });
}

