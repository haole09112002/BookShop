// var buttons = document.querySelectorAll('.my-btn');

// function handleClick(event) {
//     // Hủy chọn tất cả các thẻ a khác trong danh sách
//     buttons.forEach(function (button) {
//         button.classList.remove('clicked');
//     });

//     // Đánh dấu thẻ a hiện tại là đã chọn
//     event.target.classList.add('clicked');
// }

// // Gắn sự kiện nhấp vào cho mỗi thẻ a
// buttons.forEach(function (button) {
//     button.addEventListener('click', handleClick);
// });

function getTop5ByCategoryId(id, element) {

    fetch('/bookshop/books/top-categories/' + id).then(function (response) {
        return response.text();
    }).then(function (html) {
        console.log(html)
        document.getElementById("topCategory").innerHTML = html;

        var buttons = document.querySelectorAll('.my-btn');
        buttons.forEach(function (button) {
            button.classList.remove('clicked');
        });
        element.classList.add("clicked");

    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });

}
function getTop5Sale() {

    fetch('/bookshop/books/top-sale').then(function (response) {
        return response.text();
    }).then(function (html) {
        console.log(html)
        document.getElementById("topSale").innerHTML = html;



    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });

}




function loadData() {
    firstCategoryId = jQuery('#firstCategoryId').val();
    getTop5ByCategoryId(firstCategoryId, jQuery('.my-btn')[0]);
    getTop5Sale();
}
// function getBooksByCategory(id, page) {
//     // this.event.preventDefault();
//     fetch('http://localhost:8080/books/booksByCategory/' + id + "?page=" + page).then(function (response) {
//         return response.text();
//     }).then(function (html) {
//         console.log(html)
//         var booksByCategoryElement = document.getElementById("booksByCategory");
//         booksByCategoryElement.innerHTML = html;
//         // document.getElementById("booksByCategory").innerHTML = html;
//     }).catch(function (err) {
//         console.warn('Something went wrong.', err);
//     });
// }
// function getBooksByAuthor(id, page) {
//     // this.event.preventDefault();
//     fetch('http://localhost:8080/books/booksByAuthor/' + id + "?page=" + page).then(function (response) {
//         return response.text();
//     }).then(function (html) {
//         console.log(html)
//         var booksByCategoryElement = document.getElementById("booksByAuthor");
//         booksByCategoryElement.innerHTML = html;
//         // document.getElementById("booksByCategory").innerHTML = html;
//     }).catch(function (err) {
//         console.warn('Something went wrong.', err);
//     });
// }
