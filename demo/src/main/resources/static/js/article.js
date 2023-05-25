$(document).ready(function() {
    loadArticles();
});

$(document).delegate('#search-button','click', function(event){
    event.preventDefault();

    searchArticle();
});

const newArticleBtn = document.getElementById("new-article-btn");
newArticleBtn.addEventListener("click", function() {
    window.location.href = "new-article.html";
});

function searchArticle() {
    var searchValue = $('#search-input').val();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/articles/search?query=' + searchValue,
        dataType: 'json',
        success: function(data) {
            var tableBody = $('#article-table tbody');
            tableBody.empty();
            $.each(data, function(index, article) {
                var row = '<tr>';
                row += '<td><a href="/article-detail.html?articleId=' + article.id + '">' + article.title + '</a></td>';
                row += '<td>' + article.date + '</td>';
                row += '<td><a href="/new-article.html?articleId=' + article.id + '" class="btn btn-primary">Edit</a></td>';
                row += '</tr>';
                tableBody.append(row);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log('Error: ' + textStatus + ' - ' + errorThrown);
        }
    });
}

function loadArticles() {
    $.ajax({
        url: 'http://localhost:8080/api/articles',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var tableBody = $('#article-table tbody');
            tableBody.empty();
            $.each(data, function(index, article) {
                var row = '<tr>';
                row += '<td><a href="/article-detail.html?articleId=' + article.id + '">' + article.title + '</a></td>';
                row += '<td>' + article.date + '</td>';
                row += '<td><a href="/new-article.html?articleId=' + article.id + '" class="btn btn-primary">Edit</a></td>';
                row += '</tr>';
                tableBody.append(row);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log('Error loading articles: ' + errorThrown);
        }
    });
}